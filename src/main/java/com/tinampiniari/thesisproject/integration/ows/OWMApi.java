package com.tinampiniari.thesisproject.integration.ows;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coord",
        "weather",
        "main",
        "wind",
        "clouds",
        "dt",
        "sys",
        "timezone",
        "id",
        "name"
})
public class OWMApi {

    @JsonProperty("coord")
    private Coord coord;

    @JsonProperty("weather")
    public List<Weather> weather;

    @JsonProperty("main")
    private MainInfo main;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("rain")
    private Rain rain;

    @JsonProperty("snow")
    private Snow snow;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    private Instant dt;

    @JsonProperty("sys")
    private Sys sys;

    @JsonProperty("timezone")
    private Long timezone;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("coord")
    public Coord getCoord() {
        return this.coord;
    }

    @JsonProperty("weather")
    public List<Weather> getWeather() {
        return this.weather;
    }

    @JsonProperty("wind")
    public Wind getWind() {
        return this.wind;
    }

    @JsonProperty("rain")
    public Rain getRain() {
        return this.rain;
    }

    @JsonProperty("snow")
    public Snow getSnow() {
        return this.snow;
    }

    @JsonProperty("dt")
    public Instant getDt() {
        return this.dt;
    }

    @JsonProperty("sys")
    public Sys getSys() {
        return this.sys;
    }

    @JsonProperty("timezone")
    public Long getTimezone() {
        return this.timezone;
    }

    @JsonProperty("id")
    public Integer getId() {
        return this.id;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }



    public MainInfo getMain() {
        return this.main;
    }


    public Clouds getClouds() {
        return this.clouds;
    }



    public OWMApi(Coord coord, MainInfo main, Wind wind, Sys sys, String name) {
        this.coord = coord;
        this.main = main;
        this.wind = wind;
        this.sys = sys;
        this.name = name;
    }
}
