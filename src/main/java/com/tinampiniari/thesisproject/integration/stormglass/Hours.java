package com.tinampiniari.thesisproject.integration.stormglass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hours {

   @JsonProperty("airTemperature")
   private List<AirTemperature> airTemperature;

   @JsonProperty("time")
   private String time;

   private String date;

    @JsonProperty("airTemperature")
    public List<AirTemperature> getAirTemperature() {
        return airTemperature;
    }
    @JsonProperty("airTemperature")
    public void setAirTemperature(List<AirTemperature> airTemperature) {
        this.airTemperature = airTemperature;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return time.substring(0,9);
    }

    public void setDate(String date) {
        this.date = date;
    }
}
