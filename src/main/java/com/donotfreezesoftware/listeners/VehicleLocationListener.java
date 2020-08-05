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
    public void update (EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime ) 
    {
        log.info( "Vehicle Location Listener has been invoked" );
        int numEvents = newEvents.length;
        
        
    }
    
}
