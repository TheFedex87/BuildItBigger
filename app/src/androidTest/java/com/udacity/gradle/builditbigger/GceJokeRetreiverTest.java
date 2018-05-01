package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.paid.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * Created by feder on 01/05/2018.
 */

@RunWith(AndroidJUnit4.class)
public class GceJokeRetreiverTest {
    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void CheckGceReturnsAJoke(){
        onView(withId(R.id.say_a_joke)).perform(click());
        onView(withId(R.id.joke)).check(matches(allOf(not(withText("")), not(withText(containsString("failed to connect"))))));
    }
}
