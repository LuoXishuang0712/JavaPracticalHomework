package com.oracle.team.domain;

import java.io.Serializable;

public class NoteBook implements Equipment, Serializable {
    private String model = null;
    private double price = 0.0;

    public NoteBook(String model, double price){
        this.model = model;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return this.model + "(" + this.price + ")";
    }
}
