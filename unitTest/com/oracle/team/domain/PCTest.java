package com.oracle.team.domain;

import junit.framework.TestCase;

public class PCTest extends TestCase {

    public void testGetDescription() {
        PC pc = new PC("Test", "testDisplay");
        System.out.println(pc.getDescription());
    }
}