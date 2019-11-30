package com.example.wake.ethiocinemamovie.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wake.ethiocinemamovie.Models.MovieSchedule;
import com.example.wake.ethiocinemamovie.R;

import java.util.ArrayList;



public class MovieScheduleAdapter extends RecyclerView.Adapter<MovieScheduleAdapter.ScheduleViewHolder>{
    private ScheduleChosenListener listener;
    private ArrayList<MovieSchedule> schedules;


    public interface ScheduleChosenListener {
        public void onScheduleChosen(int position);
    }

    public MovieScheduleAdapter(ScheduleChosenListener listener, ArrayList<MovieSchedule> schedules) {
        this.listener = listener;
        this.schedules = schedules;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_schedule, parent, false);
        ScheduleViewHolder viewHolder = new ScheduleViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        final MovieSchedule schedule = schedules.get(position);
        holder.CinemaName.setText(schedule.getCinemaName());
        holder.SchedulePrice.setText(schedule.getPrice() + " ETB");
        holder.ScheduleTime.setText(schedule.getTime());
        holder.ScheduleDate.setText(schedule.getDate());
        holder.AvailableSeats.setText(schedule.getAvailableSeats());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView CinemaName, ScheduleDate, ScheduleTime, SchedulePrice, AvailableSeats;
        public ScheduleViewHolder(View itemView) {
            super(itemView);

            CinemaName = itemView.findViewById(R.id.scheduleCinemaName);
            ScheduleDate = itemView.findViewById(R.id.sche_date);
            SchedulePrice = itemView.findViewById(R.id.sche_price);
            ScheduleTime = itemView.findViewById(R.id.sche_time);
            AvailableSeats = itemView.findViewById(R.id.sche_seats);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.onScheduleChosen(getAdapterPosition());
                }
            });
        }
    }
}
