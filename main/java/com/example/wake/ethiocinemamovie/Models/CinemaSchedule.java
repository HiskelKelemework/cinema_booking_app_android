package com.example.wake.ethiocinemamovie.Models;

import java.util.Date;



public class CinemaSchedule {
    private String movieName;
    private String movieId;
    private String time;
    private Date date;
    private String price;
    private String cinemaId;
    private String id;

    public CinemaSchedule(String id, String movieName, String movieId, String time, Date date, String price) {
        this.movieName = movieName;
        this.movieId = movieId;
        this.time = time;
        this.date = date;
        this.price = price;
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTime() {
        return time + ":00 PM";
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDate();
    }

    public String getId() {
        return id;
    }
}
