package kz.kuanysh.newsapiapp2.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.kuanysh.newsapiapp2.data.data_source.local.dao.ArticleDao
import kz.kuanysh.newsapiapp2.data.data_source.remote.NetworkApi
import kz.kuanysh.newsapiapp2.domain.entety.Article
import kz.kuanysh.newsapiapp2.domain.repository.ArticleRepository
import kz.kuanysh.newsapiapp2.extension.Result
import kz.kuanysh.newsapiapp2.extension.apiCall

class ArticleRepositoryImpl(
    private val remoteSource: NetworkApi,
    private val localSource: ArticleDao,
) : ArticleRepository {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    private var currentSearchQuery = ""
    private var currentPage = 1

    override suspend fun loadArticles(searchQuery: String, forceRefresh: Boolean): Result<Unit> =
        apiCall {
            if (forceRefresh) {
                currentPage = 1
                localSource.deleteAllRecords()
            }

            val articles = remoteSource.getEverything(
                searchQuery = searchQuery,
                page = currentPage++,
                pageSize = DEFAULT_PAGE_SIZE,
            ).toLocal()

            if (searchQuery == currentSearchQuery) {
                localSource.insertArticles(articles)
            } else {
                localSource.refreshArticles(articles)
                currentSearchQuery = searchQuery
                currentPage = 1
            }
        }

    override fun getArticles(): Flow<List<Article>> =
        localSource.getArticles().map { cachedArticles ->
            cachedArticles.map { it.toDomain() }
        }
}