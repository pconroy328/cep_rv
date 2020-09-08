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
 * { "topic":"WS2308/STATUS", "dateTime":"2020-09-08T10:03:55-0600" ,
 *      "tendency":"Rising " ,"forecast":"Sunny  " ,
 *      "indoorTemp":69.8 ,"indoorHumdity":45.0 ,
 *      "outdoorTemp":34.3 ,"outdoorHumdity":92.0 ,"outdoorPressure":30.2 ,"outdoorWindChill":27.8 ,
 *      "windSpeed":7.8 ,"windHeading":22.5 ,
 *      "rainLastHour":0.02 ,"rainLastDay":0.20 ,"rainTotal":3.32 ,
 *      "stationDate":"09/08/20" ,"stationTime":"10:03:00" }
 *
 * @author pconroy
 */
public class WeatherCurrentConditionsEvent extends MQTTMessage_POJO
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( WeatherCurrentConditionsEvent.class );    
    
    @SerializedName("tendency")         String  tendency;
    @SerializedName("forecast")         String  forecast;
    
    @SerializedName("indoorTemp")       float   indoorTemp;
    @SerializedName("indoorHumdity")    float   indoorHumdity;
    
    @SerializedName("outdoorTemp")      float   outdoorTemp;
    @SerializedName("outdoorHumdity")   float   outdoorHumdity;
    @SerializedName("outdoorPressure")  float   outdoorPressure;
    @SerializedName("outdoorWindChill") float   outdoorWindChill;
    
    @SerializedName("windSpeed")        float   windSpeed;
    @SerializedName("windHeading")      float   windHeading;
    
    @SerializedName("rainLastHour")     float   rainLastHour;
    @SerializedName("rainLastDay")      float   rainLastDay;
    @SerializedName("rainTotal")        float   rainTotal;
    
    @SerializedName("stationDate")      String  stationDate;
    @SerializedName("stationTime")      String  stationTime;
    
    
    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  WeatherCurrentConditionsEvent ()
    {
        super();
    }

    // -------------------------------------------------------------------------
    public static WeatherCurrentConditionsEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            WeatherCurrentConditionsEvent  event = null;
            event = gson.fromJson( jsonMessage, WeatherCurrentConditionsEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a WeatherCurrentConditionsEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    public String getTendency() {
        return tendency;
    }

    public void setTendency(String tendency) {
        this.tendency = tendency;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public float getIndoorTemp() {
        return indoorTemp;
    }

    public void setIndoorTemp(float indoorTemp) {
        this.indoorTemp = indoorTemp;
    }

    public float getIndoorHumdity() {
        return indoorHumdity;
    }

    public void setIndoorHumdity(float indoorHumdity) {
        this.indoorHumdity = indoorHumdity;
    }

    public float getOutdoorTemp() {
        return outdoorTemp;
    }

    public void setOutdoorTemp(float outdoorTemp) {
        this.outdoorTemp = outdoorTemp;
    }

    public float getOutdoorHumdity() {
        return outdoorHumdity;
    }

    public void setOutdoorHumdity(float outdoorHumdity) {
        this.outdoorHumdity = outdoorHumdity;
    }

    public float getOutdoorPressure() {
        return outdoorPressure;
    }

    public void setOutdoorPressure(float outdoorPressure) {
        this.outdoorPressure = outdoorPressure;
    }

    public float getOutdoorWindChill() {
        return outdoorWindChill;
    }

    public void setOutdoorWindChill(float outdoorWindChill) {
        this.outdoorWindChill = outdoorWindChill;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindHeading() {
        return windHeading;
    }

    public void setWindHeading(float windHeading) {
        this.windHeading = windHeading;
    }

    public float getRainLastHour() {
        return rainLastHour;
    }

    public void setRainLastHour(float rainLastHour) {
        this.rainLastHour = rainLastHour;
    }

    public float getRainLastDay() {
        return rainLastDay;
    }

    public void setRainLastDay(float rainLastDay) {
        this.rainLastDay = rainLastDay;
    }

    public float getRainTotal() {
        return rainTotal;
    }

    public void setRainTotal(float rainTotal) {
        this.rainTotal = rainTotal;
    }

    public String getStationDate() {
        return stationDate;
    }

    public void setStationDate(String stationDate) {
        this.stationDate = stationDate;
    }

    public String getStationTime() {
        return stationTime;
    }

    public void setStationTime(String stationTime) {
        this.stationTime = stationTime;
    }
    

}
