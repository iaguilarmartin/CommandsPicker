package com.iaguilarmartin.commandspicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.model.Table;
import com.iaguilarmartin.commandspicker.R;

import java.util.List;


// This adapter is user to display tables inside the TablesFragment GridVIew
public class TablesAdapter extends ArrayAdapter<Table> {
    private int mTableResource;
    private Context mContext;

    public TablesAdapter(Context context, int resource, List<Table> tables) {
        super(context, android.R.layout.simple_list_item_1, tables);

        mContext = context;
        mTableResource = resource;
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
