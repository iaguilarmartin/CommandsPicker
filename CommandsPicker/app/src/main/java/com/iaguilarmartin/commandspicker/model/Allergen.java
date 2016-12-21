package com.iaguilarmartin.commandspicker.model;

import java.io.Serializable;

public class Allergen implements Serializable {
    private String mId;
    private String mName;
    private String mFoundIn;
    private String mIcon;

    public Allergen() {
    }

    public Allergen(String id, String name, String foundIn, String icon) {
        mId = id;
        mName = name;
        mFoundIn = foundIn;
        mIcon = icon;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFoundIn() {
        return mFoundIn;
    }

    public void setFoundIn(String foundIn) {
        mFoundIn = foundIn;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }
}
