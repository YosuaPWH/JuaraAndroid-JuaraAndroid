package com.yosuahaloho.retrokoran.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yosuahaloho.retrokoran.R

/**
 * Created by Yosua on 23/05/2023
 */
@Composable
fun EmptyAnimation() {
    val compositi by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.not_found))
    val load by animateLottieCompositionAsState(composition = compositi, iterations = LottieConstants.IterateForever)
    LottieAnimation(composition = compositi, progress = { load })
}