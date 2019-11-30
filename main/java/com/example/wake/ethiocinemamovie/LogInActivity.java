package com.example.wake.ethiocinemamovie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wake.ethiocinemamovie.Models.User;
import com.example.wake.ethiocinemamovie.Models.UserAccManager;

public class LogInActivity extends AppCompatActivity implements UserAccManager.OnLogInResultListener {
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        pd = new ProgressDialog(this);
        pd.setTitle("Log in");
        pd.setMessage("please wait");

        SharedPreferences preferences = this.getSharedPreferences(User.ACCOUNT_PREF_NAME, Context.MODE_PRIVATE);
        if (preferences.getBoolean("isUserLoggedIn", false)) {
            goHome();
            return;
        }

        Button createAccount = findViewById(R.id.creatAcc);
        createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(LogInActivity.this, CreateAccountActivity.class)
                );
                finish();
            }
        });

        Button continueGuest = findViewById(R.id.contGuestLog);
        continueGuest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            goHome();
            }
        });

        Button login = findViewById(R.id.Log_login);
        final LogInActivity that = this;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView usrTV = findViewById(R.id.log_username);
                TextView usrPass = findViewById(R.id.log_pass);

                pd.show();
                UserAccManager.login(
                        usrTV.getText().toString(),
                        usrPass.getText().toString(),
                        that,
                        that
                );
            }
        });
    }

    @Override
    public void onLogInResult(boolean result, boolean connection) {
        pd.dismiss();
        if (result) goHome();
        else Toast.makeText(this, connection?"Incorrect credential":" Log in failed!! " , Toast.LENGTH_SHORT).show();
    }

    public void goHome() {
        startActivity(
                new Intent(LogInActivity.this, HomeActivity.class)
        );
        finish();
    }



}
