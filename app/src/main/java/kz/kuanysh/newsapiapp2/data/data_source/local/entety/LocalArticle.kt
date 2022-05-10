package kz.kuanysh.newsapiapp2.data.data_source.local.entety

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.kuanysh.newsapiapp2.domain.entety.Article

@Entity(tableName = "Article")
class LocalArticle(
    @PrimaryKey
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "content")
    val content: String?,
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String?,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String?,
) {

    fun toDomain() = Article(
        title = title,
        description = description ?: "",
        content = content ?: "",
        urlToImage = urlToImage,
    )
}