package com.example.wake.ethiocinemamovie;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wake.ethiocinemamovie.Adapters.MoviesPagerAdapter;

public class MoviesActivity extends AppCompatActivity {
    public static final String KEY_CHOSEN_MOVIE_POSITION = "com.example.wake.ethiocinemamovie.keychosenid";
    public static final String KEY_CHOSEN_SCHEDULE_POSITION = "com.example.wake.ethiocinemamovie.keychosenScheduule";

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        setUpTransition();

        toolbar = findViewById(R.id.movie_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Movies");

        tabLayout = (TabLayout) findViewById(R.id.movie_tablayout);
        viewPager = (ViewPager) findViewById(R.id.movie_pager);

        MoviesPagerAdapter pagerAdapter = new MoviesPagerAdapter(getSupportFragmentManager());


        MoviesListFrag page1 = new MoviesListFrag();
        page1.setPageType(MoviesListFrag.ALL_MOVIES);

        MoviesListFrag page2 = new MoviesListFrag();
        page2.setPageType(MoviesListFrag.TODAY_MOVIES);

        MoviesListFrag page3 = new MoviesListFrag();
        page3.setPageType(MoviesListFrag.TOP_RATED);

        pagerAdapter.addPage(page1, "all");
        pagerAdapter.addPage(page2, "today");
        pagerAdapter.addPage(page3, "top rated");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setUpTransition() {
        View decor = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Fade fade = new Fade();
            fade.excludeTarget(decor.findViewById(R.id.movie_toolbar), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
