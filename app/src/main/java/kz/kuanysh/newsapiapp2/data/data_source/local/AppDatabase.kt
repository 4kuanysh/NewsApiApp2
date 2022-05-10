package kz.kuanysh.newsapiapp2.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.kuanysh.newsapiapp2.data.data_source.local.dao.ArticleDao
import kz.kuanysh.newsapiapp2.data.data_source.local.entety.LocalArticle

@Database(
    entities = [LocalArticle::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        const val DB_NAME = "newsapi.db"
    }

}