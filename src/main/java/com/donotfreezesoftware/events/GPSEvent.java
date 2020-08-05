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
 * 
 * {"topic":"GPS", "datetime":"2020-08-03T13:05:52-0600", "mode":"3D", "latitude":40.020874, "longitude":-105.088037, "altitude":5217.59, "speed":0.0, "track":-100.0, "climb":0.0, "GDOP":"POOR", "distance":0.00, "geohash":"9xj78tt4"}
 * @author pconroy
 */
public class GPSEvent extends MQTTMessage_POJO
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( GPSEvent.class );    
    
    @SerializedName("mode")         String  mode;
    @SerializedName("latitude")     float   latitude;
    @SerializedName("longitude")    float   longitude;
            
    @SerializedName("altitude")     float   altitude;
    @SerializedName("speed")        float   speed;
    @SerializedName("track")        float   track;
    @SerializedName("climb")        float   climb;
    @SerializedName("distance")     float   distance;
    @SerializedName("GDOP")         String  GDOP;
    @SerializedName("geohash")      String  geohash;
    
    
    // -------------------------------------------------------------------------
    public static GPSEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            GPSEvent  event = null;
            event = gson.fromJson( jsonMessage, GPSEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a GPSEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
}
