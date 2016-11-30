package com.iaguilarmartin.commandspicker.model;

/**
 * Created by iaguilarmartin on 30/11/16.
 */

public class CourseCategory {
    private String mName;

    public CourseCategory() {
    }

    public CourseCategory(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
