package com.iaguilarmartin.commandspicker.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by iaguilarmartin on 27/11/16.
 */

public class Course extends CourseCategory {
    private String mId;
    private String mDescription;
    private Double mPrice;
    private String mImage;
    private ArrayList<Allergen> mAllergens;

    public Course() {
        super();

        mAllergens = new ArrayList<Allergen>();
    }

    public Course(String id, String name, String description, Double price, String image, ArrayList<Allergen> allergens) {
        super(name);

        mId = id;
        mDescription = description;
        mPrice = price;
        mImage = image;
        mAllergens = allergens;
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

    public String getFormattedPrice() {
        return String.format("%.2f €", mPrice);
    }

    public void addAllergen(Allergen allergen) {
        mAllergens.add(allergen);
    }

    @Override
    public String toString() {
        return getName();
    }
}
