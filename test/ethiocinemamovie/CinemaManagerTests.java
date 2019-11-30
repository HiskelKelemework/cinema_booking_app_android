package com.example.wake.ethiocinemamovie;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.wake.ethiocinemamovie.Models.CinemasManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class CinemaManagerTests {
    private Context ctx;
    private CinemasManager manager;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void SetUp() throws Exception{
        ctx = mActivityRule.getActivity();
        manager = CinemasManager.getInstance(ctx);
    }


    @Test
    public void ensureLoadAllCinemasWork(){
        final int expectedNumberOfCinemas = 4;
        final String expectedCinemaName = "cinema Empire";
        final String expextedAddress = "mehal piasa";


        manager.loadCinemasAndNotifyMe(new CinemasManager.OnCinemasLoadedListener() {
            @Override
            public void OnCinemasLoaded() {
                assertEquals(expectedNumberOfCinemas, manager.getAllCinemas().size());
                assertEquals(expectedCinemaName, manager.getCinema(0).getName());
                assertEquals(expextedAddress, manager.getCinema(0).getAddress());
            }
        });
    }

    @Test
    public void ensureLoadCinemaDetailWork(){
        final int expectedNofSeats = 10;


        manager.loadCinemasAndNotifyMe(new CinemasManager.OnCinemasLoadedListener() {
            @Override
            public void OnCinemasLoaded() {
                //
            }
        });

        manager.loadCinemaDetaileAndNotifyMe(0, new CinemasManager.OnCinemaDetailLoadedListener() {
            @Override
            public void OnCinemaDetailLoaded() {
//                assertEquals(expectedDirector, manager.getCinema());
                assertEquals(expectedNofSeats, manager.getCinema(0).getNumberOfSeats());

            }
        });
    }
}
