package com.iaguilarmartin.commandspicker.activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.fragments.TableDetailFragment;

public class TableDetailActivity extends AppCompatActivity {

    private int mTableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);

        mTableNumber = getIntent().getIntExtra(MainActivity.TABLE_NUMBER_EXTRA, -1);
        setTitle(String.format(getString(R.string.table_number_string), mTableNumber));

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment_table_detail) == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_table_detail, TableDetailFragment.newInstance(mTableNumber))
                    .commit();
        }
    }
}
