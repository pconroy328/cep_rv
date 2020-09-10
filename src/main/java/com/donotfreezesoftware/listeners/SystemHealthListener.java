/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.listeners;

import com.donotfreezesoftware.ceprv.MQTTClient;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class SystemHealthListener implements UpdateListener 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( SystemHealthListener.class );    

    @Override
    public void update (EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) 
    {
        log.info( "System Health Listener has been invoked" );
        int numEvents = newEvents.length;
        //
        // We usually (?always?) get just one New Event
        if (numEvents > 0 && newEvents != null) {
            //
            // Extract the data. We did "SELECT *" so we got all of the fields in the POJO
            // We can extract them by name from the first event that arrived
            String  host = (String) newEvents[0].get( "host" );
            float   cpu_temp = (Float) newEvents[0].get( "cpuTemperature" );
            float   cpu_utilization = (Float) newEvents[0].get( "cpuUtilization" );
            float   root_percent_full = (Float) newEvents[0].get( "rootPercentUsed" );
            int     xmt_errors = (Integer) newEvents[0].get( "xmt_errors" );
            int     rcv_errors = (Integer) newEvents[0].get( "rcv_errors" );
            
            log.info( "CALLING PUBLISH System Health Info came in for host [" + host + "]" );
            String message = "Host [" + host + "] CPU Temp: " + cpu_temp;
           MQTTClient.getInstance().publishMessage( "CEP/NODE", "FRIBITZ" );

        }
    }
            
}
