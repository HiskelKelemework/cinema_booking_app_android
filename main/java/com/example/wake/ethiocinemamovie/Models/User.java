package com.example.wake.ethiocinemamovie.Models;

import java.util.ArrayList;



public class User {
    private static String fullName;
    private static  float balance;
    private static  String imageUrl;
    private static boolean isLoggedIn = false;
    private static  boolean isDetailLoaded = false;
//    private static ArrayList<Ticket> bookings = new ArrayList<>();
    private static ArrayList<Ticket> bookings = new ArrayList<>();
    public static final String ACCOUNT_PREF_NAME = "user_account_info";

    private static  String token;

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        User.fullName = fullName;
    }

    public static float getBalance() {
        return balance;
    }

    public static void setBalance(float balance) {
        User.balance = balance;
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        User.imageUrl = imageUrl;
    }

    public static ArrayList<Ticket> getBookings() {
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        for (int i = 0; i < 10; i++) {
            tickets.add(
                    new Ticket("Fasf", "fasfa", "88888888", "8:00", null)
            );
        }

        return tickets;
    }

    public static void setBookings(ArrayList<Ticket> bookings) {
        User.bookings = bookings;
    }

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        User.isLoggedIn = isLoggedIn;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        User.token = token;
    }

    public static boolean isIsDetailLoaded() {
        return isDetailLoaded;
    }

    public static void setIsDetailLoaded(boolean isDetailLoaded) {
        User.isDetailLoaded = isDetailLoaded;
    }

    public boolean doesUserHaveEnoughMoney(float money) {
        return money <= balance;
    }
}
