package com.iaguilarmartin.commandspicker.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.model.CommanderApplication;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREFERENCE_NUMBER_OF_TABLES = "numberOfTables";

    private EditText mEditText;
    private CommanderApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.settings_menu_option);

        // Getting custom application singleton
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superResult = super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return superResult;
    }

    // This function updates number of tables inside application shared preferences
    // and regenerate table information inside Application Singleton

    private void setNumberOfTables(int numberOfTables) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putInt(PREFERENCE_NUMBER_OF_TABLES, numberOfTables).apply();
        mApp.setNumberOfTables(numberOfTables);
        mEditText.setText(String.valueOf(numberOfTables));
    }
}
