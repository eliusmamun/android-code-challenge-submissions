package com.onefootball

import android.app.Instrumentation
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.onefootball.utils.EspressoIdlingResource
import com.onefootball.view.MyNewsActivity
import com.onefootball.view.NewsAdapter
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestMyNewsActivity {

    @get:Rule
    val activityRule = ActivityTestRule(MyNewsActivity::class.java, true, true)

    @Before
    fun registeringIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisteringIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun onLaunchSuccessfullyNewsData() {

        activityRule.activity.viewModelStore

        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.list_error))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.loading_view))
            .check(matches(not(isDisplayed())))

    }


    @Test
    fun testRecyclerViewScroll() {
        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.newsRecyclerView)
        val itemCount: Int? = recyclerView.adapter?.itemCount

        onView(withId(R.id.newsRecyclerView))
            .perform(
                RecyclerViewActions.scrollToPosition<NewsAdapter.NewsViewHolder>(
                    itemCount!!.minus(
                        1
                    )
                )
            )
    }


    @Test
    fun testOpenNewsLinkSuccessfully() {
        Intents.init()
        val expectedIntent = allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://onefootball.com/en/news/the-5-players-who-could-be-the-next-messi-or-ronaldo-en-26880141?variable=20190822")
        )
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        onView(withId(R.id.newsRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<NewsAdapter.NewsViewHolder>(
                0,
                click()
            )
        )

        intended(expectedIntent)
        Intents.release()
    }

}