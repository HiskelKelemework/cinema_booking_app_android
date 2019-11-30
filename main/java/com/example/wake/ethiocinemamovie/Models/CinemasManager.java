package com.example.wake.ethiocinemamovie.Models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wake.ethiocinemamovie.Utilities.CloudConnectionManager;
import com.example.wake.ethiocinemamovie.Utilities.NetworkingManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;



public class CinemasManager {
    private static CinemasManager ourInstance;
    private ArrayList<Cinema> allCinemas = new ArrayList<>();
    private boolean cinemasLoaded = false;
    private Context ctx;
    private boolean areCinemasBeingLoaded = false;
    private CloudConnectionManager cloudConnectionManager;
    private ArrayList<CinemasManager.OnCinemasLoadedListener> listeners = new ArrayList<>();

    public static CinemasManager getInstance(Context ctx) {
        if (ourInstance == null) {
            ourInstance = new CinemasManager(ctx);
        }
        return ourInstance;
    }


    public CinemasManager(Context ctx) {
        this.ctx = ctx;
        cloudConnectionManager = CloudConnectionManager.getInstance(ctx);
    }



    public interface OnCinemasLoadedListener {
        public void OnCinemasLoaded();
    }

    public interface OnCinemaDetailLoadedListener {
        public void OnCinemaDetailLoaded();
    }

    public void loadCinemasAndNotifyMe(OnCinemasLoadedListener listener) {
        Log.i("mmm", "fff");
        listeners.add(listener);
        if (areCinemasBeingLoaded) {
            return;
        }
        areCinemasBeingLoaded = true;

        String url = NetworkingManager.MAIN_URL + NetworkingManager.ALL_CINEMAS_PATH;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        cinemasLoaded = true;
                        parseAndFillAllCinemas(response);
                        for (int i = 0; i < listeners.size(); i++) {
                            listeners.get(i).OnCinemasLoaded();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERR", "Reached here");
                        for (int i = 0; i < listeners.size(); i++) {
                            listeners.get(i).OnCinemasLoaded();
                        }
                    }
                }
        );

        cloudConnectionManager.getRequestQueue().add(request);
    }

    public void loadCinemaDetaileAndNotifyMe(final int position, final OnCinemaDetailLoadedListener listener){
        String url = NetworkingManager.MAIN_URL + NetworkingManager.CINEMA_DETIAL_PATH + getCinema(position).getId();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getCinema(position).isDetailLoaded = true;
                        parseCinemaDetail(response, position);
                        listener.OnCinemaDetailLoaded();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERR", "Reached here");
                        listener.OnCinemaDetailLoaded();
                    }
                }
        );

        cloudConnectionManager.getRequestQueue().add(request);

    }

    public Cinema getCinema(int position) {
        return allCinemas.get(position);
    }

    public boolean isCinemasLoaded() {
        return cinemasLoaded;
    }

    public ArrayList<Cinema> getAllCinemas() {
        return allCinemas;
    }

    public void parseAndFillAllCinemas(JSONObject result) {

        try {
            if (result.getBoolean("success")) {
                JSONArray data = result.getJSONArray("data");
                JSONObject currentLoadedMovie;

                for (int i = 0; i < data.length(); i++) {
                    currentLoadedMovie = data.getJSONObject(i);
                    Cinema currentCinema = new Cinema(
                            currentLoadedMovie.getString("name"),
                            currentLoadedMovie.getString("address"),
                            currentLoadedMovie.getString("_id"),
                            currentLoadedMovie.getString("imageURL")
                    );
                    allCinemas.add(currentCinema);
                }

                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseCinemaDetail(JSONObject result, final int position) {
        try {
            if (result.getBoolean("success")) {
                JSONObject data = result.getJSONObject("data");
                JSONArray schedules = data.getJSONArray("schedules");
                JSONObject currentLoadedSchedule;
                for (int i = 0; i < schedules.length(); i++) {
                    currentLoadedSchedule = (JSONObject) schedules.get(i);
                    getCinema(position).getSchedules().add(
                            new CinemaSchedule(
                                    currentLoadedSchedule.getString("schedule_id"),
                                    currentLoadedSchedule.getString("movie_name"),
                                    currentLoadedSchedule.getString("movie_id"),
                                    currentLoadedSchedule.getString("time"),
                                    parseDate(currentLoadedSchedule.getString("date")),
                                    currentLoadedSchedule.getString("price")
                                    )
                    );
                    getCinema(position).setNumberOfSeats(data.getInt("seats"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Date parseDate(String date) {
        String[] chunks = date.split("-");
        return new Date(
                Integer.parseInt(chunks[0]),
                Integer.parseInt(chunks[1]),
                Integer.parseInt(chunks[2].substring(0,2))
                );
    }











    // Testing purpose
    public static CinemasManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new CinemasManager();
        }
        return ourInstance;
    }

    public CinemasManager() {
    }


}
