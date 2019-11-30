package com.example.wake.ethiocinemamovie;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.wake.ethiocinemamovie.Models.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class SingleCinemaTest {
    @Rule
    public ActivityTestRule<SingleCinemaActivity> mActivityRule =
            new ActivityTestRule<>(SingleCinemaActivity.class);

    @Test
    public void ensureDataDisplayed() {
        String expectedCinemaName = "mukera";
        String expectedAddress = "ache";

        onView(withText(expectedCinemaName)).check(matches(withParent(withId(R.id.cinemaDetailToolbar))));
    }

    @Test
    public void ensureScheduleChoosenWorks() {
        onView(withId(R.id.cinema_det_schedules))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.bookingToolbar)).check(matches(isDisplayed()));

    }


}
