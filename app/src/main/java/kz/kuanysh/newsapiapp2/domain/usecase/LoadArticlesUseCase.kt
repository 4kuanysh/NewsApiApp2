package kz.kuanysh.newsapiapp2.domain.usecase

import kz.kuanysh.newsapiapp2.domain.repository.ArticleRepository

class LoadArticlesUseCase(
    private val repository: ArticleRepository
) {

    suspend operator fun invoke(searchQuery: String, forceRefresh: Boolean) =
        repository.loadArticles(searchQuery, forceRefresh)
}