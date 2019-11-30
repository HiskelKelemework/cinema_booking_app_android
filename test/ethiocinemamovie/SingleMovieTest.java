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
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class SingleMovieTest {
    @Rule
    public ActivityTestRule<SingleMovieActivity> mActivityRule =
            new ActivityTestRule<>(SingleMovieActivity.class);
    @Test
    public void ensureDataDisplayed() {
        String expectedMovieName = "wanaw";

        onView(withText(expectedMovieName)).check(matches(withParent(withId(R.id.movieDetailToolbar))));
    }
}
