package com.yosuahaloho.retrokoran.ui.component

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Created by Yosua on 23/05/2023
 */
@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}