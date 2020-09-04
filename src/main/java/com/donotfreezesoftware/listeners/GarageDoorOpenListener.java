/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.listeners;

import com.donotfreezesoftware.events.HHBStatusEvent;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.internal.event.map.MapEventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class GarageDoorOpenListener implements UpdateListener 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( GarageDoorOpenListener.class );  

    @Override
    public void update (EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) 
    {
        log.info( "GarageDoorOpenListener has been invoked" );
        int numEvents = newEvents.length;
        //
        // We usually (?always?) get just one New Event
        if (numEvents > 0 && newEvents != null) {
            MapEventBean    myEvents = (MapEventBean) newEvents[0];
            HHBStatusEvent  motionEvent = (HHBStatusEvent) myEvents.get( "gme" );
            HHBStatusEvent  doorEvent = (HHBStatusEvent) myEvents.get( "gde" );
            
            //
            // Extract the data. We did "SELECT *" so we got all of the fields in the POJO
            // We can extract them by name from the first event that arrived
            //int     deviceType = (Integer) newEvents[0].get( "deviceType" );
            // String  name = (String) newEvents[0].get( "deviceName" );
            //String  state = (String) newEvents[0].get( "deviceStatus" );
            //int     duration = (Integer) newEvents[0].get( "statusDuration" );
            //boolean isTriggered = (Boolean) newEvents[0].get( "isTriggered" );

            log.info( "--------------------------------------------------------------------------" );
            log.info( "GarageDoorOpenListener. Motion Sensor State: [" + motionEvent.getDeviceStatus() + "] Duration: " + motionEvent.getStatusDuration() );
            log.info( "                        Door   Sensor State: [" + doorEvent.getDeviceStatus() + "] Duration: " + doorEvent.getStatusDuration() );
        }
    }
}