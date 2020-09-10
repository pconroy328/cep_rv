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
 * {"topic": "OBD/STATUS", "version": "1.0", "dateTime": "2020-09-10T11:11:02-0600", 
 *  "connected": false, 
 *  "adapterVoltage": 13.1, "ambientAirTemp": 0, "ambientPressure": 0, "coolantTemp": 0, 
 *  "distanceWithMIL": 0, "engineLoad": 0, "fuelLevel": 0, "RPM": 0, "speed": 0, 
 *  "throttlePostion": 0, "runTime": 0, "moduleVoltage": 0, "relativeThrottlePos": 0, "throttleActuator": 0}
 * @author pconroy
 */
public class OBD2StatusEvent extends MQTTMessage_POJO
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( OBD2StatusEvent.class );    
    
    @SerializedName("connected")            boolean     connected;
    @SerializedName("adapterVoltage")       float       adapterVoltage;
    @SerializedName("ambientAirTemp")       float       ambientAirTemp;
    @SerializedName("ambientPressure")      float       ambientPressure;
    
    @SerializedName("coolantTemp")          float       coolantTemp;
    @SerializedName("distanceWithMIL")      float       distanceWithMIL;
    @SerializedName("engineLoad")           float       engineLoad;
    @SerializedName("fuelLevel")            float       fuelLevel;
    @SerializedName("RPM")                  int         RPM;
    
    @SerializedName("speed")                float       speed;
    @SerializedName("throttlePostion")      float       throttlePostion;
    @SerializedName("runTime")              float       runTime;
    @SerializedName("moduleVoltage")        float       moduleVoltage;
    @SerializedName("relativeThrottlePos")  float       relativeThrottlePos;
    @SerializedName("throttleActuator")     float       throttleActuator;
    
    
    
    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  OBD2StatusEvent ()
    {
        super();
    }
    
    // -------------------------------------------------------------------------
    public static OBD2StatusEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            OBD2StatusEvent  event = null;
            event = gson.fromJson( jsonMessage, OBD2StatusEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a OBD2StatusEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public float getAdapterVoltage() {
        return adapterVoltage;
    }

    public void setAdapterVoltage(float adapterVoltage) {
        this.adapterVoltage = adapterVoltage;
    }

    public float getAmbientAirTemp() {
        return ambientAirTemp;
    }

    public void setAmbientAirTemp(float ambientAirTemp) {
        this.ambientAirTemp = ambientAirTemp;
    }

    public float getAmbientPressure() {
        return ambientPressure;
    }

    public void setAmbientPressure(float ambientPressure) {
        this.ambientPressure = ambientPressure;
    }

    public float getCoolantTemp() {
        return coolantTemp;
    }

    public void setCoolantTemp(float coolantTemp) {
        this.coolantTemp = coolantTemp;
    }

    public float getDistanceWithMIL() {
        return distanceWithMIL;
    }

    public void setDistanceWithMIL(float distanceWithMIL) {
        this.distanceWithMIL = distanceWithMIL;
    }

    public float getEngineLoad() {
        return engineLoad;
    }

    public void setEngineLoad(float engineLoad) {
        this.engineLoad = engineLoad;
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public int getRPM() {
        return RPM;
    }

    public void setRPM(int RPM) {
        this.RPM = RPM;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getThrottlePostion() {
        return throttlePostion;
    }

    public void setThrottlePostion(float throttlePostion) {
        this.throttlePostion = throttlePostion;
    }

    public float getRunTime() {
        return runTime;
    }

    public void setRunTime(float runTime) {
        this.runTime = runTime;
    }

    public float getModuleVoltage() {
        return moduleVoltage;
    }

    public void setModuleVoltage(float moduleVoltage) {
        this.moduleVoltage = moduleVoltage;
    }

    public float getRelativeThrottlePos() {
        return relativeThrottlePos;
    }

    public void setRelativeThrottlePos(float relativeThrottlePos) {
        this.relativeThrottlePos = relativeThrottlePos;
    }

    public float getThrottleActuator() {
        return throttleActuator;
    }

    public void setThrottleActuator(float throttleActuator) {
        this.throttleActuator = throttleActuator;
    }
}
