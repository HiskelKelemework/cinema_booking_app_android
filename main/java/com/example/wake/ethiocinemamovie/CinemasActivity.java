package com.example.wake.ethiocinemamovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.wake.ethiocinemamovie.Adapters.CinemaListAdapter;
import com.example.wake.ethiocinemamovie.Models.Cinema;
import com.example.wake.ethiocinemamovie.Models.CinemasManager;

import java.util.ArrayList;

public class CinemasActivity extends AppCompatActivity implements CinemaListAdapter.CinemaChosenListener, CinemasManager.OnCinemasLoadedListener {
    public static final String KEY_CHOSEN_CINEMA_POSITION = "com.example.wake.ethiocinemamovie.keychosenid_cinema";
    public static final String KEY_CHOSEN_SCHEDULE_POSITION = "com.example.wake.ethiocinemamovie.keychosenScheduule_cinema";

    ProgressBar pb;
    android.support.v7.widget.Toolbar toolbar;
    LinearLayout connProb;

    ArrayList<Cinema> allCinemas = new ArrayList<>();
    RecyclerView cinemasList;
    CinemaListAdapter adapter;


    CinemasManager cinemasManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinemas);

        toolbar = findViewById(R.id.cinemas_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cinemas");

        connProb = findViewById(R.id.cinemas_conn_prob);
        pb = findViewById(R.id.cinema_loading_prog);
        pb.setVisibility(View.VISIBLE);

        cinemasList = findViewById(R.id.cinemas_recyl);
        adapter = new CinemaListAdapter(allCinemas, this, this);
//        adapter = new CinemaListAdapter(getAllCinemas(), this, this);

        cinemasList.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        cinemasList.setLayoutManager(layoutManager);

        cinemasManager = CinemasManager.getInstance(this);


        Button retry = findViewById(R.id.cinemas_retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpCinemasList();
            }
        });

        setUpCinemasList();
    }

    @Override
    public void onCinemaChosen(int position) {
        //
        Intent intent = new Intent(this, SingleCinemaActivity.class);
        intent.putExtra(KEY_CHOSEN_CINEMA_POSITION, position);
        startActivity(intent);

        Toast.makeText(this, " " + position, Toast.LENGTH_SHORT).show();

    }

    public void setUpCinemasList() {
        if (cinemasManager.isCinemasLoaded()) {
            pb.setVisibility(View.GONE);
            for (int i = 0; i<cinemasManager.getAllCinemas().size(); i++) {
                allCinemas.add(cinemasManager.getCinema(i));
            }
            adapter.notifyDataSetChanged();
        }
        else {
            cinemasManager.loadCinemasAndNotifyMe(this);
        }

    }

    public ArrayList<Cinema> getAllCinemas() {
        ArrayList<Cinema> cinemas = new ArrayList<>();
        cinemas.add(
                new Cinema("mukera", "1st streas  henok sefer", "Fasf", "")
        );
        cinemas.add(
                new Cinema("mukera", "1st streas  henok sefer", "Fasf", "")
        );
        return cinemas;
    }

    @Override
    public void OnCinemasLoaded() {
        pb.setVisibility(View.GONE);
        if (cinemasManager.isCinemasLoaded()) {
            for (int i = 0; i < cinemasManager.getAllCinemas().size(); i++) {
                allCinemas.add(cinemasManager.getCinema(i));
            }
            Toast.makeText(this, " " + allCinemas.size(), Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
        else {
            connProb.setVisibility(View.VISIBLE);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
