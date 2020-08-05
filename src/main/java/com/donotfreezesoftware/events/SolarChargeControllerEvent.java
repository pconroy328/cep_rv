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
 * POJO Class that represents this JSON data packet from the Solar Charge Controller"
 * 
 *  {   "topic":    "SCC/1/DATA",
        "version":    "3.0",
        "dateTime":    "2020-07-27T16:28:51-0600",
        "controllerDateTime":    "07/27/20 16:28:51",
        "isNightTime":    false,
        "loadIsOn":    true,
        "pvVoltage":    18.56,
        "pvCurrent":    1.3,
        "pvPower":    24.16,
        "pvStatus":    "Normal",
        "loadVoltage":    13.73,
        "loadCurrent":    0.34,
        "loadPower":    4.66,
        "loadLevel":    "Light",
        "loadControlMode":    "Manual",
        "batterySOC":    98,
        "batteryVoltage":    13.73,
        "batteryCurrent":    1.43,
        "batteryStatus":    "Normal",
        "batteryMaxVoltage":    15.02,
        "batteryMinVoltage":    12.94,
        "batteryChargingStatus":    "Floating",
        "batteryTemperature":    81,
        "controllerTemperature":    95.8,
        "chargerStatusNormal":    "No",
        "chargerRunning":    "Yes",
        "deviceArrayChargingStatusBits":    7,
        "energyConsumedToday":    0.07,
        "energyConsumedMonth":    2,
        "energyConsumedYear":    8.47,
        "energyConsumedTotal":    11.8,
        "energyGeneratedToday":    0.22,
        "energyGeneratedMonth":    6.88,
        "energyGeneratedYear":    44.24,
        "energyGeneratedTotal":    56.4
    }
 * 
 * @author pconroy
 */
public class SolarChargeControllerEvent extends MQTTMessage_POJO
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( SolarChargeControllerEvent.class );    
    
    //
    //  We're using Google GSON to parse the JSON
    //  If the variable name matches the key in the JSON, then Gson will know
    //  how to unpack the message. But sometimes - they don't match. So the
    //  '@SerialixedName()' annotation can be used to match up a JSON key with
    //  the variable
    //
    
    //
    //  These first three are common across all JSON messages we see, so they're in the
    //  superclass
    //@SerializedName("topic")                String      topic;
    //@SerializedName("version")              String      version;
    //@SerializedName("dateTime")             String      dateTimeStr;
    
    @SerializedName("controllerDateTime")       String      controllerDateTimeStr;

    @SerializedName("isNightTime")          boolean     isNightTime;
    @SerializedName("loadIsOn")             boolean     loadIsOn;

    @SerializedName("pvVoltage")            float       pvVoltage;
    @SerializedName("pvCurrent")            float       pvCurrent;
    @SerializedName("pvPower")              float       pvPower;
    @SerializedName("pvStatus")             String      pvStatus;

    @SerializedName("loadVoltage")          float       loadVoltage;
    @SerializedName("loadCurrent")          float       loadCurrent;
    @SerializedName("loadPower")            float       loadPower;
    @SerializedName("loadLevel")            String      loadLevel;
    @SerializedName("loadControlMode")      String      loadControlMode;

    @SerializedName("batterySOC")           int         batterySOC;
    @SerializedName("batteryVoltage")       float       batteryVoltage;
    @SerializedName("batteryCurrent")       float       batteryCurrent;
    @SerializedName("batteryStatus")        String      batteryStatus;
    @SerializedName("batteryMaxVoltage")    float       batteryMaxVoltage;
    @SerializedName("batteryMinVoltage")    float       batteryMinVoltage;
    @SerializedName("batteryChargingStatus")    String      batteryChargingStatus;
    @SerializedName("batteryTemperature")   float       batteryTemperature;

    @SerializedName("controllerTemperature")    float       controllerTemperature;
    @SerializedName("chargerStatusNormal")  String      chargerStatusNormal;
    @SerializedName("chargerRunning")       String      chargerRunning;

    @SerializedName("deviceArrayChargingStatusBits")    int         deviceArrayChargingStatusBits;

    @SerializedName("energyConsumedToday")  float       energyConsumedToday;
    @SerializedName("energyConsumedMonth")  float       energyConsumedMonth;
    @SerializedName("energyConsumedYear")   float       energyConsumedYear;
    @SerializedName("energyConsumedTotal")  float       energyConsumedTotal;
    @SerializedName("energyGeneratedToday") float       energyGeneratedToday;
    @SerializedName("energyGeneratedMonth") float       energyGeneratedMonth;
    @SerializedName("energyGeneratedYear")  float       energyGeneratedYear;
    @SerializedName("energyGeneratedTotal") float       energyGeneratedTotal;   

    
    // -------------------------------------------------------------------------
    public static SolarChargeControllerEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            SolarChargeControllerEvent  scce = null;
            scce = gson.fromJson( jsonMessage, SolarChargeControllerEvent.class );
            scce.setCreatedOn( System.currentTimeMillis() );
            scce.setJsonPayload( jsonMessage );
            
            return scce;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a SolarChargeControllerEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
    
    //
    //  NetBeans IDE Generated getters and setters
        
    //public String getTopic() {
    //    return super.topic;
    //}

    //public void setTopic(String topic) {
    //    this.topic = topic;
    //}

    //public String getVersion() {
    //    return version;
    //}

    //public void setVersion(String version) {
    //    this.version = version;
    //}

    //public String getDateTimeStr() {
    //    return dateTimeStr;
    //}

    //public void setDateTimeStr(String dateTimeStr) {
    //    this.dateTimeStr = dateTimeStr;
    //}

    public String getControllerDateTimeStr() {
        return controllerDateTimeStr;
    }

    public void setControllerDateTimeStr(String controllerDateTimeStr) {
        this.controllerDateTimeStr = controllerDateTimeStr;
    }

    public boolean isIsNightTime() {
        return isNightTime;
    }

    public void setIsNightTime(boolean isNightTime) {
        this.isNightTime = isNightTime;
    }

    public boolean isLoadIsOn() {
        return loadIsOn;
    }

    public void setLoadIsOn(boolean loadIsOn) {
        this.loadIsOn = loadIsOn;
    }

    public float getPvVoltage() {
        return pvVoltage;
    }

    public void setPvVoltage(float pvVoltage) {
        this.pvVoltage = pvVoltage;
    }

    public float getPvCurrent() {
        return pvCurrent;
    }

    public void setPvCurrent(float pvCurrent) {
        this.pvCurrent = pvCurrent;
    }

    public float getPvPower() {
        return pvPower;
    }

    public void setPvPower(float pvPower) {
        this.pvPower = pvPower;
    }

    public String getPvStatus() {
        return pvStatus;
    }

    public void setPvStatus(String pvStatus) {
        this.pvStatus = pvStatus;
    }

    public float getLoadVoltage() {
        return loadVoltage;
    }

    public void setLoadVoltage(float loadVoltage) {
        this.loadVoltage = loadVoltage;
    }

    public float getLoadCurrent() {
        return loadCurrent;
    }

    public void setLoadCurrent(float loadCurrent) {
        this.loadCurrent = loadCurrent;
    }

    public float getLoadPower() {
        return loadPower;
    }

    public void setLoadPower(float loadPower) {
        this.loadPower = loadPower;
    }

    public String getLoadLevel() {
        return loadLevel;
    }

    public void setLoadLevel(String loadLevel) {
        this.loadLevel = loadLevel;
    }

    public String getLoadControlMode() {
        return loadControlMode;
    }

    public void setLoadControlMode(String loadControlMode) {
        this.loadControlMode = loadControlMode;
    }

    public int getBatterySOC() {
        return batterySOC;
    }

    public void setBatterySOC(int batterySOC) {
        this.batterySOC = batterySOC;
    }

    public float getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(float batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public float getBatteryCurrent() {
        return batteryCurrent;
    }

    public void setBatteryCurrent(float batteryCurrent) {
        this.batteryCurrent = batteryCurrent;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public float getBatteryMaxVoltage() {
        return batteryMaxVoltage;
    }

    public void setBatteryMaxVoltage(float batteryMaxVoltage) {
        this.batteryMaxVoltage = batteryMaxVoltage;
    }

    public float getBatteryMinVoltage() {
        return batteryMinVoltage;
    }

    public void setBatteryMinVoltage(float batteryMinVoltage) {
        this.batteryMinVoltage = batteryMinVoltage;
    }

    public String getBatteryChargingStatus() {
        return batteryChargingStatus;
    }

    public void setBatteryChargingStatus(String batteryChargingStatus) {
        this.batteryChargingStatus = batteryChargingStatus;
    }

    public float getBatteryTemperature() {
        return batteryTemperature;
    }

    public void setBatteryTemperature(float batteryTemperature) {
        this.batteryTemperature = batteryTemperature;
    }

    public float getControllerTemperature() {
        return controllerTemperature;
    }

    public void setControllerTemperature(float controllerTemperature) {
        this.controllerTemperature = controllerTemperature;
    }

    public String getChargerStatusNormal() {
        return chargerStatusNormal;
    }

    public void setChargerStatusNormal(String chargerStatusNormal) {
        this.chargerStatusNormal = chargerStatusNormal;
    }

    public String getChargerRunning() {
        return chargerRunning;
    }

    public void setChargerRunning(String chargerRunning) {
        this.chargerRunning = chargerRunning;
    }

    public int getDeviceArrayChargingStatusBits() {
        return deviceArrayChargingStatusBits;
    }

    public void setDeviceArrayChargingStatusBits(int deviceArrayChargingStatusBits) {
        this.deviceArrayChargingStatusBits = deviceArrayChargingStatusBits;
    }

    public float getEnergyConsumedToday() {
        return energyConsumedToday;
    }

    public void setEnergyConsumedToday(float energyConsumedToday) {
        this.energyConsumedToday = energyConsumedToday;
    }

    public float getEnergyConsumedMonth() {
        return energyConsumedMonth;
    }

    public void setEnergyConsumedMonth(float energyConsumedMonth) {
        this.energyConsumedMonth = energyConsumedMonth;
    }

    public float getEnergyConsumedYear() {
        return energyConsumedYear;
    }

    public void setEnergyConsumedYear(float energyConsumedYear) {
        this.energyConsumedYear = energyConsumedYear;
    }

    public float getEnergyConsumedTotal() {
        return energyConsumedTotal;
    }

    public void setEnergyConsumedTotal(float energyConsumedTotal) {
        this.energyConsumedTotal = energyConsumedTotal;
    }

    public float getEnergyGeneratedToday() {
        return energyGeneratedToday;
    }

    public void setEnergyGeneratedToday(float energyGeneratedToday) {
        this.energyGeneratedToday = energyGeneratedToday;
    }

    public float getEnergyGeneratedMonth() {
        return energyGeneratedMonth;
    }

    public void setEnergyGeneratedMonth(float energyGeneratedMonth) {
        this.energyGeneratedMonth = energyGeneratedMonth;
    }

    public float getEnergyGeneratedYear() {
        return energyGeneratedYear;
    }

    public void setEnergyGeneratedYear(float energyGeneratedYear) {
        this.energyGeneratedYear = energyGeneratedYear;
    }

    public float getEnergyGeneratedTotal() {
        return energyGeneratedTotal;
    }

    public void setEnergyGeneratedTotal(float energyGeneratedTotal) {
        this.energyGeneratedTotal = energyGeneratedTotal;
    }
}
