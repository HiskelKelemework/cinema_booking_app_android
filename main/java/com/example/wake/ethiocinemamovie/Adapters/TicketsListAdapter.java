package com.example.wake.ethiocinemamovie.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wake.ethiocinemamovie.Models.Ticket;
import com.example.wake.ethiocinemamovie.R;

import java.util.ArrayList;

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.TicketViewHolder> {

    private ArrayList<Ticket> tickets;
    private Context ctx;

    RecyclerView recyclerView;
    CinemaListAdapter adapter;

    public TicketsListAdapter(ArrayList<Ticket> tickets, Context ctx) {
        this.tickets = tickets;
        this.ctx = ctx;
    }


    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket, parent, false);
        TicketViewHolder viewHolder = new TicketViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TicketViewHolder holder, final int position) {
        final Ticket currentTicket = tickets.get(position);
        holder.movieName.setText(currentTicket.getMovieName());
        holder.cinemaName.setText(currentTicket.getCinemaName());
        holder.dateTime.setText(currentTicket.getTime());
        holder.verificationCode.setText(currentTicket.getVerificationCode());
        holder.daysLeft.setText("" + currentTicket.getDaysLeft());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder{
        public TextView movieName, cinemaName, dateTime, verificationCode, daysLeft;
        public View view;

        public TicketViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            movieName = itemView.findViewById(R.id.ticket_movieName);
            cinemaName = itemView.findViewById(R.id.ticket_place);
            dateTime = itemView.findViewById(R.id.ticket_time);
            verificationCode = itemView.findViewById(R.id.ticket_verification);
            daysLeft = itemView.findViewById(R.id.ticket_daysLeft);
        }

    }
}

