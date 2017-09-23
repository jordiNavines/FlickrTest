package com.jordi.navines.flickr.flickrtest;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jordi on 23/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    @Rule
    public ActivityTestRule<GalleryActivity> activityTestRule = new ActivityTestRule<GalleryActivity>(GalleryActivity.class);


    @Test
    public void testUIGallery() throws InterruptedException {
        activityTestRule.launchActivity(new Intent());

        Thread.sleep(1000);
        onView(ViewMatchers.withId(R.id.gallery_fragment))
                .perform(RecyclerViewActions.scrollToPosition(10));

        Thread.sleep(2000);
        onView(ViewMatchers.withId(R.id.gallery_fragment))
                .perform(RecyclerViewActions.scrollToPosition(20));

        Thread.sleep(2000);
        onView(ViewMatchers.withId(R.id.gallery_fragment))
                .perform(RecyclerViewActions.scrollToPosition(30));

        Thread.sleep(2000);
        onView(ViewMatchers.withId(R.id.gallery_fragment))
                .perform(RecyclerViewActions.scrollToPosition(0));

        onView(ViewMatchers.withId(R.id.gallery_fragment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        onView(withId(R.id.pager)).check(matches(isDisplayed()));

        onView(withId(R.id.pager)).perform(swipeLeft());

        onView(withId(R.id.pager)).perform(swipeRight());
    }
}
