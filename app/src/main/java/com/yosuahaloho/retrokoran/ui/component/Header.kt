package com.yosuahaloho.retrokoran.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yosuahaloho.retrokoran.ui.page.home.HomeViewModel
import com.yosuahaloho.retrokoran.ui.theme.DMSerif
import com.yosuahaloho.retrokoran.ui.theme.textColor

/**
 * Created by Yosua on 23/05/2023
 */

@Composable
fun Header(
    viewModel: HomeViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onClickSearch: (Boolean) -> Unit
) {
    var active by remember { mutableStateOf(false) }
    val searchFocus = remember { FocusRequester() }

    SearchBarItem(
        searchFocus = searchFocus,
        viewModel = viewModel,
        active = active,
        navController = navController,
        onClickSearch = onClickSearch,
        onActive = { isActive ->
            active = isActive
        }
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "RetroKoran",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                fontFamily = DMSerif
            )
            Text(
                text = "Pilihan Koran Hari Ini",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = DMSerif
            )
        }
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = textColor,
            modifier = modifier
                .size(30.dp)
                .testTag("Search")
                .clickable {
                    active = true
                    onClickSearch(true)
                }
        )
    }
}