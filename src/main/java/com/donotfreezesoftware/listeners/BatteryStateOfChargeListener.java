/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.listeners;

import com.donotfreezesoftware.events.SolarChargeControllerEvent;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import org.slf4j.LoggerFactory;

/**
 *  This is the object that's instantiated when the EPL Statement
 *  "Battery State of Charge < Threshhold" is triggered
 * 
 * @author pconroy
 */
public class BatteryStateOfChargeListener implements UpdateListener 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger(BatteryStateOfChargeListener.class );    

    @Override
    public void update (EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime ) 
    {
        log.info( "Battery State of Charge has dropped below the threshold" );
        int numEvents = newEvents.length;
        
        //
        // We usually (?always?) get just one New Event
        if (numEvents > 0 && newEvents != null) {
            //
            // Extract the data. We did "SELECT *" so we got all of the fields in the POJO
            // We can extract them by name from the first event that arrived
            int     batterySOC = (Integer) newEvents[0].get( "batterySOC" );
            
            log.info( "SCC EventListener saying that battery SOC is: " + batterySOC + "%" );
            
            //
            // So now we can do additional logic. For example, if the BatterySOC is low
            // and the sun is shining and the PV Panels are producing, then maybe we have a
            // problem! Let's pull out some data and see if we can figure out what's going on!
            boolean isNightTime = (Boolean) newEvents[0].get( "isNightTime" );
            
            float   pvVoltage = (Float) newEvents[0].get( "pvVoltage" );
            float   pvCurrent = (Float) newEvents[0].get( "pvCurrent" );

            float   loadVoltage = (Float) newEvents[0].get( "loadVoltage" );
            float   loadCurrent = (Float) newEvents[0].get( "loadCurrent" );

            float   batteryVoltage = (Float) newEvents[0].get( "batteryVoltage" );
            float   batteryTemperature = (Float) newEvents[0].get( "batteryTemperature" );
            
            //
            // First of all - are we harming the battery? Sun up or sun down?
            if (batterySOC <= 50) {
                log.warn( "Battery State of Charge is too low! Possible Battery Damage occuring!");
                log.warn( "Battery State of Charge is: " + batterySOC + "%  Voltage is: " + batteryVoltage + "V" );
                
            } else if (batterySOC < 70 && !isNightTime) {
                log.warn( "Battery State of Charge is low! And the Sun is up." );
                log.warn( "Battery State of Charge is: " + batterySOC + "%  Voltage is: " + batteryVoltage + "V" );
                log.warn( "Solar Panel Voltage is: " + pvVoltage + "V  Current is: " + pvCurrent + "A" );
                log.warn( "Load Voltage is: " + loadVoltage + "V  Current is: " + loadCurrent + "A" );
                log.warn( "Battery Voltage is: " + batteryVoltage + "V  Temperatire is: " + batteryTemperature + "*F" );
                
            } else if (batterySOC < 70 && isNightTime) {
                log.warn( "Battery State of Charge is low! But the sun has set. Just monitor the situation" );
            }
        }
    }
    
}
