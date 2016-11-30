package com.iaguilarmartin.commandspicker.model;

import java.util.ArrayList;

/**
 * Created by iaguilarmartin on 27/11/16.
 */

public class Table {
    private int mNumber;
    private Courses mCourses;


    public Table(int number) {
        mNumber = number;
        mCourses = new Courses();
    }

    public Table(int number, Courses courses) {
        mNumber = number;
        mCourses = courses;
    }

    public int getNumber() {
        return mNumber;
    }

    public Courses getCourses() {
        return mCourses;
    }
}
