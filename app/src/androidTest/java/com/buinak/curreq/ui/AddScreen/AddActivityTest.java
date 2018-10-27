package com.buinak.curreq.ui.AddScreen;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddActivityTest {
    @Rule
    public ActivityTestRule<AddActivity> activityTestRule =
            new ActivityTestRule<>(AddActivity.class);


    @Test
    public void selectingTwoElements_shouldFinishActivity(){
        onView(withText("AUD")).perform(click());
        onView(withText("USD")).perform(click());

        assertTrue(activityTestRule.getActivity().isFinishing());
    }

    @Test
    public void selectingSameElement_shouldDoNothing(){
        onView(withText("AUD")).perform(click());
        onView(withText("AUD")).perform(click());
        onView(withText("AUD")).perform(click());
        onView(withText("AUD")).perform(click());
        onView(withText("AUD")).perform(click());
        onView(withText("AUD")).perform(click());

        onView(withText("USD")).perform(click());
        onView(withText("USD")).perform(click());
        onView(withText("USD")).perform(click());
        onView(withText("USD")).perform(click());

        assertFalse(activityTestRule.getActivity().isFinishing());
    }

}