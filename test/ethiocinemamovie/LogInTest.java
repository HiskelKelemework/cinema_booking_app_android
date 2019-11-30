package com.example.wake.ethiocinemamovie;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LogInTest {
    @Rule
    public ActivityTestRule<LogInActivity> mActivityRule =
            new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void ensureLoginWorks() {
        // Type text and then press the button.
        onView(withId(R.id.log_username))
                .perform(typeText("whafatgsfasfsdaft"), closeSoftKeyboard());

        onView(withId(R.id.log_pass))
                .perform(typeText("aaa"), closeSoftKeyboard());

        onView(withId(R.id.Log_login))
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }

}
