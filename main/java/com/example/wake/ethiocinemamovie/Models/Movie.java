package com.example.wake.ethiocinemamovie.Models;

import java.util.ArrayList;


public class Movie {
    private String title;
    private String genre;
    private String rating;
    private String imageUrl;
    private String duration;
    private String id;
    private String director;
    private String synopsis;
    private String casts;


    public boolean isDetailedLoaded = false;
    private ArrayList<MovieSchedule> schedule = new ArrayList();

    public Movie(String title, String genre, String rating, String imageUrl, String id, String duration) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.id = id;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public ArrayList<MovieSchedule> getSchedule() {
        return schedule;
    }

    public String getDuration() {
        return duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
