package com.example.wake.ethiocinemamovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.wake.ethiocinemamovie.Models.BookingManager;
import com.example.wake.ethiocinemamovie.Models.MovieSchedule;
import com.example.wake.ethiocinemamovie.Models.MoviesManager;
import com.example.wake.ethiocinemamovie.Utilities.NetworkingManager;
import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONObject;

import java.util.HashMap;

public class BookingActivity extends AppCompatActivity implements BookingManager.OnBookinResultListener {
    Toolbar toolbar;
    Spinner TicketCountPicker;
    TextView totPrice;
    ProgressDialog dialog;
    NumberPicker numberPicker;

    int numberOfTickets;
    int totalPrice;

    int moviePosition;
    int schedulePosition;
    MoviesManager manager;
    MovieSchedule chosenSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        manager = MoviesManager.getInstance(this);
        totPrice = findViewById(R.id.bookingTotalPrice);
        numberPicker = findViewById(R.id.number_picker);


        Intent intent = getIntent();
        moviePosition = intent.getIntExtra(MoviesActivity.KEY_CHOSEN_MOVIE_POSITION, 0);
        schedulePosition = intent.getIntExtra(MoviesActivity.KEY_CHOSEN_SCHEDULE_POSITION, 0);
        chosenSchedule =  manager.getMovie(moviePosition).getSchedule().get(schedulePosition);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Booking");
        dialog.setMessage("Booking is in progress");
        dialog.setCanceledOnTouchOutside(false);

        toolbar = findViewById(R.id.bookingToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button confirmBooking = findViewById(R.id.confirmBooking);

        confirmBooking.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                handleConfirmingBooking();
            }
        });

        fillUpData();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberOfTickets = newVal;
                totalPrice = numberOfTickets *  Integer.parseInt(chosenSchedule.getPrice());
                totPrice.setText(""+totalPrice + " ETB");
            }
        });
    }


    public Integer[] getPossibleTicketCounts() {
        Integer [] values = new Integer[10];
        for (int i = 1; i <= 10; i++) {
            values[i-1] = i;
        }
        return values;
    }

    public void handleConfirmingBooking() {
        dialog.show();
        BookingManager.requestBooking(chosenSchedule.getCinemaId(), chosenSchedule.getId(), numberOfTickets, this, this);

        Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }



    public void fillUpData() {
        getSupportActionBar().setTitle(
                manager.getMovie(moviePosition).getTitle()
        );

        TextView place = findViewById(R.id.bookingPlace);
        place.setText(chosenSchedule.getCinemaName());

        TextView time = findViewById(R.id.bookingtime);
        time.setText(chosenSchedule.getTime() + " " + chosenSchedule.getDate());

        TextView singlePrice = findViewById(R.id.bookingPrice);
        singlePrice.setText(chosenSchedule.getPrice());

        totPrice.setText(chosenSchedule.getPrice());
    }

    @Override
    public void onBookingResult(boolean result) {
        String message;
        if (result) message = "your booking has been succesfuly done";
        else message = "Booking Failed!!";

        final Snackbar snackbar = Snackbar.make(findViewById(R.id.bookingActivity), message, Snackbar.LENGTH_INDEFINITE);
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
