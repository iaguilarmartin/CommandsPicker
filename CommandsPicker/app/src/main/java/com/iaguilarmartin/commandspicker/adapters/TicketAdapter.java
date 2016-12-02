package com.iaguilarmartin.commandspicker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.model.Ticket;
import com.iaguilarmartin.commandspicker.model.Utils;

import java.util.ArrayList;

/**
 * Created by iaguilarmartin on 2/12/16.
 */

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private Context mContext;
    private ArrayList<Ticket.TicketRow> mRows;

    public TicketAdapter(Context context, ArrayList<Ticket.TicketRow> rows) {
        super();

        mContext = context;
        mRows = rows;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_ticket_row, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        Ticket.TicketRow row = mRows.get(position);
        holder.bindTicketRow(row);
    }

    @Override
    public int getItemCount() {
        return mRows.size();
    }

    protected class TicketViewHolder extends RecyclerView.ViewHolder {

        private TextView mTVRowText;
        private TextView mTVRowTotal;

        public TicketViewHolder(View itemView) {
            super(itemView);

            mTVRowText = (TextView) itemView.findViewById(R.id.ticketRowText);
            mTVRowTotal = (TextView) itemView.findViewById(R.id.ticketRowTotal);
        }

        public void bindTicketRow(Ticket.TicketRow row) {
            mTVRowText.setText(row.toString());
            mTVRowTotal.setText(Utils.formatDoubleToPrice(row.getTotal()));
        }
    }
}
