package com.yosuahaloho.retrokoran.ui.page.detail_news

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.yosuahaloho.retrokoran.R
import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.ui.theme.DMSerif
import com.yosuahaloho.retrokoran.ui.theme.RetroKoranTheme
import com.yosuahaloho.retrokoran.ui.theme.backgroundPage
import com.yosuahaloho.retrokoran.ui.theme.blackColor
import com.yosuahaloho.retrokoran.ui.theme.pinkColor
import com.yosuahaloho.retrokoran.util.effectShimmer

/**
 * Created by Yosua on 14/05/2023
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailNewsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailNewsViewModel = hiltViewModel(),
    article: Article,
) {
    viewModel.getContent(article.url)

    val content = viewModel.content.collectAsState().value

    viewModel.checkIsBookmarkedNews(article.title)

    val isBookmark by viewModel.isBookmarkedNews.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = blackColor,
                onClick = {
                    viewModel.changeBookmarkStatus(article)
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_save),
                    contentDescription = "",
                    tint = if (isBookmark) pinkColor else backgroundPage
                )
            }
        },
    ) {
        Column(
            modifier = modifier
                .padding(10.dp)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "RetroKoran",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                fontFamily = DMSerif,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Divider(
                color = blackColor,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = article.publishedAt,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                color = blackColor,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
            Text(
                text = article.title,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                fontFamily = DMSerif,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Text(
                text = "Published by NBC News",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            val painter = rememberAsyncImagePainter(article.urlToImage)
            if (painter.state is AsyncImagePainter.State.Loading) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .effectShimmer()
                )
            } else {
                Image(
                    painter = painter,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                )
            }
            Text(
                text = "Author: ${article.author}",
                fontStyle = FontStyle.Italic
            )
            Text(
                text = content,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify
            )
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailNewsScreenPreview() {
    RetroKoranTheme {
        Box(modifier = Modifier.background(backgroundPage)) {
            DetailNewsScreen(
                article = Article(
                    title = "23 Rumah di Bojongkoneng Bogor Rusak Akibat Pergeseran Tanah",
                    description = "Puluhan rumah di tiga kampung di Desa Bojongkoneng, Kabupaten Bogor, Jawa Barat, mengalami kerusakan akibat bencana alam pergeseran tanah.",
                    url = "",
                    urlToImage = "",
                    publishedAt = "2023-05-21T01:32:54Z",
                    author = "Meredith Deliso",
                    content = "A lawsuit alleges a homebuilding company created a hostile working environment and discriminated "
                ),
            )
        }
    }
}