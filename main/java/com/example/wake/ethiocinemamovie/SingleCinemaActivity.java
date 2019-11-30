package com.example.wake.ethiocinemamovie;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wake.ethiocinemamovie.Adapters.CinemaScheduleAdapter;
import com.example.wake.ethiocinemamovie.Adapters.MovieScheduleAdapter;
import com.example.wake.ethiocinemamovie.Models.Cinema;
import com.example.wake.ethiocinemamovie.Models.CinemaSchedule;
import com.example.wake.ethiocinemamovie.Models.CinemasManager;
import com.example.wake.ethiocinemamovie.Models.Movie;
import com.example.wake.ethiocinemamovie.Models.MovieSchedule;
import com.example.wake.ethiocinemamovie.Models.MoviesManager;

import java.util.ArrayList;
import java.util.Date;

public class SingleCinemaActivity extends AppCompatActivity implements MovieScheduleAdapter.ScheduleChosenListener, CinemasManager.OnCinemasLoadedListener, CinemasManager.OnCinemaDetailLoadedListener {
    RecyclerView cinemSchedules;
    CinemaScheduleAdapter adapter;

    int cinemaPosition;
    CinemasManager cinemaManager;
    ArrayList<CinemaSchedule> schedules = new ArrayList<>();
    Cinema currentCinema;

    ProgressBar prog1, prog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_cinema);

        cinemaManager = CinemasManager.getInstance(this);

        prog1 = findViewById(R.id.cinema_loading_prog);
        prog2 = findViewById(R.id.aboutCinema_loading_prog);

        prog1.setVisibility(View.VISIBLE);
        prog2.setVisibility(View.VISIBLE);

        Toolbar toolbar = findViewById(R.id.cinemaDetailToolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.cinema_collapsingTBL);
        getSupportActionBar().setTitle("Mukera");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        cinemaPosition = intent.getIntExtra(CinemasActivity.KEY_CHOSEN_CINEMA_POSITION, 0);
        currentCinema = cinemaManager.getCinema(cinemaPosition);
//        setUpMovieSchedule();

        LinearLayout detailToggle = findViewById(R.id.cen_show_onMap);
        detailToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleShowOnMap();
            }
        });


        cinemSchedules = findViewById(R.id.cinema_det_schedules);
        adapter = new CinemaScheduleAdapter(this, cinemaManager.getCinema(cinemaPosition).getSchedules());
        cinemSchedules.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        cinemSchedules.setLayoutManager(layoutManager);

        fillUpMovieInitialInfo();
    }

    private ArrayList<CinemaSchedule> getSchedule() {
        ArrayList<CinemaSchedule> schs = new ArrayList<>();
        schs.add(
                new CinemaSchedule("Fsdaf","henok", "fasdf", "2:30", new Date(2018, 6, 10), "20 ETB")
        );

        schs.add(
                new CinemaSchedule("Fsadf","henok", "fasdf", "2:30", new Date(2018, 6, 10), "20 ETB")
        );        schs.add(
                new CinemaSchedule("fsd","henok", "fasdf", "2:30", new Date(2018, 6, 10), "20 ETB")
        );
        return schs;
    }

    private void handleShowOnMap() {
    }

    @Override
    public void onScheduleChosen(int position) {
        Intent intent = new Intent(this, BookingActivityCinema.class);
        intent.putExtra(CinemasActivity.KEY_CHOSEN_CINEMA_POSITION, cinemaPosition);
        intent.putExtra(CinemasActivity.KEY_CHOSEN_SCHEDULE_POSITION, position);
        startActivity(intent);
    }


    public void fillUpMovieInitialInfo() {
        Cinema cinema = cinemaManager.getCinema(cinemaPosition);
        getSupportActionBar().setTitle(cinema.getName()); // suspected to produce error

        TextView phone = findViewById(R.id.cin_det_phone);
        phone.setText(cinema.getPhone());

        TextView addresss = findViewById(R.id.cine_det_address);
        addresss.setText(cinema.getAddress());

        // image not loaded yet

        fillUpMovieLaterInfo();
    }


    public void fillUpMovieLaterInfo() {
        if (currentCinema.isDetailLoaded) {
            for (int i = 0; i < currentCinema.getSchedules().size(); i++) {
                schedules.add(currentCinema.getSchedules().get(i));
            }
            adapter.notifyDataSetChanged();
        }
        else {
            cinemaManager.loadCinemaDetaileAndNotifyMe(cinemaPosition, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void OnCinemasLoaded() {
        // here goes some thing else
    }

    @Override
    public void OnCinemaDetailLoaded() {
        prog1.setVisibility(View.GONE);
        prog2.setVisibility(View.GONE);
        if (cinemaManager.getCinema(cinemaPosition).isDetailLoaded) {
            //succ
            TextView seats = findViewById(R.id.cine_number_seats);
            seats.setText(""+currentCinema.getNumberOfSeats());

            adapter.notifyDataSetChanged();
        }
        else {
            // error
            String messasge = "connecting to server failed!! ";
            final CinemasManager.OnCinemaDetailLoadedListener listener = this;
            final Snackbar snackbar = Snackbar.make(findViewById(R.id.bookingActivity), messasge, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("try again", new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                    cinemaManager.loadCinemaDetaileAndNotifyMe(cinemaPosition, listener);
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorTheme));
            snackbar.show();
        }
    }
}
