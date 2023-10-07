package com.yosuahaloho.retrokoran

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Yosua on 24/05/2023
 */
@HiltAndroidTest
class TestFeature {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun displaySearch() {
        composeTestRule.onNodeWithTag("Search")
    }
}