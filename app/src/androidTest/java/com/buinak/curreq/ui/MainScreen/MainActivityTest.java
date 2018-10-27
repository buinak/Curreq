package com.buinak.curreq.ui.MainScreen;

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.buinak.curreq.R;
import com.buinak.curreq.ui.AddScreen.AddActivity;
import com.buinak.curreq.ui.SettingsScreen.SettingsActivity;

import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeoutException;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

    @Test
    public void selectingTwoCurrencies_shouldAddARate(){
        RecyclerView recyclerView = intentsTestRule.getActivity().findViewById(R.id.main_screen_recycler_view);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        int oldCount = adapter.getItemCount();

        onView(withId(R.id.floatingActionButton)).perform(click());
        intended(hasComponent(AddActivity.class.getName()));

        onView(withText("BRL")).perform(click());
        onView(withText("KRW")).perform(click());

        int newCount = adapter.getItemCount();
        assertTrue(newCount > oldCount);
    }

    @Test
    public void pullingDownOnTheList_shouldUpdateData(){
        TextView dateView = intentsTestRule.getActivity().findViewById(R.id.testView_date);
        String oldDate = dateView.getText().toString();

        onView(withId(R.id.swipeRefreshLayout)).perform(swipeDown());
        SystemClock.sleep(2000);
        String newDate = dateView.getText().toString();

        assertNotEquals(oldDate, newDate);

    }
}