package com.iaguilarmartin.commandspicker.model;


import com.iaguilarmartin.commandspicker.adapter.CoursesAdapter;

public class CourseCategory implements CoursesAdapter.CourseAdapterItem {
    private String mName;

    public CourseCategory(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
