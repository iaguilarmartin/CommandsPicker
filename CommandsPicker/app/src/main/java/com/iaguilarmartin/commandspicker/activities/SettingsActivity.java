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
import com.iaguilarmartin.commandspicker.model.Utils;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREFERENCE_NUMBER_OF_TABLES = "numberOfTables";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Utils.initializeToolbar(this, R.string.settings_menu_option);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int numberOfTables = prefs.getInt(PREFERENCE_NUMBER_OF_TABLES, Integer.parseInt(getString(R.string.config_number_of_tables)));

        final EditText numberOfTablesEdit = (EditText) findViewById(R.id.numberOfTablesEdit);
        numberOfTablesEdit.setText(String.valueOf(numberOfTables));

        Button addTableBtn = (Button) findViewById(R.id.addTableBtn);
        addTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfTables = Integer.parseInt(String.valueOf(numberOfTablesEdit.getText()));
                numberOfTables++;
                numberOfTablesEdit.setText(String.valueOf(numberOfTables));

                prefs.edit().putInt(PREFERENCE_NUMBER_OF_TABLES, numberOfTables).apply();
            }
        });

        Button removeTableBtn = (Button) findViewById(R.id.removeTableBtn);
        removeTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfTables = Integer.parseInt(String.valueOf(numberOfTablesEdit.getText()));
                if (numberOfTables > 1) {
                    numberOfTables--;
                    numberOfTablesEdit.setText(String.valueOf(numberOfTables));

                    prefs.edit().putInt(PREFERENCE_NUMBER_OF_TABLES, numberOfTables).apply();
                }
            }
        });
    }
}
