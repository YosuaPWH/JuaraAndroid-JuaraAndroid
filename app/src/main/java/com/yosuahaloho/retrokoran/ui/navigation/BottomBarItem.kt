package com.yosuahaloho.retrokoran.ui.navigation

import androidx.annotation.DrawableRes

/**
 * Created by Yosua on 11/05/2023
 */
data class BottomBarItem(
    val label: String,
    @DrawableRes
    val icon: Int,
    val screen: Screen,
    val contentDescription: String,
)
