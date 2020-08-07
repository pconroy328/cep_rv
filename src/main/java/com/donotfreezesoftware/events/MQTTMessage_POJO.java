/*MQTTClient
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.events;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
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
    protected   LocalDateTime   localDateTime;           // convert dateTimeString
    protected   long            createdOn;          // millis when message was instantiated
    protected   String          jsonPayload;        // the entire JSON message
   
    
    //
    // Don't need a superclass constructor. But do need a subclass ctor!
    //  Then Gson seems to know how to fill in the superclass attributes when 
    //  parsing the subclass. You can't do much in here because Gson has called
    //  this ctor before anything has been deserialized
    //
    public  MQTTMessage_POJO ()
    {
    }
    
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
    
    // -------------------------------------------------------------------------
    public  void setDateTimeFromString (String dateTimeStr)
    {
        if (dateTimeStr == null) {
            log.error( "Passed in a null dateTimeString to parse" );
            localDateTime = LocalDateTime.now();
            return;
        }
                
        try {
            localDateTime = LocalDateTime.parse( dateTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME );
            
        } catch (DateTimeParseException pex) {  try {
            localDateTime = LocalDateTime.parse( dateTimeStr, DateTimeFormatter.ISO_DATE_TIME );
            
        } catch (DateTimeParseException pex2) { try {
            localDateTime = LocalDateTime.parse( dateTimeStr, DateTimeFormatter.ISO_ZONED_DATE_TIME );
            
        } catch (Exception ex) {  } }
            log.info( "Unknown dateTime formatted string in message payload [" + dateTimeStr + "]" );
            
            List<String> formatStrings = Arrays.asList("yyyy-MM-dd'T'HH:mmX", 
                        "yyyy-MM-dd'T'HH:mm:ss'Z'",   "yyyy-MM-dd'T'HH:mm:ssZ",
                        "yyyy-MM-dd'T'HH:mm:ss",      "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss", 
                        "MM/dd/yyyy HH:mm:ss",        "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", 
                        "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS", 
                        "MM/dd/yyyy'T'HH:mm:ssZ",     "MM/dd/yyyy'T'HH:mm:ss", 
                        "yyyy:MM:dd HH:mm:ss",        "yyyyMMdd", "yyyy-MM-dd'T'HH:mmZ");

            for (String formatString : formatStrings) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
                    localDateTime = LocalDateTime.parse(dateTimeStr, formatter);
                    break;
                } catch (DateTimeParseException pex3) {
                    /* do nothing */ ;
                }
            }
            
            //
            // Still no luck?  Just set it to NOW
            if (localDateTime == null) {
                log.error( "Unable to parse this date time string [" + dateTimeStr + "]" );
                localDateTime = LocalDateTime.now();
            }
        }
    }
}


