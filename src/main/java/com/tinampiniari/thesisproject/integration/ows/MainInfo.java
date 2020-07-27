package com.tinampiniari.thesisproject.integration.ows;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MainInfo implements Serializable {

    @JsonProperty("temp")
    private Double temp;

    @JsonProperty("feels_like")
    private Double feels_like;

    @JsonProperty("temp_min")
    private Double temp_min;

    @JsonProperty("temp_max")
    private Double temp_max;

    @JsonProperty("pressure")
    private Integer pressure;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("sea_level")
    private Integer sea_level;

    @JsonProperty("grnd_level")
    private Integer grnd_level;

    @JsonProperty("temp")
    public Double getTemp() {
        return (double) Math.round(this.temp);
    }

    @JsonProperty("temp")
    public void setTemp(Double temp) {
        this.temp = temp;
    }

    @JsonProperty("feels_like")
    public Double getFeels_like() {
        return (double) Math.round(this.feels_like);
    }

    @JsonProperty("feels_like")
    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    @JsonProperty("temp_min")
    public Double getTemp_min() {
        return (double) Math.round(this.temp_min);
    }

    @JsonProperty("temp_min")
    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    @JsonProperty("temp_max")
    public Double getTemp_max() {
        return (double) Math.round(this.temp_max);
    }

    @JsonProperty("temp_max")
    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    @JsonProperty("pressure")
    public Integer getPressure() {
        return this.pressure;
    }

    @JsonProperty("pressure")
    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    @JsonProperty("humidity")
    public Integer getHumidity() {
        return this.humidity;
    }

    @JsonProperty("humidity")
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    @JsonProperty("sea_level")
    public Integer getSea_level() {
        return sea_level;
    }

    @JsonProperty("sea_level")
    public void setSea_level(Integer sea_level) {
        this.sea_level = sea_level;
    }

    @JsonProperty("grnd_level")
    public Integer getGrnd_level() {
        return grnd_level;
    }

    @JsonProperty("grnd_level")
    public void setGrnd_level(Integer grnd_level) {
        this.grnd_level = grnd_level;
    }
}
