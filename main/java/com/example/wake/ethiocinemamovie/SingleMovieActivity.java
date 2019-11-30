package com.example.wake.ethiocinemamovie;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wake.ethiocinemamovie.Adapters.MovieScheduleAdapter;
import com.example.wake.ethiocinemamovie.Models.Movie;
import com.example.wake.ethiocinemamovie.Models.MovieSchedule;
import com.example.wake.ethiocinemamovie.Models.MoviesManager;

import java.util.ArrayList;
import java.util.Date;

public class SingleMovieActivity extends AppCompatActivity
        implements MovieScheduleAdapter.ScheduleChosenListener, MoviesManager.OnMovieDetailLoadedListener {


    RecyclerView movieScheduleRV;
    MovieScheduleAdapter adapter;
    LinearLayoutManager layoutManager;

    int moviePosition;
    MoviesManager moviesManager;
    ArrayList<MovieSchedule> schedules = new ArrayList<>();
    Movie currentMovie;

    ProgressBar prog1, prog2;

    private boolean isAboutDetailShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        moviesManager = MoviesManager.getInstance(this);

        prog1 = findViewById(R.id.aboutMovie_loading_prog);
        prog2 = findViewById(R.id.movie_loading_prog);

        prog1.setVisibility(View.VISIBLE);
        prog2.setVisibility(View.VISIBLE);

        Toolbar toolbar = findViewById(R.id.movieDetailToolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsingTBL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        moviePosition = intent.getIntExtra(MoviesActivity.KEY_CHOSEN_MOVIE_POSITION, 0);
        currentMovie = moviesManager.getMovie(moviePosition);
//        setUpMovieSchedule();

        LinearLayout detailToggle = findViewById(R.id.movDetInfoToogle);
        detailToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDetailToggle();
            }
        });


        movieScheduleRV = findViewById(R.id.movie_det_schedules);
        adapter = new MovieScheduleAdapter(this, currentMovie.getSchedule());
        movieScheduleRV.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        movieScheduleRV.setLayoutManager(layoutManager);

        fillUpMovieInitialInfo();

    }

    @Override
    public void onScheduleChosen(int position) {
        Toast.makeText(this, "received " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra(MoviesActivity.KEY_CHOSEN_MOVIE_POSITION, moviePosition);
        intent.putExtra(MoviesActivity.KEY_CHOSEN_SCHEDULE_POSITION, position);
        startActivity(intent);
    }

    public ArrayList<MovieSchedule> getSchedule() {
        ArrayList<MovieSchedule> a = new ArrayList<>();
        for (int i = 0; i < 10;i++) {
            a.add(new MovieSchedule("fsdaf","Fasf", "ekelfl ee0", "ekeelle ", "20 birr",
                    new Date(2018, 6, 10), "2:30", 5));
        }
        return a;
    }

    public void setUpMovieSchedule() {
        if (moviesManager.getMovie(moviePosition).isDetailedLoaded) {
            schedules = moviesManager.getMovie(moviePosition).getSchedule();
        }
        else
            moviesManager.loadMovieDetailAndNotifyMe(moviePosition, this);
    }

    @Override
    public void OnMovieDetailLoaded() {
        prog1.setVisibility(View.GONE);
        prog2.setVisibility(View.GONE);

        if (currentMovie.isDetailedLoaded) {
            fillUpMovieLaterInfo();
        }
        else {
            String messasge = "connecting to server failed!! ";
            final MoviesManager.OnMovieDetailLoadedListener listener = this;
            final Snackbar snackbar = Snackbar.make(findViewById(R.id.bookingActivity), messasge, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("try again", new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                    moviesManager.loadMovieDetailAndNotifyMe(moviePosition, listener);
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorTheme));
            snackbar.show();
        }


    }

    public void handleDetailToggle() {
        isAboutDetailShown = !isAboutDetailShown;
        TextView toggleText = findViewById(R.id.mov_det_showLesMore_toogle);
        ImageView toggleIcon = findViewById(R.id.mov_det_showLesMore_icon);

        int status = View.GONE;

        if (isAboutDetailShown)  {
            status = View.VISIBLE;
            toggleText.setText("show less");
            toggleIcon.setImageResource(R.drawable.ic_up_24dp);
        }
        else {
            toggleText.setText("show more");
            toggleIcon.setImageResource(R.drawable.ic_down_24dp);
        }

        findViewById(R.id.castsWrapper).setVisibility(status);
        findViewById(R.id.storyWrapper).setVisibility(status);
    }


    public void fillUpMovieInitialInfo() {
        Movie movie = moviesManager.getMovie(moviePosition);
        getSupportActionBar().setTitle(movie.getTitle()); // suspected to produce error

        TextView genre = findViewById(R.id.mov_det_genere);
        genre.setText(movie.getGenre());

        TextView duration = findViewById(R.id.mov_det_duration);
        duration.setText(movie.getDuration());

        TextView rating = findViewById(R.id.movie_det_rating);
        rating.setText(movie.getRating() + "/10");

        // image not loaded yet

        fillUpMovieLaterInfo();
    }


    public void fillUpMovieLaterInfo() {
        if (currentMovie.isDetailedLoaded) {
            TextView director = findViewById(R.id.directorName);
            director.setText(currentMovie.getDirector());

            TextView synopsis = findViewById(R.id.synopsis);
            synopsis.setText(currentMovie.getSynopsis());

            TextView casts = findViewById(R.id.casts);
            casts.setText(currentMovie.getCasts());

            for (int i = 0; i < currentMovie.getSchedule().size(); i++) {
                schedules.add(currentMovie.getSchedule().get(i));
            }
            adapter.notifyDataSetChanged();
        }
        else {
            moviesManager.loadMovieDetailAndNotifyMe(moviePosition, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


}
