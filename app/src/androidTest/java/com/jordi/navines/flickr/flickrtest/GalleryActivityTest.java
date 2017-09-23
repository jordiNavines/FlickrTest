package com.jordi.navines.flickr.flickrtest;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryActivity;
import com.jordi.navines.flickr.flickrtest.utils.Utils;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by jordi on 23/09/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GalleryActivityTest {

    @Rule
    public ActivityTestRule<GalleryActivity> activityTestRule = new ActivityTestRule<GalleryActivity>(GalleryActivity.class);


    @Test
    public void test1_ScrollGallery() throws InterruptedException {
        activityTestRule.launchActivity(new Intent());

        Thread.sleep(1000);
        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.scrollToPosition(10));

        Thread.sleep(2000);
        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.scrollToPosition(20));

        Thread.sleep(2000);
        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.scrollToPosition(30));

        Thread.sleep(2000);
        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.scrollToPosition(0));

        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        onView(withId(R.id.pager)).check(matches(isDisplayed()));

        onView(withId(R.id.pager)).perform(swipeLeft());

        onView(withId(R.id.pager)).perform(swipeRight());
    }

    @Test
    public void test2_refreshlist() throws InterruptedException {
        activityTestRule.launchActivity(new Intent());

        Thread.sleep(1000);

        onView(withId(R.id.swipe_refresh_list))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));

        Thread.sleep(1000);
    }


    @Test
    public void test3_NoConnectionMessage() throws InterruptedException {
        activityTestRule.launchActivity(new Intent());

        WifiManager wifiManager = (WifiManager) activityTestRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);

        Thread.sleep(1000);

        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.scrollToPosition(10));

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.error_no_connection)))
                .check(matches(isDisplayed()));

        wifiManager.setWifiEnabled(true);
    }



    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }

    @After
    public void setUp() throws InterruptedException {
        WifiManager wifiManager = (WifiManager)activityTestRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }
}
