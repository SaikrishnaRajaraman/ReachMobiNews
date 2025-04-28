package com.saikrishna.reachmobi.presentation.news_item_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.saikrishna.reachmobi.data.model.NewsItemDto
import com.saikrishna.reachmobi.data.model.NewsSource
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.data.model.toNewsItem
import com.saikrishna.reachmobi.ui.theme.ReachMobiTheme
import com.saikrishna.reachmobi.R

@Composable
fun NewsListItem(
    newsItem: NewsItem,
    onClick: () -> Unit,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    isFavoriteScreen: Boolean = false
) {

    ReachMobiTheme {
        Row(
            modifier = modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CoilImage(
                imageUrl = newsItem.urlToImage ?: "",
                contentDescription = newsItem.title,
                modifier = Modifier.clip(
                    RoundedCornerShape(8)
                )
            )

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    newsItem.title ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(newsItem.source ?: "")
                Text(newsItem.publishedAt ?: "", fontSize = 8.sp)
            }

            IconButton(onClick = onFavorite) {
                if (isFavoriteScreen) {
                    Icon(
                        Icons.Rounded.Delete,
                        contentDescription = ""
                    )
                } else {
                    Icon(
                        if (newsItem.isFavorite) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = ""
                    )
                }
            }

        }
    }
}

@Composable
fun CoilImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .size(64.dp),
        placeholder = painterResource(R.drawable.image_placeholder), // optional
//        error = painterResource(R.drawable.image_error),       // optional
        contentScale = ContentScale.Crop
    )
}


@PreviewLightDark
@Composable
private fun NewsListItemPreview() {

    ReachMobiTheme {
        NewsListItem(
            newsItem = newsItemDto, onClick = {}, onFavorite = {}, modifier = Modifier.background(
                MaterialTheme.colorScheme.primaryContainer
            )
        )
    }

}


internal val newsItemDto = NewsItemDto(
    source = NewsSource(id = "espn", name = "ESPN"),
    author = "",
    title = "Cole Anthony carries Magic past Hawks, into playoffs - ESPN",
    description = "Cole Anthony scored 26 points off the bench to help the Magic beat the Hawks on Tuesday and set up a first round series vs. the Celtics.",
    url = "https://www.espn.com/nba/story/_/id/44692284/cole-anthony-carries-magic-hawks-playoffs",
    urlToImage = "https://a.espncdn.com/combiner/i?img=%2Fphoto%2F2025%2F0416%2Fr1479410_1024x576_16%2D9.jpg",
    publishedAt = "2025-04-16T02:28:00Z",
    content = "Apr 15, 2025, 10:28 PM ET\\r\\nORLANDO, Fla. -- Cole Anthony came off the Magic bench with 26 points and six assists to lead the Orlando Magic to a 120-95 win over the Atlanta Hawks on Tuesday night in t… [+1238 chars]"
).toNewsItem()