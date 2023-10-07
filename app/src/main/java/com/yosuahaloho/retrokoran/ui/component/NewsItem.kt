package com.yosuahaloho.retrokoran.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.ui.theme.RetroKoranTheme
import com.yosuahaloho.retrokoran.ui.theme.backgroundNews
import com.yosuahaloho.retrokoran.util.effectShimmer

/**
 * Created by Yosua on 12/05/2023
 */
@Composable
fun NewsItem(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(123.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = modifier
                .background(backgroundNews)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "By NBC News",
                    fontSize = 12.sp
                )
                Text(
                    text = article.title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = article.publishedAt,
                    fontSize = 12.sp
                )
            }

            val painter = rememberAsyncImagePainter(article.urlToImage)
            if (painter.state is AsyncImagePainter.State.Loading) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = modifier
                        .weight(1f)
                        .width(100.dp)
                        .fillMaxSize()
                        .effectShimmer()
                )
            } else {
                Image(
                    painter = painter,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = modifier
                        .weight(1f)
                        .width(100.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewsItemPreview() {
    RetroKoranTheme {
        NewsItem(
            Article(
                title = "23 Rumah di Bojongkoneng Bogor Rusak Akibat Pergeseran Tanah",
                description = "Puluhan rumah di tiga kampung di Desa Bojongkoneng, Kabupaten Bogor, Jawa Barat, mengalami kerusakan akibat bencana alam pergeseran tanah.",
                url = "",
                urlToImage = "",
                publishedAt = "2023-05-21T01:32:54Z",
                author = "Meredith Deliso",
                content = "A lawsuit alleges a homebuilding company created a hostile working environment and discriminated "
            ),
            onClick = {}
        )
    }
}