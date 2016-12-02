package com.iaguilarmartin.commandspicker.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.model.CommanderApplication;
import com.iaguilarmartin.commandspicker.model.Utils;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREFERENCE_NUMBER_OF_TABLES = "numberOfTables";

    EditText mEditText;
    CommanderApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(R.string.settings_menu_option);

        mApp = (CommanderApplication) getApplication();

        mEditText = (EditText) findViewById(R.id.numberOfTablesEdit);
        mEditText.setText(String.valueOf(mApp.getNumberOfTables()));

        Button addTableBtn = (Button) findViewById(R.id.addTableBtn);
        addTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfTables = Integer.parseInt(String.valueOf(mEditText.getText()));
                numberOfTables++;

                setNumberOfTables(numberOfTables);
            }
        });

        Button removeTableBtn = (Button) findViewById(R.id.removeTableBtn);
        removeTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfTables = Integer.parseInt(String.valueOf(mEditText.getText()));
                if (numberOfTables > 1) {
                    numberOfTables--;

                    setNumberOfTables(numberOfTables);
                }
            }
        });
    }

    private void setNumberOfTables(int numberOfTables) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putInt(PREFERENCE_NUMBER_OF_TABLES, numberOfTables).apply();
        mApp.setNumberOfTables(numberOfTables);
        mEditText.setText(String.valueOf(numberOfTables));
    }
}
