package com.tinampiniari.thesisproject.integration.ows;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tinampiniari.thesisproject.data.model.City;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "city",
        "list",
        "country",
        "population",
        "timezone",
        "sunrise",
        "sunset"

})
public class OWMForecast {

    @JsonProperty("city")
    private City city;

    @JsonProperty("list")
    private List<ListInfo> list;


    @JsonProperty("list")
    public List<ListInfo> getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(List<ListInfo> list) {
        this.list = list;
    }

    @JsonProperty("city")
    public City getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(City city) {
        this.city = city;
    }
}
