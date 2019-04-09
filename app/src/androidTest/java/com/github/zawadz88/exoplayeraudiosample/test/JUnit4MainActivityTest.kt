package com.github.zawadz88.exoplayeraudiosample.test

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.zawadz88.exoplayeraudiosample.R
import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JUnit4MainActivityTest {

    @Suppress("unused")
    @JvmField
    @Rule
    val scenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun play_button_should_be_displayed() {
        // when
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)

        // then
        onView(withId(R.id.togglePlaybackButton)).check(matches(isDisplayed()))
    }

    @Test
    fun prev_and_next_buttons_should_be_disabled() {
        // when
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)

        // then
        onView(withId(R.id.nextButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.previousButton)).check(matches(not(isEnabled())))
    }
}
