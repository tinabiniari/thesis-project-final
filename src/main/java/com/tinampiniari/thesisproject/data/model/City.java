package com.tinampiniari.thesisproject.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tinampiniari.thesisproject.integration.ows.Coord;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "country",
        "sunrise",
        "sunset"
})
public class City {

    @JsonProperty("id")
    public Long id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("coord")
    public Coord coord;

    @JsonProperty("country")
    public String country;

    @JsonProperty("sunrise")
    public Long sunrise;

    @JsonProperty("sunset")
    public Long sunset;

    @JsonProperty("name")
    public String getCity() {
        return name;
    }

    @JsonProperty("id")
    public Long getId() { return id;
    }

    @JsonProperty("id")
    public void setId(Long id) { this.id = id;
    }

    @JsonProperty("name")
    public void setCity(String name) {
        this.name = name;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("sunrise")
    public Long getSunrise() {
        return sunrise;
    }

    @JsonProperty("sunrise")
    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    @JsonProperty("sunset")
    public Long getSunset() {
        return sunset;
    }

    @JsonProperty("sunset")
    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    @JsonProperty("coord")
    public Coord getCoord() {
        return coord;
    }

    @JsonProperty("coord")
    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
