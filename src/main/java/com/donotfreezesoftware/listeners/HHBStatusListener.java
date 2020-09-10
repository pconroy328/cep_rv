/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.listeners;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class HHBStatusListener implements UpdateListener 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( HHBStatusListener.class );  

    @Override
    public void update (EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) 
    {
        // log.info( "Home Heartbeat Event Listener has been invoked" );
        int numEvents = newEvents.length;
        //
        // We usually (?always?) get just one New Event
        if (numEvents > 0 && newEvents != null) {
            //
            // Extract the data. We did "SELECT *" so we got all of the fields in the POJO
            // We can extract them by name from the first event that arrived
            int     deviceType = (Integer) newEvents[0].get( "deviceType" );
            String  name = (String) newEvents[0].get( "deviceName" );
            String  state = (String) newEvents[0].get( "deviceStatus" );
            int     duration = (Integer) newEvents[0].get( "statusDuration" );
            boolean isTriggered = (Boolean) newEvents[0].get( "isTriggered" );
            String  macAddress = (String) newEvents[0].get( "macAddress" );

            /*if (macAddress.equals( "000000B357" ))
                log.info( "GARAGE DOOR - [" + name + "]  State:[" + state + "]  Triggered: " + isTriggered + "  Duration: " + duration );
            else if (macAddress.equals( "000000FF02" ))
                log.info( "GARAGE MOTION  - [" + name + "]  State:[" + state + "]  Triggered: " + isTriggered + "  Duration: " + duration );
            */
            //log.info( "HHBStatusEvent [" + name + "]  State:[" + state + "]  Triggered: " + isTriggered + "  Duration: " + duration );
        }
    }

    
}
