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
 * "topic" : "HHB/STATUS",  "datetime" : "2020-08-26T10:28:42-0600" , 
 * "deviceType" :  2 , "type" : "HOME KEY" ,  "name" : "Dad's Key" , 
 * "state" : "NA" , "duration" : 8 , 
 * "setAlarmAction" : "NA" , "unsetAlarmAction" : "NA" , "setCallAction" : "NA" , "unsetCallAction" : "NA" , 
 * "online" : "ONLINE" , "battery" : "BATTERY OK" , "triggered" : "CLEARED" , "MACAddress" : "0000011480" }
 * @author pconroy
 */
public class HHBStatusEvent extends MQTTMessage_POJO
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( HHBStatusEvent.class );    
    
    @SerializedName("datetime")         String              dateTimeString;         // need to override the superclass var
    
    @SerializedName("deviceType")       int                 deviceType;             // HHB device type (eg. 23 = Motion Sensor)
    @SerializedName("type")             String              deviceTypeString;       // String of type (eg. "MOTION SENSOR")
    @SerializedName("name")             String              deviceName;             // Sensor name (eg. "FRONT DOOR")
    @SerializedName("state")            String              deviceStatus;           // Sensor state (eg. "OPEN", "CLOSED", "MOTION", "NO MOTION")
    @SerializedName("duration")         int                 statusDuration;         // How long (seconds) the sensor has been in this state
    @SerializedName("setAlarmAction")   String              alarmOnSetting1;        // See Note 1
    @SerializedName("unsetAlarmAction") String              alarmOnSetting2;        // See Note 1
    @SerializedName("setCallAction")    String              callOnSetting1;         // See Note 2
    @SerializedName("unsetCallAction")  String              callOnSetting2;         // See Note 2
    @SerializedName("online")           String              deviceOnlineStr;        // "ONLINE" or "OFF-LINE"
    @SerializedName("battery")          String              batteryOKStr;           // "BATTERY OK" or "LOW BATTERY"
    @SerializedName("triggered")        String              triggeredStr;           // "CLEARED" or "TRIGGERED"
    @SerializedName("MACAddress")       String              macAddress;             // MAC Address of this sensor
    
    private boolean     deviceOnline;
    private boolean     batteryOK;
    private boolean     isTriggered;                        // cannot name this 'triggered' or you get a GSON error "multiple definitions'
    
    // ------------
    // Note 1:  The HHB System can be set to 'alarm' the Key FOB based on the sensor type and state/
    //  Eg. You can configure the System to alarm when a Door Sensor is 'OPEN' and you could 
    //  configure the System to alarm when a Door Sensor is 'CLOSED'.  A motion sensor could alarm
    //  on 'MOTION', a Tilt Sensor could alarm on 'TILT' and a power sensor could alarm on 'OFF'
    //  alarmSetting1 will be similar to "ALARM ON OPEN" and alarmSetting2 could be "NO ALARM ON CLOSED"
    //
    // Note 2: The system had (past tense) the capability to do the above but call into the Eaton
    //  servers and, in turn, call you or send a text message.  So everything said above if copied
    //  to these attributes, just replace 'ALARM' with 'CALL'.  For example the Tilt Sensor could be
    //  "CALL ON NO-TILT" for callOnSetting1 and "NO CALL ON TILT" for callOnSetting2

    //
    //  Here are the states form the HHB Source code:
    /*
        sensorType = "OPEN-CLOSE SENSOR";
        state = (deviceRecPtr->ocSensor->isOpen ? "OPEN" : "CLOSED");

        sensorType = "TILT SENSOR";
        state = (deviceRecPtr->tsSensor->isOpen ? "OPEN" : "CLOSED");
    
        sensorType = "MOTION SENSOR";   
        state = (deviceRecPtr->motSensor->motionDetected ? "MOTION" : "NO MOTION");

        sensorType = "WATER LEAK SENSOR";
        state = (deviceRecPtr->wlSensor->wetnessDetected ? "WET" : "DRY");

        sensorType = "POWER SENSOR";
        state = (deviceRecPtr->psSensor->isPowerOn ? "ON" : "OFF");
    */
    
    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  HHBStatusEvent ()
    {
        super();
    }
    
    // -------------------------------------------------------------------------
    public static HHBStatusEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            HHBStatusEvent  event = null;
            event = gson.fromJson( jsonMessage, HHBStatusEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a HHBStatusEvent Object (" + jsonMessage + ")", ex);
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

    public String getAlarmOnSetting1() {
        return alarmOnSetting1;
    }

    public void setAlarmOnSetting1(String alarmOnSetting1) {
        this.alarmOnSetting1 = alarmOnSetting1;
    }

    public String getAlarmOnSetting2() {
        return alarmOnSetting2;
    }

    public void setAlarmOnSetting2(String alarmOnSetting2) {
        this.alarmOnSetting2 = alarmOnSetting2;
    }

    public String getCallOnSetting1() {
        return callOnSetting1;
    }

    public void setCallOnSetting1(String callOnSetting1) {
        this.callOnSetting1 = callOnSetting1;
    }

    public String getCallOnSetting2() {
        return callOnSetting2;
    }

    public void setCallOnSetting2(String callOnSetting2) {
        this.callOnSetting2 = callOnSetting2;
    }

    public String getDeviceOnlineStr() {
        return deviceOnlineStr;
    }

    public void setDeviceOnlineStr(String deviceOnlineStr) {
        this.deviceOnlineStr = deviceOnlineStr;
        this.deviceOnline = deviceOnlineStr.contains( "ON" );
    }

    public String getBatteryOKStr() {
        return batteryOKStr;
    }

    public void setBatteryOKStr(String batteryOKStr) {
        this.batteryOKStr = batteryOKStr;
        this.batteryOK = batteryOKStr.contains( "OK" );
    }

    public String getTriggeredStr() {
        return triggeredStr;
    }

    public void setTriggeredStr(String triggeredStr) {
        this.triggeredStr = triggeredStr;
        this.isTriggered = triggeredStr.contains( "TRIG" );
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    public boolean isDeviceOnline() {
        return deviceOnline;
    }

    public void setDeviceOnline(boolean deviceOnline) {
        this.deviceOnline = deviceOnline;
    }

    public boolean isBatteryOK() {
        return batteryOK;
    }

    public void setBatteryOK(boolean batteryOK) {
        this.batteryOK = batteryOK;
    }

    public boolean isIsTriggered() {
        return isTriggered;
    }

    public void setIsTriggered(boolean isTriggered) {
        this.isTriggered = isTriggered;
    }
}
