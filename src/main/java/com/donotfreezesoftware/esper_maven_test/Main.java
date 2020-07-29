/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.esper_maven_test;

import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author pconroy
 */
public class Main 
{
    private static  final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger( "Main" );
    
    public static void main (String args[]) 
    {
        log.info( "Starting..." );
        MQTTClient  mqttClient = new MQTTClient();
        try {
            mqttClient.connect( "tcp://gx100.local:1883", "esperrv" );
            mqttClient.subscribe( "SCC/1/DATA" );
        } catch (MqttException mqttEx) {
            
        }
        
        EPCompiler compiler = EPCompilerProvider.getCompiler();
    }
}
