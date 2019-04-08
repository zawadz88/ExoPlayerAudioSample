package com.github.zawadz88.exoplayeraudiosample.test

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.zawadz88.exoplayeraudiosample.R
import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import de.mannodermaus.junit5.ActivityScenarioExtension
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class MainActivityTest {

    @Suppress("unused")
    @JvmField
    @RegisterExtension
    val scenarioExtension = ActivityScenarioExtension.launch<MainActivity>()

    val scenario: ActivityScenario<MainActivity> by lazy { scenarioExtension.scenario }

    @Test
    fun play_button_should_be_displayed() {
        // when
        scenario.moveToState(Lifecycle.State.RESUMED)

        //then
        onView(withId(R.id.togglePlaybackButton)).check(matches(isDisplayed()))
    }

    @Test
    fun prev_and_next_buttons_should_be_disabled() {
        // when
        scenario.moveToState(Lifecycle.State.RESUMED)

        //then
        onView(withId(R.id.nextButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.previousButton)).check(matches(not(isEnabled())))
    }
}
