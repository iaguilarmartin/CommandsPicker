package com.iaguilarmartin.commandspicker.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.activities.CoursesActivity;
import com.iaguilarmartin.commandspicker.activities.MainActivity;
import com.iaguilarmartin.commandspicker.adapters.CoursesAdapter;
import com.iaguilarmartin.commandspicker.model.CommanderApplication;
import com.iaguilarmartin.commandspicker.model.Table;

import java.util.ArrayList;

/**
 * Created by iaguilarmartin on 1/12/16.
 */

public class TableDetailFragment extends Fragment {

    private static final String ARG_TABLE_NUMBER = "argTableNUmber";
    public static final int CLOSE_TABLE_RESULT = 0;

    ListView mListView;
    int mTableNumber;
    CommanderApplication mApp;

    public static TableDetailFragment newInstance(int tableNumber) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_TABLE_NUMBER, tableNumber);

        TableDetailFragment tableDetailFragment = new TableDetailFragment();
        tableDetailFragment.setArguments(arguments);

        return tableDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mTableNumber = getArguments().getInt(ARG_TABLE_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mApp = (CommanderApplication) getActivity().getApplication();

        View root = inflater.inflate(R.layout.fragment_table_detail, container, false);
        mListView = (ListView) root.findViewById(R.id.tableCoursesList);

        TextView tvTableName = (TextView) root.findViewById(R.id.tableNameText);
        if (getActivity() instanceof MainActivity) {
            tvTableName.setText(String.format(getString(R.string.table_number_string), mTableNumber));
        } else {
            tvTableName.setVisibility(View.GONE);

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mListView
                    .getLayoutParams();

            mlp.setMargins(0, 0, 0, 0);
        }

        FloatingActionButton addCourseBtn = (FloatingActionButton) root.findViewById(R.id.course_add_button);
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CoursesActivity.class);
                intent.putExtra(CoursesActivity.EXTRA_TABLE_NUMBER, mTableNumber);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        updateTableCourses();
    }

    private void updateTableCourses() {

        Table table = mApp.getTable(mTableNumber);
        if (table != null) {
            ArrayList<CoursesAdapter.CourseAdapterItem> courses = table.getCourses().getArray(getActivity());

            if (mListView.getAdapter() == null || mListView.getAdapter().getCount() != courses.size()) {
                CoursesAdapter adapter = new CoursesAdapter(getActivity(), R.layout.list_item_course_simple, courses);
                mListView.setAdapter(adapter);
            }
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.table_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.close_table_option) {
            TicketFragment ticketFragment = TicketFragment.newInstance(mApp.generateTicket(mTableNumber));
            ticketFragment.setTargetFragment(this, CLOSE_TABLE_RESULT);
            ticketFragment.show(getFragmentManager(), null);
        }

        return superValue;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.close_table_option);
        item.setEnabled(mListView.getAdapter() != null && mListView.getAdapter().getCount() > 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CLOSE_TABLE_RESULT && resultCode == Activity.RESULT_OK) {
            mApp.closeTable(mTableNumber);
            getActivity().finish();
        }
    }
}
