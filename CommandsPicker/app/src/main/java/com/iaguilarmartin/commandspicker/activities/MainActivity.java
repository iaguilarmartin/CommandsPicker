package com.iaguilarmartin.commandspicker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.fragments.TablesFragment;
import com.iaguilarmartin.commandspicker.model.Table;

public class MainActivity extends AppCompatActivity implements TablesFragment.OnTableSelectedListener {

    public final static String TABLE_NUMBER_EXTRA = "extraTableNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.tables_activity_title);
    }

    @Override
    public void onTableSelected(Table table) {
        Intent intent = new Intent(this, TableDetailActivity.class);
        intent.putExtra(TABLE_NUMBER_EXTRA, table.getNumber());

        startActivity(intent);
    }
}
