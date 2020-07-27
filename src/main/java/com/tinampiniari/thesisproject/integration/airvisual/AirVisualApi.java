package com.tinampiniari.thesisproject.integration.airvisual;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinampiniari.thesisproject.integration.ows.Coord;

import java.util.ArrayList;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirVisualApi {

    private String city;

    private String country;

    private Integer aqius;

    private String timestamp;

    private String state;

    private ArrayList<Coord> coordinates;

    private String color;

    private String title;

    private String description;

    public Integer getAqius() {
        return aqius;
    }

    public void setAqius(Integer aqius) {
        this.aqius = aqius;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Coord> getCoord() {
        return coordinates;
    }

    public void setCoord(ArrayList<Coord> coordinates) {
        this.coordinates = coordinates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackNested(Map<String, Object> data) {
        this.city = (String) data.get("city");
        this.country = (String) data.get("country");
        this.state = (String) data.get("state");
        Map<String, ArrayList<Coord>> location = (Map<String, ArrayList<Coord>>) data.get("location");
        this.coordinates = location.get("coordinates");
        Map<String, Object> current = (Map<String, Object>) data.get("current");
        Map<String, Object> pollution = (Map<String, Object>) current.get("pollution");
        this.aqius = (Integer) pollution.get("aqius");
        this.timestamp = (String) pollution.get("ts");

        if(this.aqius > 0 && this.aqius <= 50){
            this.color = "bg-green";
            this.title = "Good";
            this.description = "Air quality is considered satisfactory, and air pollution poses little or no risk";
        }
        else if(this.aqius > 50 && this.aqius <= 100){
            this.color = "bg-yellow";
            this.title = "Moderate";
            this.description = "Air quality is acceptable; however, for some pollutants there may be a moderate health concern for a very small number of people who are unusually sensitive to air pollution.";
        }
        else if(this.aqius > 100 && this.aqius <= 150){
            this.color = "bg-orange";
            this.title = "Unhealthy for sensitive groups";
            this.description = "Members of sensitive groups may experience health effects. The general public is not likely to be affected.";
        }
        else if (this.aqius > 150 && this.aqius <= 200) {
            this.color = "bg-deep-orange";
            this.title = "Unhealthy";
            this.description = "Everyone may begin to experience health effects; members of sensitive groups may experience more serious health effects";
        }
        else if (this.aqius > 200 && this.aqius <= 300) {
            this.color = "bg-red";
            this.title = "Very Unhealthy";
            this.description = "Health warnings of emergency conditions. The entire population is more likely to be affected.";
        }
        else if (this.aqius > 300) {
            this.color = "bg-deep-red";
            this.title = "Hazardous";
            this.description = "Health alert: everyone may experience more serious health effects.";
        }
    }

}
