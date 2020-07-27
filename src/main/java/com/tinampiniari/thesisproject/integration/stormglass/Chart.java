package com.tinampiniari.thesisproject.integration.stormglass;

import java.io.Serializable;

public class Chart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer X;
    private Integer Y;
    private String Label;

   public Integer getX(){
       return X;
   }

   public void setX(Integer x) {
       X = x;
   }

    public Integer getY() {
        return Y;
    }

    public void setY(Integer y) {
        Y = y;
    }

    public String getLabel(){
        return Label;
    }

    public void setLabel(String label){
        Label = label;
    }

}
