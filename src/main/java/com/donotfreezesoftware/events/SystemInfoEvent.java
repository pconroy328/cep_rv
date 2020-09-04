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
 *  Captures this JSON payload
 * {"topic": "NODE", "host": "mqttrv", "eth0 IP": "?.?.?.?", "wlan0 IP": "192.168.7.1", "wlan1 IP": "10.0.0.39", "booted": "2020-08-06 23:59:10", 
 * "uptime": "10:10:56.760000", "cpu_pct": 0.2, "cpu_temp": 113.18, "cpu_cnt": 4, "root_size": "15.3", "root_percent": 23.7, 
 * "xmt_errors": 0, "rcv_errors": 0, "ssid": "None", "signal_strength": "-79 dBm", "model": "3B 1.2 1GB Embest", "camera_present": false}

 * NB: Missing version and dateTime,
 * make root_size a float
 * @author pconroy
 */
public class SystemInfoEvent extends MQTTMessage_POJO
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( SystemInfoEvent.class );    

    @SerializedName("host")             String  host;
    @SerializedName("eth0 IP")          String  eth0_IP;
    @SerializedName("wlan0 IP")         String  wlan0_IP;
    @SerializedName("wlan1 IP")         String  wlan1_IP;
    @SerializedName("booted")           String  bootrimeString;
    @SerializedName("uptime")           String  uptimeString;
    @SerializedName("cpu_pct")          float   cpuUtilization;
    @SerializedName("cpu_temp")         float   cpuTemperature;
    @SerializedName("cpu_cnt")          int     numCPUs;
    @SerializedName("root_size")        String  rootSizeStr;
    @SerializedName("root_percent")     float   rootPercentUsed;
    @SerializedName("xmt_errors")       int     xmt_errors;
    @SerializedName("rcv_errors")       int     rcv_errors;
    @SerializedName("ssid")             String  ssid;
    @SerializedName("signal_strength")  String  signal_strength;
    @SerializedName("model")            String  model;
    @SerializedName("camera_present")   boolean camera_present;

    
    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  SystemInfoEvent ()
    {
        super();
    }
    
    // -------------------------------------------------------------------------
    public static SystemInfoEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            SystemInfoEvent  event = null;
            event = gson.fromJson( jsonMessage, SystemInfoEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a SystemInfoEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEth0_IP() {
        return eth0_IP;
    }

    public void setEth0_IP(String eth0_IP) {
        this.eth0_IP = eth0_IP;
    }

    public String getWlan0_IP() {
        return wlan0_IP;
    }

    public void setWlan0_IP(String wlan0_IP) {
        this.wlan0_IP = wlan0_IP;
    }

    public String getWlan1_IP() {
        return wlan1_IP;
    }

    public void setWlan1_IP(String wlan1_IP) {
        this.wlan1_IP = wlan1_IP;
    }

    public String getBootrimeString() {
        return bootrimeString;
    }

    public void setBootrimeString(String bootrimeString) {
        this.bootrimeString = bootrimeString;
    }

    public String getUptimeString() {
        return uptimeString;
    }

    public void setUptimeString(String uptimeString) {
        this.uptimeString = uptimeString;
    }

    public float getCpuUtilization() {
        return cpuUtilization;
    }

    public void setCpuUtilization(float cpuUtilization) {
        this.cpuUtilization = cpuUtilization;
    }

    public float getCpuTemperature() {
        return cpuTemperature;
    }

    public void setCpuTemperature(float cpuTemperature) {
        this.cpuTemperature = cpuTemperature;
    }

    public int getNumCPUs() {
        return numCPUs;
    }

    public void setNumCPUs(int numCPUs) {
        this.numCPUs = numCPUs;
    }

    public String   getRootSizeStr() {
        return rootSizeStr;
    }

    public void setRootSizeStr(String rootSizeStr) {
        this.rootSizeStr = rootSizeStr;
    }

    public float getRootPercentUsed() {
        return rootPercentUsed;
    }

    public void setRootPercentUsed(float rootPercentUsed) {
        this.rootPercentUsed = rootPercentUsed;
    }

    public int getXmt_errors() {
        return xmt_errors;
    }

    public void setXmt_errors(int xmt_errors) {
        this.xmt_errors = xmt_errors;
    }

    public int getRcv_errors() {
        return rcv_errors;
    }

    public void setRcv_errors(int rcv_errors) {
        this.rcv_errors = rcv_errors;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSignal_strength() {
        return signal_strength;
    }

    public void setSignal_strength(String signal_strength) {
        this.signal_strength = signal_strength;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isCamera_present() {
        return camera_present;
    }

    public void setCamera_present(boolean camera_present) {
        this.camera_present = camera_present;
    }
    
}
