package com.example.wake.ethiocinemamovie.Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovieSchedule {
    private String movieId;
    private String cinemaId;
    private String cinemaName;
    private String price;
    private Date date;
    private String time;
    private String id;
    private int availableSeats;


    // I beleive it should be organised in few days


    public MovieSchedule(String id, String movieId, String cinemaId, String cinemaName,
                         String price, Date date, String time, int availableSeats) {
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.price = price;
        this.date = date;
        this.time = time;
        this.id = id;
        this.availableSeats = availableSeats;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDate();
    }

    public String getTime() {
        return time + ":00 PM";
    }

    public String getId() {
        return id;
    }

    public String getAvailableSeats() {
        return availableSeats + " seats";
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
