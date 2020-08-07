/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.ceprv;

import com.donotfreezesoftware.events.GPSEvent;
import com.donotfreezesoftware.events.SolarChargeControllerEvent;
import com.donotfreezesoftware.listeners.BatteryStateOfChargeListener;
import com.donotfreezesoftware.listeners.VehicleLocationListener;
import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class Main 
{
    //
    //  Esper uses SLF4J - we might as well too!
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( Main.class );    
    
    public static void main (String args[]) 
    {
        PropertyConfigurator.configure( "/home/pconroy/NetBeansProjects/cep_rv/src/log4j.properties" );

        log.info( "Starting..." );
        //
        // Remember the basic steps are:
        //  1) Esper Runtime/Compiler initial setup.   (Runtime was more or less the "Engine" in V7 and below)
        //  2) Create a POJO that represents the Event
        //  3) Create the EPL - this is what you want to happen when your event pattern is found
        //  4) Create a Listener Class that will be invoked when the Esper Engine detects the event pattern
        //  5) Subscribe to the MQTT Event stream
        //      5.1) As Events Arrive (serialized as JSON messages), deserialize JSON to right Event POJO
        //      5.2) Feed that Event POJO into the Esper Engine for processing
        //
        
        
        //
        // Step 1 - Esper Engine Initial Setup
        //  1.1) You need to get a reference to the Compiler to compile the EPL
        //  1.2) You need a configuration object
        
        EPCompiler compiler = EPCompilerProvider.getCompiler();
        Configuration   configuration = new Configuration();
        
        // Enable more verbose debugging if you want to
        //configuration.getCompiler().getLogging().setEnableCode(true);
        
        
        // Tell Esper what Event POJOs will be coming into the engine
        //  That means Step 2 -- creating the POJO -- is done.  
        //  We've got two Events and two event POJOs: SolarChageControllerEvent and 
        //  the GPSEvent
        configuration.getCommon().addEventType( SolarChargeControllerEvent.class );
        configuration.getCommon().addEventType( GPSEvent.class );
        

        //
        // Setup the Esper Runtime - passing in the configuration
        // We'll need that runtime reference passed into the MQTT Client object too
        EPRuntime runtime = EPRuntimeProvider.getDefaultRuntime( configuration );
        

        //
        //  For every unique Event Pattern we're going to detect, there should be a Listener
        //  Object.  This is the object that gets invoked when the Event Pattern is seen
        
        //
        //  Let's say we're interested when the "Battery State of Charge" drops below
        //  70% as that could be damaging to the battery.  
        BatteryStateOfChargeListener bsocListener = new BatteryStateOfChargeListener();
        
        //
        // Now let's create the EPL that tells Esper to let us know when the Battery State of Charge
        //  drops below 70%! 
        // Every EPL statement has a unique name. It gets compiled, deployed and the Listener Class
        //  is associated with it
        String  anEPLQuery = "@name('scce_batterysoclow') SELECT * FROM SolarChargeControllerEvent scce WHERE scce.batterySOC < 70";        
        EPDeployment deployment = compileDeploy( runtime, anEPLQuery );
        runtime.getDeploymentService().getStatement( deployment.getDeploymentId(), "scce_batterysoclow" ).addListener( bsocListener );

        
        //
        // Let's keep going. For GPS, tell us when we're home! We know we're home when the geohash matches our home's
        //  location.
        VehicleLocationListener rvHomeListener = new VehicleLocationListener();
        anEPLQuery = "@name('gpse_rvhome') SELECT * FROM GPSEvent gpse WHERE gpse.geohash='9xj78abc'";
        EPDeployment deployment2 = compileDeploy( runtime, anEPLQuery );
        runtime.getDeploymentService().getStatement( deployment2.getDeploymentId(), "gpse_rvhome" ).addListener( rvHomeListener );

        
        //
        // Now we can sit back and let MQTT and Esper do their thing.
        //  -  MQTTClient will be receiving events from the broker. The JSON events
        //     will be inflated into POJOs and sent into the Esper runtime engine.
        //  -  Esper's engine will watch the event stream waiting for those patterns
        //     described by the EPL statements to appear. When they do, the matching
        //     Listener objects will be invoked!
        //
        // Step 5 - Now wait and just process events
        MQTTClient  mqttClient = new MQTTClient();
        try {
            mqttClient.connect( "tcp://gx100.local:1883", "esperrv" );
            mqttClient.subscribe( "SCC/1/DATA" );
            mqttClient.subscribe( "GPS" );
            mqttClient.setTheRuntime( runtime );
        } catch (MqttException mqttEx) {
            log.error( "Error!", mqttEx );
        }
        
        while (true) {
            try { Thread.sleep( 1000 ); } catch (Exception ex) { break; }
        }
        
        log.error( "Exiting" );
    }
    
    // -------------------------------------------------------------------------
    public static EPDeployment compileDeploy (EPRuntime runtime, String epl) 
    {
        //
        // This method appears in the Esper documentation for migrating 
        //  older code (before v8) over to the new approach that's in V8 and up
        //  It's a helper method.
        //
        try {
            // Obtain a copy of the engine configuration
            Configuration configuration = runtime.getConfigurationDeepCopy();

            // Build compiler arguments
            CompilerArguments args = new CompilerArguments(configuration);

            // Make the existing EPL objects available to the compiler
            args.getPath().add(runtime.getRuntimePath());

            // Compile
            EPCompiled compiled = EPCompilerProvider.getCompiler().compile(epl, args);

            // Return the deployment
            return runtime.getDeploymentService().deploy(compiled);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
