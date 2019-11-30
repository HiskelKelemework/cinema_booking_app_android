package com.example.wake.ethiocinemamovie.Utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class CloudConnectionManager {
    private static CloudConnectionManager mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private CloudConnectionManager(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }


    public static synchronized CloudConnectionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CloudConnectionManager(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
