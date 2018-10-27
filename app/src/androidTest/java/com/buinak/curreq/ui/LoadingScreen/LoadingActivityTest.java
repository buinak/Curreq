package com.buinak.curreq.ui.LoadingScreen;

import android.os.SystemClock;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.intent.Intents.intended;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoadingActivityTest {

    @Rule
    public ActivityTestRule<LoadingActivity> activityTestRule =
            new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void loadingActivity_shouldCloseAfter3Seconds(){
        SystemClock.sleep(3000);
        assertTrue(activityTestRule.getActivity().isFinishing());
    }

}