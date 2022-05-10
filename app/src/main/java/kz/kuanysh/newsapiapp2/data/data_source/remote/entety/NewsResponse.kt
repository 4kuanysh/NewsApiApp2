package kz.kuanysh.newsapiapp2.data.data_source.remote.entety

import com.google.gson.annotations.SerializedName
import kz.kuanysh.newsapiapp2.data.data_source.local.entety.LocalArticle


class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
) {

    class Article(
        @SerializedName("author")
        val author: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("publishedAt")
        val publishedAt: String?,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String?,
        @SerializedName("urlToImage")
        val urlToImage: String?,
    )

    fun toLocal() = articles.map {
        LocalArticle(
            title = it.title,
            description = it.description,
            content = it.content,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
        )
    }
}