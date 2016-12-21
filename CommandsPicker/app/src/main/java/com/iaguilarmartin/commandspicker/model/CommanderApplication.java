package com.iaguilarmartin.commandspicker.model;

import android.app.Application;
import android.preference.PreferenceManager;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.activity.SettingsActivity;

import java.util.ArrayList;

// Extending default application object to store table information on it
// in order to be accesible all along the app context

public class CommanderApplication extends Application {

    private ArrayList<Table> mTables;

    @Override
    public void onCreate() {
        super.onCreate();

        int numberOfTables = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(SettingsActivity.PREFERENCE_NUMBER_OF_TABLES, Integer.parseInt(getString(R.string.config_number_of_tables)));
        setNumberOfTables(numberOfTables);
    }

    public void setNumberOfTables(int numberOfTables) {
        mTables = new ArrayList<>();
        for (int i = 0; i < numberOfTables; i++) {
            Table table = new Table(i + 1);
            mTables.add(table);
        }
    }

    public int getNumberOfTables() {
        return mTables.size();
    }

    public void addCourse(int tableNumber, Course course) {
        getTable(tableNumber).getCourses().addCourse(course);
    }

    public ArrayList<Table> getTables() {
        return mTables;
    }

    public Table getTable(int tableNumber) {
        return tableNumber > 0 && tableNumber <= mTables.size() ? mTables.get(tableNumber - 1) : null;
    }

    public Ticket generateTicket(int tableNumber) {
        return getTable(tableNumber).generateTicket();
    }

    public void closeTable(int tableNumber) {
        getTable(tableNumber).setCourses(new Courses());
    }
}
