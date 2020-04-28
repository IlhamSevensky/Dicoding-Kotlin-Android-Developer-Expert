package com.ilham.made.fourthsubmission.activitybehaviour

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ilham.made.fourthsubmission.R
import com.ilham.made.fourthsubmission.ui.search.SearchActivity
import com.ilham.made.fourthsubmission.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(SearchActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun searchBehaviour() {

        onView(withId(R.id.search_view))
            .check(matches(isDisplayed()))
        onView(withId(R.id.search_view))
            .perform(click())
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("Real Madrid vs Barcelona")).perform(pressImeActionButton())


        onView(withId(R.id.rv_search_match))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_search_match))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Thread.sleep(2000)
        onView(withId(R.id.rv_search_match))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))

        onView(withId(R.id.search_view))
            .perform(click())
        onView(isAssignableFrom(EditText::class.java))
            .perform(clearText(), typeText("Juventus vs Inter")).perform(pressImeActionButton())

        onView(withId(R.id.rv_search_match))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_search_match))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Thread.sleep(2000)
        onView(withId(R.id.rv_search_match))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))

    }
}