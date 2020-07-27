package com.tinampiniari.thesisproject.integration.ows;

import java.io.Serializable;
import java.util.List;

public class ForecastChart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer X;
    private List<Double> Y;
    private String Label;

    public Integer getX() {
        return X;
    }

    public void setX(Integer x) {
        X = x;
    }

    public List<Double> getY() {
        return Y;
    }

    public void setY(List<Double> y) {
        Y = y;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

}
