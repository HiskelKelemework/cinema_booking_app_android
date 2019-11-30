package com.example.wake.ethiocinemamovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wake.ethiocinemamovie.Models.MovieSchedule;
import com.example.wake.ethiocinemamovie.Models.MoviesManager;
import com.example.wake.ethiocinemamovie.Models.User;
import com.example.wake.ethiocinemamovie.Utilities.CloudConnectionManager;
import com.example.wake.ethiocinemamovie.Utilities.NetworkingManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RechargeActivity extends AppCompatActivity {
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.RecharginToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recharge Balance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Recharging");
        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);

        Button recharge = findViewById(R.id.rechargeBTN);
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRecharge();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void handleRecharge() {
        dialog.show();
        HashMap data = new HashMap();
        AppCompatEditText cardNum = findViewById(R.id.inp_card_number);
        data.put("card_number", cardNum.getText().toString());

        String url = NetworkingManager.MAIN_URL + NetworkingManager.RECHARGE_PATH;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean correct = false;
                        try {
                            if (response.getBoolean("success")) {
                                correct = true;
                                User.setBalance(
                                        Float.parseFloat(response.getJSONObject("data").getString("balance"))
                                );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        onRechargeResult(true, correct);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onRechargeResult(false, false);
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

        CloudConnectionManager.getInstance(this).getRequestQueue().add(request);

    }

    public void onRechargeResult(boolean connection, boolean result) {
        dialog.dismiss();
        String message;
        if (!connection) message = "connection error!";
        else if (result) message = "Successfully recharged. Your current balance is " + User.getBalance();
        else message = "Invalid card number";

        final Snackbar snackbar = Snackbar.make(findViewById(R.id.rechargeActivity), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorTheme));
        snackbar.show();
    }


}
