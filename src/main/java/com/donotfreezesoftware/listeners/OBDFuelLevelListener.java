/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.listeners;

import com.donotfreezesoftware.events.OBD2StatusEvent;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class OBDFuelLevelListener implements UpdateListener 
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
            OBD2StatusEvent anEvent = (OBD2StatusEvent) newEvents[ 0 ].getUnderlying();
            float   fuelLevel = anEvent.getFuelLevel();
            
            log.info( "Fuel Level is: {} ", fuelLevel );
            
        }
    }
}
