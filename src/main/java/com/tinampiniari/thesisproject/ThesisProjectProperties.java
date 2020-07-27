package com.tinampiniari.thesisproject;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties("app.weather")
public class ThesisProjectProperties {

    @Valid
    private final Api api = new Api();


    private List<String> locations = Arrays.asList("Athens,GR","Prague,CZ");

    public Api getApi() {
        return this.api;
    }

    public List<String> getLocations() {
        return this.locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public static class Api{

        private String key;

        private String stormglasskey;

        private String airvisualKey;

        private String newsApiKey;

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getStormglasskey() {
            return stormglasskey;
        }

        public void setStormglasskey(String stormglasskey) {
            this.stormglasskey = stormglasskey;
        }

        public String getAirVisualKey(){
            return airvisualKey;
        }

        public void setAirVisualKey(String airvisualKey){
            this.airvisualKey = airvisualKey;
        }

        public String getNewsApiKey() {
            return newsApiKey;
        }

        public void setNewsApiKey(String newsApiKey) {
            this.newsApiKey = newsApiKey;
        }
    }


}
