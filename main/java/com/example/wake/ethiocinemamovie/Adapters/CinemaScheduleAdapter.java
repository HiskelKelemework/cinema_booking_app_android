package com.example.wake.ethiocinemamovie.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wake.ethiocinemamovie.Models.CinemaSchedule;
import com.example.wake.ethiocinemamovie.Models.MovieSchedule;
import com.example.wake.ethiocinemamovie.R;

import java.util.ArrayList;

public class CinemaScheduleAdapter extends RecyclerView.Adapter<CinemaScheduleAdapter.ScheduleViewHolder>{
    private com.example.wake.ethiocinemamovie.Adapters.MovieScheduleAdapter.ScheduleChosenListener listener;
    private ArrayList<CinemaSchedule> schedules;


    public interface ScheduleChosenListener {
        public void onScheduleChosen(int position);
    }

    public CinemaScheduleAdapter(MovieScheduleAdapter.ScheduleChosenListener listener, ArrayList<CinemaSchedule> schedules) {
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
        final CinemaSchedule schedule = schedules.get(position);
        holder.MovieName.setText(schedule.getMovieName());
        holder.SchedulePrice.setText(schedule.getPrice() + " ETB");
        holder.ScheduleTime.setText(schedule.getTime());
        holder.ScheduleDate.setText(schedule.getDate());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView MovieName, ScheduleDate, ScheduleTime, SchedulePrice;
        public ScheduleViewHolder(View itemView) {
            super(itemView);

            MovieName = itemView.findViewById(R.id.scheduleCinemaName);
            ScheduleDate = itemView.findViewById(R.id.sche_date);
            SchedulePrice = itemView.findViewById(R.id.sche_price);
            ScheduleTime = itemView.findViewById(R.id.sche_time);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.onScheduleChosen(getAdapterPosition());
                }
            });
        }
    }
}
