package com.example.kaka.androidbakery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.kaka.androidbakery.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Kaka on 6/6/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    public static final int RECIPE_LIST_ITEM_TO_CLICK = 2;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void openRecipeSwipeToStepOpenStepItem_ClickNextStep() throws InterruptedException {

        Thread.sleep(1000);
        onView(withId(R.id.rv_recipe_list))
                .perform(scrollToPosition(3));

        Thread.sleep(1000);
        onView(withId(R.id.rv_recipe_list))
                .perform(actionOnItemAtPosition(RECIPE_LIST_ITEM_TO_CLICK, click()));

        Thread.sleep(1000);
        onView(allOf(withId(R.id.rv_ingredients_list), isDisplayed()))
                .check(matches(hasDescendant(withId(R.id.tv_ingredient))));

        onView(withId(R.id.viewPager)).perform(swipeLeft());

        Thread.sleep(1000);
        onView(withId(R.id.rv_steps_list))
                .perform(actionOnItem(hasDescendant(withText("Recipe Introduction")), click()));

        onView(withId(R.id.tv_recipe_step_instruction))
                .check(matches(withText(mMainActivityTestRule.getActivity().getResources().getString(R.string.step_descriptions_for_testing))));

        Thread.sleep(1000);
        onView(withId(R.id.bt_next_step))
                .perform(click());
//
//        Thread.sleep(1000);
//        onView(withId(R.id.tv_recipe_step_instruction))
//                .check(matches(withText(mMainActivityTestRule.getActivity().getResources().getString(R.string.recipe_3_step_2_instructions_for_testiing))));


    }

}
