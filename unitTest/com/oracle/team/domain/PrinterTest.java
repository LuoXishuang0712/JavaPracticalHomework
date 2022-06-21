package com.oracle.team.domain;

import junit.framework.TestCase;

public class PrinterTest extends TestCase {

    public void testGetDescription() {
        Printer printer = new Printer("Test", "TestPrinter");
        System.out.println(printer.getDescription());
    }
}