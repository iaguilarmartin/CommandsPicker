package com.iaguilarmartin.commandspicker.model;

import com.iaguilarmartin.commandspicker.adapters.CoursesAdapter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by iaguilarmartin on 27/11/16.
 */

public class Course implements Serializable, CoursesAdapter.CourseAdapterItem {
    private String mName;
    private String mId;
    private String mDescription;
    private Double mPrice;
    private String mImage;
    private CourseType mType;
    private String mSuggestions = "";
    private ArrayList<Allergen> mAllergens;

    public Course() {
        super();

        mAllergens = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public ArrayList<Allergen> getAllergens() {
        return mAllergens;
    }

    public void setAllergens(ArrayList<Allergen> allergens) {
        mAllergens = allergens;
    }

    public void addAllergen(Allergen allergen) {
        mAllergens.add(allergen);
    }

    @Override
    public String toString() {
        return mName;
    }

    public String getSuggestions() {
        return mSuggestions;
    }

    public void setSuggestions(String suggestions) {
        mSuggestions = suggestions;
    }

    public CourseType getType() {
        return mType;
    }

    public void setType(CourseType type) {
        mType = type;
    }

    public enum CourseType {
        starter,
        mainCourse,
        dessert
    }
}
