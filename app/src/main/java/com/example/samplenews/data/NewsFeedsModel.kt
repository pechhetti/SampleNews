package com.example.samplenews.data

import com.google.gson.annotations.SerializedName


data class NewsFeedsModel(
    @SerializedName("status") val status: String? = null,
    @SerializedName("copyright") val copyright: String? = null,
    @SerializedName("num_results") val numResults: Int = 0,
    @SerializedName("results") val results: List<Result>? = null
)

data class Result(
    @SerializedName("url") val url: String? = null,
    @SerializedName("adx_keywords") val adxKeywords: String? = null,
    @SerializedName("column") val column: Any? = null,
    @SerializedName("section") val section: String? = null,
    @SerializedName("byline") val byline: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("abstract") val abstract: String? = null,
    @SerializedName("published_date") val publishedDate: String? = null,
    @SerializedName("source") val source: String? = null,
    @SerializedName("id") val id: Long = 0,
    @SerializedName("asset_id") val assetId: Long = 0,
    @SerializedName("views") val views: Int = 0,
    @SerializedName("media") val media: List<Medium>? = null,
    @SerializedName("uri") val uri: String? = null
)

data class Medium(
    @SerializedName("type") val type: String? = null,
    @SerializedName("subtype") val subtype: String? = null,
    @SerializedName("caption") val caption: String? = null,
    @SerializedName("copyright") val copyright: String? = null,
    @SerializedName("approved_for_syndication") val approvedForSyndication: Int = 0,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadatum>? = null
)

data class MediaMetadatum(
    @SerializedName("url") val url: String? = null,
    @SerializedName("format") val format: String? = null,
    @SerializedName("height") val height: Int = 0,
    @SerializedName("width") val width: Int = 0
)
