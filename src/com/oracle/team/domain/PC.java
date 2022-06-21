package com.oracle.team.domain;

import java.io.Serializable;

public class PC implements Equipment, Serializable {
    private String model = null;
    private String display = null;

    public PC(String model, String display){
        this.model = model;
        this.display = display;
    }

    @Override
    public String getDescription() {
        return this.model + "(" + this.display + ")";
    }
}
