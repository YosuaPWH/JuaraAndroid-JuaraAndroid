package com.yosuahaloho.retrokoran.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.yosuahaloho.retrokoran.ui.theme.RetroKoranTheme
import com.yosuahaloho.retrokoran.ui.theme.backgroundNews
import com.yosuahaloho.retrokoran.util.effectShimmer

/**
 * Created by Yosua on 12/05/2023
 */
@Composable
fun NewsItemShimmer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(123.dp)
    ) {
        Row(
            modifier = Modifier
                .background(backgroundNews)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier = modifier.weight(2f)) {
                Text(
                    text = "",
                    fontSize = 12.sp,
                    modifier = modifier
                        .width(80.dp)
                        .effectShimmer()
                )
                Text(
                    text = "",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .effectShimmer()
                )
                Text(
                    text = "",
                    fontSize = 12.sp,
                    modifier = modifier
                        .width(150.dp)
                        .effectShimmer()
                )
            }
            AsyncImage(
                model = "",
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = modifier
                    .weight(1f)
                    .width(100.dp)
                    .fillMaxSize()
                    .effectShimmer()
            )
        }
    }
}

@Composable
fun LayoutItemShimmer() {
    LazyColumn {
        items(5) {
            NewsItemShimmer()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewsItemShimmerPreview() {
    RetroKoranTheme {
        NewsItemShimmer()
    }
}