package kz.kuanysh.newsapiapp2.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.kuanysh.newsapiapp2.extension.Result
import kz.kuanysh.newsapiapp2.domain.entety.Article

interface ArticleRepository {

    suspend fun loadArticles(searchQuery: String, forceRefresh: Boolean): Result<Unit>

    fun getArticles(): Flow<List<Article>>
}