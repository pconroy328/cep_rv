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
public class VehicleLocationListener implements UpdateListener 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( VehicleLocationListener.class );    

    @Override
    public void update (EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) 
    {
        log.info( "Vehicle Location Listener has been invoked" );
        int numEvents = newEvents.length;
        //
        // We usually (?always?) get just one New Event
        if (numEvents > 0 && newEvents != null) {
            //
            // Extract the data. We did "SELECT *" so we got all of the fields in the POJO
            // We can extract them by name from the first event that arrived
            String  mode = (String) newEvents[0].get( "mode" );
            float   latitude = (Float) newEvents[0].get( "latitude" );
            float   longitude = (Float) newEvents[0].get( "longitude" );
            
            float   altitude = (Float) newEvents[0].get( "altitude" );
            float   speed = (Float) newEvents[0].get( "speed" );
            float   track = (Float) newEvents[0].get( "track" );
            float   climb = (Float) newEvents[0].get( "climb" );
            float   distance = (Float) newEvents[0].get( "distance" );

            String  GDOP = (String) newEvents[0].get( "GDOP" );
            String  geohash = (String) newEvents[0].get( "geohash" );

            //
            // Now do with the data as you wish.
            if (speed > 65.0)
                log.warn( "Slow down!" );
        }
        
    }
    
}
