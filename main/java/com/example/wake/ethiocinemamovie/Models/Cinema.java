package com.example.wake.ethiocinemamovie.Models;

import java.util.ArrayList;


public class Cinema {
    private String name;
    private String address;
    private String id;
    private String imageUrl;
    private int numberOfSeats;
    private String phone = "0911932901";
    public boolean isDetailLoaded = false;
    private ArrayList<CinemaSchedule> schedules = new ArrayList<CinemaSchedule>();

    public Cinema(String name, String address, String id, String imageUrl) {
        this.name = name;
        this.address = address;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<CinemaSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<CinemaSchedule> schedules) {
        this.schedules = schedules;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
