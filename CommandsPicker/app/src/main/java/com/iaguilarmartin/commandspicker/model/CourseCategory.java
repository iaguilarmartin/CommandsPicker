package com.iaguilarmartin.commandspicker.model;


import com.iaguilarmartin.commandspicker.adapters.CoursesAdapter;

/**
 * Created by iaguilarmartin on 2/12/16.
 */

public class CourseCategory implements CoursesAdapter.CourseAdapterItem {
    private String mName;

    public CourseCategory(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
