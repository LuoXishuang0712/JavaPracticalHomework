package com.oracle.team.service;

import com.oracle.team.domain.Programmer;

import java.io.Serializable;
import java.util.ArrayList;

public class TeamServicePack implements Serializable {
    private int counter;
    private ArrayList<Programmer> team = null;

    public TeamServicePack(int counter, ArrayList<Programmer> team) {
        this.counter = counter;
        this.team = team;
    }

    public int getCounter() {
        return counter;
    }

    public ArrayList<Programmer> getTeam() {
        return team;
    }
}
