package kz.kuanysh.newsapiapp2.data.data_source.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kz.kuanysh.newsapiapp2.data.data_source.local.entety.LocalArticle

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<LocalArticle>)

    @Query("DELETE FROM Article")
    suspend fun deleteAllRecords()

    @Transaction
    suspend fun refreshArticles(articles: List<LocalArticle>) {
        deleteAllRecords()
        insertArticles(articles)
    }

    @Query("SELECT * FROM Article")
    fun getArticles(): Flow<List<LocalArticle>>

}