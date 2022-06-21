package com.oracle.team.domain;

import junit.framework.TestCase;

public class NoteBookTest extends TestCase {

    public void testGetDescription() {
        NoteBook noteBook = new NoteBook("Test", 123.456);
        System.out.println(noteBook.getDescription());
    }
}