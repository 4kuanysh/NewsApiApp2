package kz.kuanysh.newsapiapp2.domain.usecase

import kz.kuanysh.newsapiapp2.domain.repository.ArticleRepository

class GetArticlesUseCase(
    private val repository: ArticleRepository
) {

    operator fun invoke() =
        repository.getArticles()
}