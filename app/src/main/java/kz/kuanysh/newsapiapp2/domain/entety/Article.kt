package kz.kuanysh.newsapiapp2.domain.entety

data class Article(
    val title: String,
    val description: String,
    val content: String,
    val urlToImage: String?,
)