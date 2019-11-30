package com.example.wake.ethiocinemamovie;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import com.example.wake.ethiocinemamovie.Models.CinemasManager;
import com.example.wake.ethiocinemamovie.Models.Movie;
import com.example.wake.ethiocinemamovie.Models.MoviesManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MovieManagerTest {
    private Context ctx;
    private MoviesManager manager;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void SetUp() throws Exception{
        ctx = mActivityRule.getActivity();
        manager = MoviesManager.getInstance(ctx);
    }

    @Test
    public void ensureLoadAllMoviesWork(){
        final int expectedNumberOfMovies = 5;
        final String expectedMovieName = "eyemetash tegni";
        final String expextedGenre = "comedy, drama";


        manager.loadMoviesAndNotifyMe(new MoviesManager.OnMoviesLoadedListener() {
            @Override
            public void OnMoviesLoaded() {
                assertEquals(expectedNumberOfMovies, manager.getAllMovies().size());
                assertEquals(expectedMovieName, manager.getMovie(0).getTitle());
                assertEquals(expextedGenre, manager.getMovie(0).getGenre());

            }
        });
    }

    @Test
    public void ensureLoadMovieDetailWork(){
        final int expectedSchedules = 1;

        manager.getAllMovies().add(
                new Movie("fasf", "fasf", "fasf", "fasdf", "5b2b5c3350d2e907843b4f53", "102")
        );
        manager.loadMovieDetailAndNotifyMe(0, new MoviesManager.OnMovieDetailLoadedListener(){
            @Override
            public void OnMovieDetailLoaded() {
                assertEquals(true, manager.getMovie(0).isDetailedLoaded);
            }
        });
    }
}
