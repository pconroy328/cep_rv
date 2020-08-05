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
public class BatteryStateOfChargeLowListener implements UpdateListener 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( BatteryStateOfChargeLowListener.class );    

    @Override
    public void update (EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime ) 
    {
        log.info( "Battery State of Charge has dropped below the threshold" );
        int numEvents = newEvents.length;
        
        
    }
    
}
