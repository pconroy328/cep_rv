/*MQTTClient
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.events;

import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *  All of the MQTT Events share common attributes.
 *  This class will be the superclass for them.
 * 
 * @author pconroy
 */
public class MQTTMessage_POJO 
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( MQTTMessage_POJO.class );    
    
    //
    // These are the common attributes across all MQTT messages coming in
    @SerializedName("topic")            String  topic;
    @SerializedName("version")          String  version;
    @SerializedName("dateTime")         String  dateTimeString;     // the date time as an ISO8601 String
    
    //
    // Add a couple of attributes that we'll find useful
    protected   long        createdOn;          // millis when message was instantiated
    protected   String      jsonPayload;        // the entire JSON message
   
    
    //
    // Don't need a superclass constructor. Gson seems to know how to fill in the
    //  superclass attributes when parsing the subclass
    //
    
    //
    //  NetBeans IDE Generated getters and setters
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getJsonPayload() {
        return jsonPayload;
    }

    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }
    
    
}


