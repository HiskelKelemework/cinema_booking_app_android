package com.example.wake.ethiocinemamovie.Models;

import java.util.Date;


public class Ticket {
    private String cinemaName;
    private String movieName;
    private String verificationCode;
    private String time;
    private Date date;

    private String cinemaId;
    private String movieId;

    public Ticket(String cinemaName, String movieName, String verificationCode, String time, Date date) {
        this.cinemaName = cinemaName;
        this.movieName = movieName;
        this.verificationCode = verificationCode;
        this.time = time;
        this.date = date;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDaysLeft() {
        return -1;
    }
}
