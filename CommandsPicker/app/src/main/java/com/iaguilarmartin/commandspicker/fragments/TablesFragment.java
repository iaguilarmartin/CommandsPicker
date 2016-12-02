package com.iaguilarmartin.commandspicker.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.iaguilarmartin.commandspicker.adapters.TablesAdapter;
import com.iaguilarmartin.commandspicker.model.CommanderApplication;
import com.iaguilarmartin.commandspicker.model.Table;

public class TablesFragment extends Fragment {

    private GridView mGridView;
    private OnTableSelectedListener mOnTableSelectedListener;

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

                Table table = (Table) adapterView.getAdapter().getItem(i);
                if (mOnTableSelectedListener != null) {
                    mOnTableSelectedListener.onTableSelected(table);
                }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof  OnTableSelectedListener) {
            mOnTableSelectedListener = (OnTableSelectedListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof  OnTableSelectedListener) {
            mOnTableSelectedListener = (OnTableSelectedListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnTableSelectedListener = null;
    }

    private void updateTableList() {

        CommanderApplication app = (CommanderApplication) getActivity().getApplication();

        if (mGridView.getAdapter() == null || mGridView.getAdapter().getCount() != app.getNumberOfTables()) {
            TablesAdapter adapter = new TablesAdapter(getActivity(), R.layout.view_table, app.getTables());
            mGridView.setAdapter(adapter);
        }
    }

    public interface OnTableSelectedListener {
        void onTableSelected(Table table);
    }
}
