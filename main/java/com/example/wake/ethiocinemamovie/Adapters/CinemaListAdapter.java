package com.example.wake.ethiocinemamovie.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wake.ethiocinemamovie.Models.Cinema;
import com.example.wake.ethiocinemamovie.Models.Movie;
import com.example.wake.ethiocinemamovie.R;
import com.example.wake.ethiocinemamovie.MoviesActivity;
import com.example.wake.ethiocinemamovie.SingleMovieActivity;

import java.util.ArrayList;


public class CinemaListAdapter extends RecyclerView.Adapter<CinemaListAdapter.CinemaViewHolder> {

    private ArrayList<Cinema> cinemas;
    private Context ctx;
    private CinemaChosenListener listener;

    RecyclerView recyclerView;
    CinemaListAdapter adapter;

    public CinemaListAdapter(ArrayList<Cinema> cinemas, Context ctx, CinemaChosenListener listener) {
        this.cinemas = cinemas;
        this.ctx = ctx;
        this.listener = listener;
    }

    public interface CinemaChosenListener {
        public void onCinemaChosen(int position);
    }

    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_cinema, parent, false);
        CinemaViewHolder viewHolder = new CinemaViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CinemaViewHolder holder, final int position) {
        final Cinema currentCinema = cinemas.get(position);
        holder.title.setText(currentCinema.getName());
        holder.address.setText(currentCinema.getAddress());
        holder.view.findViewById(R.id.singleCinemaClickable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCinemaChosen(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    public class CinemaViewHolder extends RecyclerView.ViewHolder{
        public TextView title, address;
        public ImageView image;
        public View view;

        public CinemaViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            title = itemView.findViewById(R.id.cinema_title);
            address = itemView.findViewById(R.id.cinema_address);
            image = itemView.findViewById(R.id.cinema_pic);
        }

    }
}
