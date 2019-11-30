package com.example.wake.ethiocinemamovie.Models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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


public class MoviesManager {
    private static MoviesManager ourInstance;
    private ArrayList<Movie> allMovies = new ArrayList<>();
    private boolean moviesLoaded = false;
    private Context ctx;
    private boolean areMoviesBeingLoaded = false;
    private CloudConnectionManager cloudConnectionManager;
    private ArrayList<OnMoviesLoadedListener> listeners = new ArrayList<>();

    public MoviesManager(Context ctx) {
        this.ctx = ctx;
        cloudConnectionManager = CloudConnectionManager.getInstance(ctx);
    }

    public interface OnMoviesLoadedListener {
        public void OnMoviesLoaded();
    }

    public interface OnMovieDetailLoadedListener {
        public void OnMovieDetailLoaded();
    }


    public static MoviesManager getInstance(Context ctx) {
        if (ourInstance == null) {
            ourInstance = new MoviesManager(ctx);
        }
        return ourInstance;
    }

    private MoviesManager() {
    }

    public boolean areMoviesLoaded() {
        return moviesLoaded;
    }

    public Movie getMovie(String movieId) {
        for (int i = 0; i < allMovies.size(); i++) {
            if (getMovie(i).getId().equals(movieId)) return this.allMovies.get(i);
        }
        return null;
    }

    public Movie getMovie(int position) {
        return this.allMovies.get(position);
    }

    public ArrayList<Movie> getAllMovies() {
        return allMovies;
    }

    public void loadMoviesAndNotifyMe(final OnMoviesLoadedListener listener) {
        Log.i("mmm", "fff");
        listeners.add(listener);
        if (areMoviesBeingLoaded) {
            return;
        }
        areMoviesBeingLoaded = true;

        String url = NetworkingManager.MAIN_URL + NetworkingManager.ALL_MOVIE_PATH;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        areMoviesBeingLoaded = false;
                        moviesLoaded = true;
                        parseAndFillAllMovies(response);
                        for (int i = 0; i < listeners.size(); i++) {
                            listeners.get(i).OnMoviesLoaded();
                        }
                        listeners.clear();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        areMoviesBeingLoaded = false;
                        Log.e("ERR", "Reached here");
                        for (int i = 0; i < listeners.size(); i++) {
                            listeners.get(i).OnMoviesLoaded();
                        }
                        listeners.clear();
                    }
                }
        );

        cloudConnectionManager.getRequestQueue().add(request);

    }

    private void parseAndFillAllMovies(JSONObject response) {
        try {
            if (response.getBoolean("success") && allMovies.size() == 0) {
                JSONArray data = response.getJSONArray("data");
                JSONObject currentLoadedMovie;
                for (int i = 0; i < data.length(); i++) {
                    currentLoadedMovie = data.getJSONObject(i);
                    Movie currentMovie = new Movie(
                            currentLoadedMovie.getString("title"),
                            currentLoadedMovie.getString("genre"),
                            currentLoadedMovie.getJSONObject("rating").getString("value"),
                            currentLoadedMovie.getString("imageURL"),
                            currentLoadedMovie.getString("_id"),
                            currentLoadedMovie.getString("duration")
                    );
                    allMovies.add(currentMovie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("NET", "Reached here " + allMovies.size());

    }

    public void loadMovieDetailAndNotifyMe(final int position, final OnMovieDetailLoadedListener listener) {
        // here loading happens

        String url = NetworkingManager.MAIN_URL + NetworkingManager.MOVIE_DETIAL_PATH + getMovie(position).getId();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getMovie(position).isDetailedLoaded = true;
                        parseAndFillMovieDetail(position, response);
                        listener.OnMovieDetailLoaded();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERR", "Reached here");
                        listener.OnMovieDetailLoaded();
                    }
                }
        );

        cloudConnectionManager.getRequestQueue().add(request);



        allMovies.get(position).isDetailedLoaded = true;
        listener.OnMovieDetailLoaded();
    }

    public int getMoviePosition(String movieId) {
        for (int i = 0; i < allMovies.size(); i++) {
            if (getMovie(i).getId().equals(movieId)) return i;
        }
        return -1;
    }

    public void parseAndFillMovieDetail(final int position, JSONObject result) {
        try {
            if (result.getBoolean("success")) {
                Movie movie = getMovie(position);
                JSONObject data = result.getJSONObject("data");
                movie.setDirector(data.getString("director"));

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < data.getJSONArray("cast").length(); i++) {
                    builder.append(data.getJSONArray("cast").get(i));
                    builder.append(", ");
                }
                movie.setCasts(builder.toString());
                movie.setSynopsis(data.getString("sysnopsis"));

                JSONArray schedules = data.getJSONArray("schedules");
                JSONObject currentSchedule;
                for (int i = 0; i < schedules.length(); i++) {
                    currentSchedule = schedules.getJSONObject(i);
                    movie.getSchedule().add(
                            new MovieSchedule(
                                    currentSchedule.getJSONObject("movie_schedule").getString("schedule_id"),
                                    null,
                                    currentSchedule.getString("cinema_id"),
                                    currentSchedule.getString("cinema_name"),
                                    currentSchedule.getJSONObject("movie_schedule").getString("price"),
                                    parseDate(currentSchedule.getJSONObject("movie_schedule").getString("date")),
                                    currentSchedule.getJSONObject("movie_schedule").getString("time"),
                                    currentSchedule.getInt("seats")
                            )
                    );
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
}
