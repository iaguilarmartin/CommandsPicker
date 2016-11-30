package com.iaguilarmartin.commandspicker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.model.Table;
import com.iaguilarmartin.commandspicker.R;

/**
 * Created by iaguilarmartin on 27/11/16.
 */

public class TablesAdapter extends ArrayAdapter {
    int mTableResource;
    Context mContext;

    public TablesAdapter(Context context, int resource, int numberOfTables) {
        super(context, android.R.layout.simple_list_item_1);

        mContext = context;

        mTableResource = resource;

        for (int i = 0; i < numberOfTables; i++) {
            Table table = new Table(i + 1);
            add(table);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(mTableResource, null);

        Table table = (Table) getItem(position);

        TextView tvTableNumber = (TextView) v.findViewById(R.id.tableNumberText);
        tvTableNumber.setText(String.valueOf(table.getNumber()));

        return v;
    }
}
