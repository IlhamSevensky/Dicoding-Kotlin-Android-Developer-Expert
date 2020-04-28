package com.ilham.made.lastsubmission.activitybehaviour

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.ui.search.team.SearchTeamActivity
import com.ilham.made.lastsubmission.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTeamActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(SearchTeamActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun searchTeamBehaviour() {

        Espresso.onView(ViewMatchers.withId(R.id.search_view_team))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.search_view_team))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java))
            .perform(ViewActions.typeText("Arsenal")).perform(ViewActions.pressImeActionButton())


        Espresso.onView(ViewMatchers.withId(R.id.rv_search_team))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rv_search_team))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.rv_search_team))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))

        Espresso.onView(ViewMatchers.withId(R.id.search_view_team))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java))
            .perform(ViewActions.clearText(), ViewActions.typeText("Barcelona")).perform(
                ViewActions.pressImeActionButton()
            )

        Espresso.onView(ViewMatchers.withId(R.id.rv_search_team))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rv_search_team))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.rv_search_team))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))

    }
}