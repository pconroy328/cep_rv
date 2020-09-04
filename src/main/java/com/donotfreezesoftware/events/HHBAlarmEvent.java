/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.events;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.Calendar;
import org.slf4j.LoggerFactory;



/**
    {"topic" : "HHB/ALARM", "datetime" : "2020-08-26T11:05:20-0600" , 
    * "deviceType" : 23 , "type" : "MOTION SENSOR" , 
    * "name" : "Family Room Motion" , 
    * "state" : "MOTION" , 
    * "duration" : 18, 
    * "MACAddress" : "000007AAAF" }

 * 
 * @author patrick.conroy
 */
public class HHBAlarmEvent extends MQTTMessage_POJO
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( HHBAlarmEvent.class );    

    @SerializedName("deviceType")       int                 deviceType;                 // HHB device type (eg. 23 = Motion Sensor)
    @SerializedName("type")             String              deviceTypeString;           // String of type (eg. "MOTION SENSOR")
    @SerializedName("name")             String              deviceName;                 // Sensor name (eg. "FRONT DOOR")
    @SerializedName("state")            String              deviceStatus;               // Sensor state (eg. "OPEN", "CLOSED", "MOTION", "NO MOTION")
    @SerializedName("duration")         int                 statusDuration;             // How long (seconds) the sensor has been in this state
    @SerializedName("MACAddress")       String              macAddress;                 // MAC Address of this sensor    


    
    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  HHBAlarmEvent ()
    {
        super();
    }
    
    // -------------------------------------------------------------------------
    public static HHBAlarmEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            HHBAlarmEvent  event = null;
            event = gson.fromJson( jsonMessage, HHBAlarmEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a HHBAlarmEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTypeString() {
        return deviceTypeString;
    }

    public void setDeviceTypeString(String deviceTypeString) {
        this.deviceTypeString = deviceTypeString;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public int getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(int statusDuration) {
        this.statusDuration = statusDuration;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    
    
}
