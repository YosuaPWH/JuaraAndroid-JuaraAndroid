package com.yosuahaloho.retrokoran.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.yosuahaloho.retrokoran.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Yosua on 24/05/2023
 */
@HiltAndroidTest
class RetroKoranAppTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()

    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithTag("home_page").performClick()
        composeTestRule.onNodeWithTag("saved_page").performClick()
        composeTestRule.onNodeWithTag("about_page").performClick()
    }

    @Test
    fun checkIfBookmarkScreenIsDisplayed() {
        composeTestRule.onNodeWithTag("saved_page").performClick()
        composeTestRule.onNodeWithTag("bookmark").assertIsDisplayed()
    }

    @Test
    fun navHost_checkSearchIsDisplayed() {
        composeTestRule.onNodeWithTag("Search").assertIsDisplayed()
    }

    @Test
    fun checkIfSearchBarIsActive() {
        composeTestRule.onNodeWithTag("Search").performClick()
        val searchBar = composeTestRule.onNodeWithTag("SearchBar")
        searchBar.assertIsDisplayed()
        searchBar.performClick()
    }

    @Test
    fun checkFotoProfilIsDisplayed() {
        composeTestRule.onNodeWithTag("about_page").performClick()
        composeTestRule.onNodeWithTag("FotoProfil").assertIsDisplayed()
    }
}