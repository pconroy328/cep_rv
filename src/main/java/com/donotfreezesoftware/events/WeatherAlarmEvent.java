/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *
 * { "topic":"WS2308/ALARM", "dateTime":"2020-09-08T10:03:55-0600" , 
 *      "alarmMsg":"OUTDOOR HUMIDITY HIGH" , 
 *      "value":92.0 }
 *
 * @author pconroy
 */
public class WeatherAlarmEvent extends MQTTMessage_POJO
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( WeatherAlarmEvent.class );    
    
    @SerializedName("alarmMsg")         String  alarmMsg;
    @SerializedName("value")            float   value;


    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  WeatherAlarmEvent ()
    {
        super();
    }

    // -------------------------------------------------------------------------
    public static WeatherAlarmEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            WeatherAlarmEvent  event = null;
            event = gson.fromJson( jsonMessage, WeatherAlarmEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a WeatherAlarmEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    public String getAlarmMsg() {
        return alarmMsg;
    }

    public void setAlarmMsg(String alarmMsg) {
        this.alarmMsg = alarmMsg;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    
}
