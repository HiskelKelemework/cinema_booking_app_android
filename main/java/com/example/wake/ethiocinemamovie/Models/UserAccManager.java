package com.example.wake.ethiocinemamovie.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wake.ethiocinemamovie.R;
import com.example.wake.ethiocinemamovie.Utilities.CloudConnectionManager;
import com.example.wake.ethiocinemamovie.Utilities.NetworkingManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UserAccManager {

    public interface OnLogInResultListener {
        public void onLogInResult(boolean result, boolean connection);
    }

    public interface OnSignUpResultListener {
        public void onSignUpResult(boolean result, boolean connection);
    }

    public interface OnAccountDetailLoadedListener {
        public void OnAccountDetailLoaded(boolean connection);
    }

    public static void signUp(String firstName, String lastName, String phone,
                              String password, String userName, final Context ctx, final OnSignUpResultListener listener) {
        HashMap data = new HashMap();
        data.put("first_name", firstName);
        data.put("last_name", lastName);
        data.put("phone", phone);
        data.put("password", password);
        data.put("user_name", userName);

        String url = NetworkingManager.MAIN_URL + NetworkingManager.SIGN_UP_PATH;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // to do some tasks here token ,id user_name, phone, balance
                        boolean correct = false;
                        try {
                            if (response.getBoolean("success")) {
                                correct = true;
                                parseUserInfo(response, ctx);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.onSignUpResult(correct, true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onSignUpResult(false, false);
                    }
                }
        );

        CloudConnectionManager.getInstance(ctx).getRequestQueue().add(request);
    }

    public static void login(String userName, String passWord, final Context ctx, final OnLogInResultListener listener) {
        HashMap data = new HashMap();
        data.put("user_name", userName);
        data.put("password", passWord);

        String url = NetworkingManager.MAIN_URL + NetworkingManager.LOGIN_PATH;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // to do some tasks here token ,id user_name, phone, balance
                        boolean correct = false;
                        try {
                            if (response.getBoolean("success")) {
                                correct = true;
                                parseUserInfo(response, ctx);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.onLogInResult(correct, true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onLogInResult(false, false);
                    }
                }
        );

        CloudConnectionManager.getInstance(ctx).getRequestQueue().add(request);
    }

    public static void parseUserInfo(JSONObject response, Context ctx) {
        try {
            if (response.getBoolean("success")) {
                SharedPreferences preferences = ctx.getSharedPreferences(User.ACCOUNT_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                JSONObject data = response.getJSONObject("data");
//                User.setFullName();
                User.setToken(data.getString("token"));
                editor.putString("token", data.getString("token"));

                User.setFullName(data.getString("first_name") + " " + data.getString("last_name"));
                editor.putString("fullName", data.getString("first_name") + " " + data.getString("last_name"));

                User.setBalance(Float.parseFloat(data.getString("balance")));
                editor.putString("balance", data.getString("balance"));

                User.setImageUrl(data.getString("imageURL"));
                editor.putString("imageURL", data.getString("imageURL"));
                User.setIsLoggedIn(true);
                editor.putBoolean("isUserLoggedIn", true);
                editor.commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseUserInfo(JSONObject response) {
        // for testing purpose
        try {
            if (response.getBoolean("success")) {

                JSONObject data = response.getJSONObject("data");
//                User.setFullName();
                User.setToken(data.getString("token"));

                User.setFullName(data.getString("first_name") + " " + data.getString("last_name"));

                User.setBalance(Float.parseFloat(data.getString("balance")));

                User.setImageUrl(data.getString("imageURL"));
                User.setIsLoggedIn(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void loadAccountDetail() {
        // do the loading
    }

    public static void loadAccountDetailAndNotifyMe(final OnAccountDetailLoadedListener listener, final Context ctx) {

        String url = NetworkingManager.MAIN_URL + NetworkingManager.RECHARGE_PATH;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseUserInfo(response, ctx);
                        listener.OnAccountDetailLoaded(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.OnAccountDetailLoaded(false);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", User.getToken());
                return params;
            }
        };

        CloudConnectionManager.getInstance(ctx).getRequestQueue().add(request);
    }


    public void parseAndFillUserDetail(JSONObject result) {
        try {
            if (result.getBoolean("success")) {
                // here goes the parsing
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void restoreUserInfo(Context ctx) {
        SharedPreferences preferences = ctx.getSharedPreferences(User.ACCOUNT_PREF_NAME, Context.MODE_PRIVATE);
        User.setFullName(preferences.getString("fullName", "No user"));
        User.setToken(preferences.getString("token", null));
        User.setBalance(Float.parseFloat(preferences.getString("balance", "0")));
    }

    public void signOut(Context ctx) {
        SharedPreferences preferences = ctx.getSharedPreferences(User.ACCOUNT_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isUserLoggedIn", false);
    }
}
