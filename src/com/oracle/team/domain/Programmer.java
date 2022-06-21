package com.oracle.team.domain;

public class Programmer extends Employee {
    private Equipment equipment = null;
    private int memberId = 0;
    public final static int FREE = 0;
    public final static int BUSY = 1;
    public final static int VOCATION = 2;

    private int status = FREE;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Programmer(int id, String name, int age, double salary, Equipment equipment) {
        super(id, name, age, salary);
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
