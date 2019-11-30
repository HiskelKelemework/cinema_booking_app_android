package com.example.wake.ethiocinemamovie;

import android.support.test.espresso.ViewAssertion;
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
public class SignupTest {
    @Rule
    public ActivityTestRule<CreateAccountActivity> mActivityRule =
            new ActivityTestRule<>(CreateAccountActivity.class);

    @Test
    public void ensureCreateAccountWorks() {
        onView(withId(R.id.Cr_fullName)) // withId(R.id.my_view) is a ViewMatcher
                .perform(typeText("addis amare"), closeSoftKeyboard());
        onView(withId(R.id.cr_userName))
                .perform(typeText("whafatgsfasfsdaft"), closeSoftKeyboard()); // unique
        onView(withId(R.id.phoneNm))
                .perform(typeText("01384564"), closeSoftKeyboard());  // unique
        onView(withId(R.id.Create_password))
                .perform(typeText("aaa"), closeSoftKeyboard());

        onView(withId(R.id.creatAcc_btn))
                .perform(click()); // click() is a ViewAction

        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));// matches(isDisplayed()) is a ViewAssertion

    }
    }
