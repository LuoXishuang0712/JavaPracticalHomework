package com.oracle.team.service;

import com.oracle.team.domain.Programmer;

import java.io.Serializable;
import java.util.HashSet;

public class TeamServicePack implements Serializable {
    private int counter;
    private HashSet<Programmer> team = null;

    public TeamServicePack(int counter, HashSet<Programmer> team) {
        this.counter = counter;
        this.team = team;
    }

    public int getCounter() {
        return counter;
    }

    public HashSet<Programmer> getTeam() {
        return team;
    }
}
