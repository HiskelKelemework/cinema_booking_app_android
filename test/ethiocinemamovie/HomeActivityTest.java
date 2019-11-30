package com.example.wake.ethiocinemamovie;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;


@RunWith(AndroidJUnit4.class)

public class HomeActivityTest {
    private final String PACKAGE = "com.example.wake.ethiocinemamovie";

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void TestRechargeMenu() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open()); // Open Drawer

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.recharge));

        onView(withId(R.id.rechargeBTN)).check(matches(isDisplayed()));
    }

    @Test
    public void TestMoviesMenu() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open()); // Open Drawer
0
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.movies));

//        intended(allOf(hasComponent(MoviesActivity.class.getName())));
        onView(withId(R.id.movie_pager)).check(matches(isDisplayed()));
    }

    @Test
    public void TestCinemasMenu() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open()); // Open Drawer

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.cinemas));

        onView(withId(R.id.cinemas_toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void TestYourTickets() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open()); // Open Drawer

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.yourTickets));

        onView(withId(R.id.tickets_toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void TestYourBalance() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open()); // Open Drawer

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.yourBalance));
        onView(withText("Message")).check(matches(isDisplayed()));
    }

    @Test
    public void TestSignOut() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open()); // Open Drawer

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.signOut));
        onView(withId(R.id.Log_login)).check(matches(isDisplayed()));
    }
}
