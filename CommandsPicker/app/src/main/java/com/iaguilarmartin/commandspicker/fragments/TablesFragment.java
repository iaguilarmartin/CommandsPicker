package com.iaguilarmartin.commandspicker.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.activities.SettingsActivity;
import com.iaguilarmartin.commandspicker.activities.TableDetailActivity;
import com.iaguilarmartin.commandspicker.adapters.TablesAdapter;


public class TablesFragment extends Fragment {

    private GridView mGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tables, container, false);

        mGridView = (GridView) root.findViewById(R.id.gridTables);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TableDetailActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        updateTableList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.tables_activity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.settings_option: {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            } break;
        }

        return true;
    }

    private void updateTableList() {
        int numberOfTables = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(SettingsActivity.PREFERENCE_NUMBER_OF_TABLES, Integer.parseInt(getString(R.string.config_number_of_tables)));

        if (mGridView.getAdapter() == null || mGridView.getAdapter().getCount() != numberOfTables) {
            TablesAdapter adapter = new TablesAdapter(getActivity(), R.layout.view_table, numberOfTables);
            mGridView.setAdapter(adapter);
        }
    }
}
