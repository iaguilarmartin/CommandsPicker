package com.iaguilarmartin.commandspicker.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.fragment.TableDetailFragment;
import com.iaguilarmartin.commandspicker.fragment.TablesFragment;
import com.iaguilarmartin.commandspicker.model.Table;

public class MainActivity extends AppCompatActivity implements TablesFragment.OnTableSelectedListener, TableDetailFragment.OnTicketClosedListener {

    public final static String TABLE_NUMBER_EXTRA = "extraTableNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.tables_activity_title);
    }

    @Override
    public void onTableSelected(Table table) {
        if (table != null) {
            FrameLayout fragmentLayout = (FrameLayout) findViewById(R.id.table_detail_fragment);
            if (fragmentLayout != null) {
                // If current view contains a layout to display table information then
                // a TableDetailFragment is loaded inside it
                TableDetailFragment fragment = TableDetailFragment.newInstance(table.getNumber());
                getFragmentManager().beginTransaction().replace(R.id.table_detail_fragment, fragment).commit();
            } else {

                // Else table courses are displayed inside a new activity providing table
                // selected number
                Intent intent = new Intent(this, TableDetailActivity.class);
                intent.putExtra(TABLE_NUMBER_EXTRA, table.getNumber());

                startActivity(intent);
            }
        } else {

            // If no table is selected then fragment is removed from the view
            Fragment fragment = getFragmentManager().findFragmentById(R.id.table_detail_fragment);
            if (fragment != null) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

    @Override
    public void onTicketClosed(int tableNumber) {
        onTableSelected(null);
    }
}
