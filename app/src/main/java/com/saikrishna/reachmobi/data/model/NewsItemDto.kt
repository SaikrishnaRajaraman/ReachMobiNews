package com.saikrishna.reachmobi.data.model

data class NewsItemDto(
    val source : NewsSource?,
    val author : String?,
    val title : String?,
    val description : String?,
    val url : String,
    val urlToImage : String?,
    val publishedAt : String?,
    val content : String?
)