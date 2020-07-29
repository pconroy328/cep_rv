/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.esper_maven_test;

import com.donotfreezesoftware.events.MQTTMessage_POJO;
import com.donotfreezesoftware.events.SolarChargeControllerEvent;
import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class MQTTClient implements MqttCallback 
{
    private static  final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger( "MQTTClient" );
    
    private String brokerURL;
    private MqttAsyncClient aClient;
    

    
    // -------------------------------------------------------------------------
    //
    //  This method is called when a message (one that we subscribed to) has arrived.
    //  We need to parse it out, figure out which Event POJO matches the message.
    //  Then we create that Event POJO and feed it into the Esper Engine.
    //
    //  This is the way we map MQTT Messages to Esper Events
    //
    //  The message will be in JSON format.
    //  All of my Messages have a common set of attributes. Those will get parsed
    //  out and into the Superclass MQTTMessage
    //
    @Override
    public void messageArrived (String topic, MqttMessage message) throws Exception 
    {
        String  jsonPayload = new String( message.getPayload() );
        
        // log.debug( "Message arrived. Topic [" + topic + "] Payload [" + jsonMessage + "]" );
        
        MQTTMessage_POJO    anEvent = null;
        
        //
        // Now figure out the event based on the topic
        if (topic.equalsIgnoreCase( "SCC/1/DATA" ) ) {
            anEvent = SolarChargeControllerEvent.fromJson( jsonPayload );
        //
        // Send the event into Esper's Runtime engine
        //if (this.cepRuntime != null && anEvent != null) {
        //    cepRuntime.sendEvent( anEvent );
        //}
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    // -------------------------------------------------------------------------
    void    connect (String aBrokerURL, String aClientID) throws MqttException
    { 
        log.debug( "Calling connect to SUBSCRIBE to MQTT broker at [" + brokerURL + "]" );
        this.brokerURL = aBrokerURL;
        
        //
        // Remember - client IDs must be unique!!!
        Random  r = new Random();
        int randomID = r.nextInt(100-1) + 1;
        String clientID = aClientID + "_" + randomID;
        
        try {
            aClient = new MqttAsyncClient( brokerURL, clientID, new MemoryPersistence() );
            
            MqttConnectOptions connectionOptions = new MqttConnectOptions();
            connectionOptions.setCleanSession( false );             // otherwise you need a Persistance store
            connectionOptions.setConnectionTimeout( 10 );           // max time we wait for Broker connection
            connectionOptions.setKeepAliveInterval( 0 );            // disable keep-alive processing
            connectionOptions.setAutomaticReconnect( true );
            
            aClient.setCallback( this );                            // when a message arrives, call this class
            
            IMqttToken aToken = aClient.connect( connectionOptions );
            aToken.waitForCompletion( 10L * 1000L );
            log.warn( "MQTT Client connected to broker - automatic reconnect is on!" );
        } catch (MqttException mqttEx) {
            log.error( "MQTT Exception encountered. " + mqttEx );
            aClient = null;
            throw mqttEx;
        }
    }
    
    // -------------------------------------------------------------------------
    public    void    subscribe (String topic)
    {
        log.debug( "Calling subscribe to topic [" + topic + "]" );
        
        try {
            int QoS = 0;
            IMqttToken aToken = aClient.subscribe( topic, QoS );
            aToken.waitForCompletion( 3000L );
                      
        } catch (MqttException mqttEx) {
            log.error( "MQTT Exception encountered. " + mqttEx );
        }
    }
    
    public void connectionLost(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
