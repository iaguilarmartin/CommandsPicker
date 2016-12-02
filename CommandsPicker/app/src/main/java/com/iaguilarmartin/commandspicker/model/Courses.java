package com.iaguilarmartin.commandspicker.model;

import android.content.Context;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.adapters.CoursesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by iaguilarmartin on 30/11/16.
 */

public class Courses implements Serializable {
    private ArrayList<Course> mStarters;
    private ArrayList<Course> mMainCourses;
    private ArrayList<Course> mDesserts;
    private ArrayList<String> mImages;

    public Courses() {
        mStarters = new ArrayList<Course>();
        mMainCourses = new ArrayList<Course>();
        mDesserts = new ArrayList<Course>();
    }

    public Courses(String json) throws JSONException {
        mImages = new ArrayList<String>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonAllergens = jsonObject.getJSONArray("allergens");

        HashMap<String, Allergen> allergens = new HashMap<String, Allergen>();
        for (int i = 0; i < jsonAllergens.length(); i++) {
            JSONObject jsonAllergen = (JSONObject) jsonAllergens.get(i);
            Allergen allergen = new Allergen();
            allergen.setId(jsonAllergen.getString("id"));
            allergen.setName(jsonAllergen.getString("name"));
            allergen.setFoundIn(jsonAllergen.getString("foundIn"));
            String allergenIcon = jsonAllergen.getString("icon");
            allergen.setIcon(allergenIcon);
            mImages.add(allergenIcon);

            allergens.put(allergen.getId(), allergen);
        }

        mStarters = processCourses(jsonObject.getJSONArray("starters"), allergens, Course.CourseType.starter);
        mMainCourses = processCourses(jsonObject.getJSONArray("mainCourses"), allergens, Course.CourseType.mainCourse);
        mDesserts = processCourses(jsonObject.getJSONArray("desserts"), allergens, Course.CourseType.dessert);
    }

    private ArrayList<Course> processCourses(JSONArray jsonCourses, HashMap<String, Allergen> allergens, Course.CourseType type) throws JSONException {
        ArrayList<Course> courses = new ArrayList<Course>();

        for (int i = 0; i < jsonCourses.length(); i++) {
            JSONObject jsonCourse = (JSONObject) jsonCourses.get(i);
            Course course = new Course();
            course.setType(type);
            course.setId(jsonCourse.getString("id"));
            course.setName(jsonCourse.getString("name"));
            course.setDescription(jsonCourse.getString("description"));
            course.setPrice(jsonCourse.getDouble("price"));

            String courseImage = jsonCourse.getString("image");
            course.setImage(courseImage);
            mImages.add(courseImage);

            JSONArray jsonAllergens = jsonCourse.getJSONArray("allergens");
            for (int z = 0; z < jsonAllergens.length(); z++) {
                String allergenId = (String) jsonAllergens.get(z);
                course.addAllergen(allergens.get(allergenId));
            }

            courses.add(course);
        }

        return courses;
    }

    public ArrayList<Course> getStarters() {
        return mStarters;
    }

    public ArrayList<Course> getMainCourses() {
        return mMainCourses;
    }

    public ArrayList<Course> getDesserts() {
        return mDesserts;
    }

    public ArrayList<CoursesAdapter.CourseAdapterItem> getArray(Context context) {
        ArrayList<CoursesAdapter.CourseAdapterItem> result = new ArrayList<CoursesAdapter.CourseAdapterItem>();

        result.add(new CourseCategory(context.getString(R.string.starters_title)));
        result.addAll(mStarters);
        result.add(new CourseCategory(context.getString(R.string.main_courses_title)));
        result.addAll(mMainCourses);
        result.add(new CourseCategory(context.getString(R.string.desserts_title)));
        result.addAll(mDesserts);

        return result;
    }

    public void addCourse(Course course) {

        ArrayList<Course> list;

        switch (course.getType()) {
            case mainCourse: {
                list = mMainCourses;
            } break;
            case dessert: {
                list = mDesserts;
            } break;
            default: {
                list = mStarters;
            } break;
        }

        list.add(course);
    }

    public ArrayList<String> getCoursesAndAllergensImages() {
        return mImages;
    }
}
