package com.example.wake.ethiocinemamovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wake.ethiocinemamovie.Models.UserAccManager;

public class CreateAccountActivity extends AppCompatActivity implements UserAccManager.OnSignUpResultListener {
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        pd = new ProgressDialog(this);
        pd.setTitle("Log in");
        pd.setMessage("please wait");

        Button createAccount = findViewById(R.id.creatAcc_btn);
        final CreateAccountActivity that = this;
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                TextView fullNameTV = findViewById(R.id.Cr_fullName);
                TextView username = findViewById(R.id.cr_userName);
                TextView phone = findViewById(R.id.phoneNm);
                TextView password = findViewById(R.id.Create_password);

                String[] fullName = fullNameTV.getText().toString().split(" ");

                UserAccManager.signUp(
                        fullName[0],
                        fullName[1],
                        phone.getText().toString(),
                        password.getText().toString(),
                        username.getText().toString(),
                        that,
                        that
                );
            }
        });

        Button login = findViewById(R.id.login_create);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(CreateAccountActivity.this, LogInActivity.class)
                );
                finish();
            }
        });

        Button continueGuest = findViewById(R.id.guestCreate);
        continueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            goHome();
            }
        });
    }

    @Override
    public void onSignUpResult(boolean result, boolean connection) {
        pd.dismiss();
        if (result) goHome();
        else Toast.makeText(this, connection?"Incorrect credential":" Log in failed!! " , Toast.LENGTH_SHORT).show();
    }

    public void goHome() {
        startActivity(
                new Intent(this, HomeActivity.class)
        );
        finish();
    }
}
