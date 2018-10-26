package com.buinak.curreq.ui.MainScreen;

import com.buinak.curreq.R;
import com.buinak.curreq.ui.AddScreen.AddActivity;
import com.buinak.curreq.ui.SettingsScreen.SettingsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

//    @Rule
//    public ActivityTestRule<MainActivity> activityTestRule =
//            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickingFloatingActionButton_shouldStartAddActivity(){
        onView(withId(R.id.floatingActionButton)).perform(click());
        intended(hasComponent(AddActivity.class.getName()));
    }

    @Test
    public void clickingSettingsButton_shouldStartSettingsActivity(){
        onView(withId(R.id.menu_android)).perform(click());
        onView(withText("Settings")).perform(click());
        intended(hasComponent(SettingsActivity.class.getName()));
    }

    @Test
    public void clickingExtendedMenuIcon_shouldShowMenu(){
        onView(withId(R.id.menu_android)).perform(click());
        onView(withText("Settings")).check(matches(isDisplayed()));
    }
}