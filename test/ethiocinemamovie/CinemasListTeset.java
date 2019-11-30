package com.example.wake.ethiocinemamovie;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class CinemasListTeset {
    @Rule
    public ActivityTestRule<CinemasActivity> mActivityRule =
            new ActivityTestRule<>(CinemasActivity.class);

    @Test
    public void ensureCinemaIsChoosen() {
        onView(withId(R.id.cinemas_recyl))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.cinemaDetailToolbar)).check(matches(isDisplayed()));
    }
}
