package com.example.wake.ethiocinemamovie.Models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wake.ethiocinemamovie.Utilities.CloudConnectionManager;
import com.example.wake.ethiocinemamovie.Utilities.NetworkingManager;

import org.json.JSONObject;

import java.util.HashMap;


public class BookingManager {

    public interface OnBookinResultListener{
        void onBookingResult(boolean result);
    }

    public static void requestBooking(String cinemaid, String scheId, int numberOfTickets, final OnBookinResultListener listener, Context ctx) {
        HashMap data = new HashMap();
        data.put("cinema_id", cinemaid);
        data.put("schedule_id", scheId);
        data.put("num_of_seats", numberOfTickets);

        String url = NetworkingManager.MAIN_URL + NetworkingManager.BOOK_PATH;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // to do some tasks here
                        boolean result = false;
                        listener.onBookingResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onBookingResult(false);
                    }
                }
        );

        CloudConnectionManager.getInstance(ctx).getRequestQueue().add(request);

    }

}
