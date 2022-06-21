package com.oracle.team.domain;

import java.io.Serializable;

public class Printer implements Equipment, Serializable {
    private String type;
    private String name;

    public Printer(String type, String name){
        this.type = type;
        this.name = name;
    }

    @Override
    public String getDescription() {
        return this.type + "(" + this.name + ")";
    }
}
