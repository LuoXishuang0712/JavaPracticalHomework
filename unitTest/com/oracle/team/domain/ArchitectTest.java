package com.oracle.team.domain;

import org.junit.Before;
import org.junit.Test;

public class ArchitectTest {
    private Architect architect = null;

    @Before
    public void updateBeforeClass(){
        architect = new Architect(1, "Test", 20, 123.456,
                new PC("TestMachine", "TestDisplay")
                , 234.56, 100);;
    }

    @Test
    public void testGetBonus() {
        System.out.println(architect.getBonus());
    }

    @Test
    public void testGetStatus() {
        System.out.println(architect.getStatus());
    }

    @Test
    public void testGetEquipment() {
        System.out.println(architect.getEquipment().getDescription());
    }

    @Test
    public void testGetMemberId() {
        System.out.println(architect.getMemberId());
    }

    @Test
    public void testGetId() {
        System.out.println(architect.getId());
    }

    @Test
    public void testTestGetName() {
        System.out.println(architect.getName());
    }

    @Test
    public void testGetAge() {
        System.out.println(architect.getAge());
    }

    @Test
    public void testGetSalary() {
        System.out.println(architect.getSalary());
    }

    @Test
    public void testGetStock() {
        System.out.println(architect.getStock());
    }
}