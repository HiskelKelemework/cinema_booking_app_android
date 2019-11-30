package com.example.wake.ethiocinemamovie;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.wake.ethiocinemamovie.Models.Cinema;
import com.example.wake.ethiocinemamovie.Models.CinemaSchedule;
import com.example.wake.ethiocinemamovie.Models.CinemasManager;
import com.example.wake.ethiocinemamovie.Models.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class BookActivityTest {
    // note I should call method of BookingActivityCinema... initializeData


    @Rule
    public ActivityTestRule<BookingActivityCinema> mActivityRule =
            new ActivityTestRule<>(BookingActivityCinema.class);

    @Test
    public void ensureBookWorks() {
        User.setBalance(120.3f);
        float initial = User.getBalance();

        User.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJoaGgiLCJpYXQiOjE1Mjk1Njk3MjN9.6jGpHj2BTGEnU4faXTfTRagy3rXvyfQpcTIdUhN_qEE");

        onView(withId(R.id.confirmBooking))
                .perform(click());


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(isDisplayed()));

    }

}
