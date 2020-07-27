package com.tinampiniari.thesisproject.integration.stormglass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class History {

    @JsonProperty("hours")
    private List<Hours> hours;

    @JsonProperty("hours")
    public List<Hours> getHours() {
        return hours;
    }

    @JsonProperty("hours")
    public void setHours(List<Hours> hours) {
        this.hours = hours;
    }
}
