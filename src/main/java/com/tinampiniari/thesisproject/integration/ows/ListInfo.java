package com.tinampiniari.thesisproject.integration.ows;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dt",
        "main",
        "weather",
        "clouds",
        "wind",
        "dt_txt"
})
public class ListInfo {

    @JsonProperty("dt")
    private Long dt;

    @JsonProperty("main")
    private MainInfo main;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("wind")
    private Wind wind;


    @JsonProperty("dt_txt")
    private String dt_txt;

    @JsonProperty("dt")
    public Long getDt() {
        return dt;
    }

    @JsonProperty("dt")
    public void setDt(Long dt) {
        this.dt = dt*1000;

    }

    @JsonProperty("main")
    public MainInfo getMain() {
        return main;
    }

    @JsonProperty("main")
    public void setMain(MainInfo main) {
        this.main = main;
    }

    @JsonProperty("weather")
    public List<Weather> getWeather() {
        return weather;
    }

    @JsonProperty("weather")
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    @JsonProperty("clouds")
    public Clouds getClouds() {
        return clouds;
    }

    @JsonProperty("clouds")
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    @JsonProperty("wind")
    public Wind getWind() {
        return wind;
    }

    @JsonProperty("wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }


    @JsonProperty("dt_txt")
    public String getDt_txt() {
        return dt_txt;
    }

    @JsonProperty("dt_txt")
    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public String getDate(){
        String[] parts = getDt_txt().split(" ");
        String date = parts[0];
        return date;
    }

    public String getTime(){
        String[] parts = getDt_txt().split(" ");
        String date = parts[1];
        return date;
    }

}
