package com.saikrishna.reachmobi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Entity(tableName = "newsitems")
data class NewsItem(
    val source: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    @PrimaryKey val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val isFavorite: Boolean = false
)


fun NewsItemDto.toNewsItem(): NewsItem {
    return NewsItem(
        source = source?.name,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt?.toDisplayDateString(),
        content = content,
        isFavorite = false
    )
}


fun String.toDate(): Date? = try {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.parse(this)
} catch (e: Exception) {
    e.printStackTrace()
    null
}

fun Date.toDisplayDateString(locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat("dd/MM/yyyy", locale).format(this)


fun String.toDisplayDateString(
    locale: Locale = Locale.getDefault()
): String = this.toDate()?.toDisplayDateString(locale) ?: ""
