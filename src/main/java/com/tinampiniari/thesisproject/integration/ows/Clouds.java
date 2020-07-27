package com.tinampiniari.thesisproject.integration.ows;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
       "all"
})
public class Clouds {

    @JsonProperty("all")
    private String all;

    @JsonProperty("all")
    public String getAll() {
        return this.all;
    }

    @JsonProperty("all")
    public void setAll(String all) {
        this.all = all;
    }

}
