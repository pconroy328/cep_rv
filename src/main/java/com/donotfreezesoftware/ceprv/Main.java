/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.ceprv;

import com.donotfreezesoftware.events.GPSEvent;
import com.donotfreezesoftware.events.SolarChargeControllerEvent;
import com.donotfreezesoftware.listeners.BatteryStateOfChargeLowListener;
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
        MQTTClient  mqttClient = new MQTTClient();
        try {
            mqttClient.connect( "tcp://gx100.local:1883", "esperrv" );
            mqttClient.subscribe( "SCC/1/DATA" );
            mqttClient.subscribe( "GPS" );
        } catch (MqttException mqttEx) {
            log.error( "Error!", mqttEx );
        }
        
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
        //      1.1)    You need to get a reference to the Compiler to compile the EPL
        //      1.2)    You need a configuration object
        //          1.2.1)  Tell the Configuration object what Event POJOs will be arriving
        //      1.3)    You need a reference to a Runtime - there are several options, we'll use the default
        //          1.3.1)  Pass in the configuration object
        //          1.3.2)  Initialize the runtime
        
        EPCompiler compiler = EPCompilerProvider.getCompiler();
        Configuration   configuration = new Configuration();
        //config.getCompiler().getLogging().setEnableCode(true)
        configuration.getCompiler().getLogging().setEnableCode(true);
        
        // Tell it what Event POJOs will be coming into the engine
        //  That means Step 2 -- creating the POJO -- is done.  Our first Event POJO
        //  is the SolarChargeControllerEvent object
        configuration.getCommon().addEventType( SolarChargeControllerEvent.class );
        configuration.getCommon().addEventType( GPSEvent.class );
        
        //----------------------------------------------------------------------
        CompilerArguments args2 = new CompilerArguments(configuration);		
        EPCompiled epCompiled1;
        EPCompiled epCompiled2;
        try {
          epCompiled1 = compiler.compile("@name('my-statement') select * from GPSEvent", args2);
          epCompiled2 = compiler.compile("@name('my-statement-2') select * from SolarChargeControllerEvent", args2);
        }
        catch (EPCompileException ex) {
          // handle exception here
          throw new RuntimeException(ex);
        }
        EPRuntime runtime = EPRuntimeProvider.getDefaultRuntime(configuration);
        EPDeployment deployment;
        EPDeployment deployment2;
        try {
          deployment = runtime.getDeploymentService().deploy(epCompiled1);
          deployment2 = runtime.getDeploymentService().deploy(epCompiled2);
        }
        catch (EPDeployException ex) {
          // handle exception here
          throw new RuntimeException(ex);
        }
        EPStatement statement1 = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(), "my-statement");
        EPStatement statement2 = runtime.getDeploymentService().getStatement(deployment2.getDeploymentId(), "my-statement-2");

        BatteryStateOfChargeLowListener bsoclListener = new BatteryStateOfChargeLowListener();
        statement1.addListener(bsoclListener);
        statement2.addListener(bsoclListener);
        mqttClient.setTheRuntime( runtime );

        
        /*
        EPRuntime runtime = EPRuntimeProvider.getDefaultRuntime( configuration );
        mqttClient.setTheRuntime( runtime );
        
        //
        // Step 2 - Create a POJO for every Event type that'll be coming in. Done!
        // Step 4 We need that Listener Object
        BatteryStateOfChargeLowListener bsoclListener = new BatteryStateOfChargeLowListener();
        
        //
        // Step 3 - Create the EPL. Let's do something simple.
        //  Esper, Tell us when the Battery State of Charge percentage drops below 70%
        String  anEPLQuery = "SELECT * FROM SolarChargeControllerEvent scce";
        log.info("Compiling EPL");

        
        EPDeployment deployment = compileDeploy( runtime, anEPLQuery );
        deployment.getStatements()[0].addListener( bsoclListener );  
        runtime.initialize();
        */
        
        //
        // Now wait...
        while (true) {
            try { Thread.sleep( 1000 ); } catch (Exception ex) { break; }
        }
        log.error( "Exiting" );
    }
    
    public static EPDeployment compileDeploy (EPRuntime runtime, String epl) {
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
