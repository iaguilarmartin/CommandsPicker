package com.iaguilarmartin.commandspicker.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.adapters.TicketAdapter;
import com.iaguilarmartin.commandspicker.model.Ticket;
import com.iaguilarmartin.commandspicker.model.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by iaguilarmartin on 2/12/16.
 */

public class TicketFragment extends DialogFragment {

    private static final String ARG_TICKET = "ticket";

    private Ticket mTicket;

    public static TicketFragment newInstance(Ticket ticket) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_TICKET, ticket);

        TicketFragment ticketFragment = new TicketFragment();
        ticketFragment.setArguments(arguments);
        ticketFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return ticketFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTicket = (Ticket) getArguments().getSerializable(ARG_TICKET);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setTitle(R.string.ticket_dialog_title);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_ticket, container, false);

        TextView tvTableName = (TextView) root.findViewById(R.id.tableName);
        tvTableName.setText(String.format(getString(R.string.table_number_string), mTicket.getTableNumber()));

        TextView tvDate = (TextView) root.findViewById(R.id.ticketDate);
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date now = Calendar.getInstance().getTime();
        tvDate.setText(df.format(now));

        TextView tvTableTotal = (TextView) root.findViewById(R.id.tableTotal);
        tvTableTotal.setText(Utils.formatDoubleToPrice(mTicket.getTotal()));

        RecyclerView mList = (RecyclerView) root.findViewById(R.id.coursesList);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.setAdapter(new TicketAdapter(getActivity(), mTicket.getRows()));

        Button cancelBtn = (Button) root.findViewById(R.id.ticket_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                dismiss();
            }
        });

        Button closeTableBtn = (Button) root.findViewById(R.id.ticket_close);
        closeTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                dismiss();
            }
        });

        return root;
    }
}
