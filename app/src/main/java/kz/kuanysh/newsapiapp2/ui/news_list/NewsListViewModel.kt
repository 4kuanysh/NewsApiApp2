package kz.kuanysh.newsapiapp2.ui.news_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kz.kuanysh.newsapiapp2.domain.entety.Article
import kz.kuanysh.newsapiapp2.domain.usecase.GetArticlesUseCase
import kz.kuanysh.newsapiapp2.domain.usecase.LoadArticlesUseCase
import kz.kuanysh.newsapiapp2.extension.Result

class NewsListViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val loadArticlesUseCase: LoadArticlesUseCase,
) : ViewModel() {

    companion object {
        private const val USER_SEARCH_INPUT_DELAY = 500L
    }

    private var loadArticlesJob: Job? = null

    private val _exception = MutableStateFlow<Exception?>(null)
    val exception get() = _exception.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles get() = _articles.asStateFlow()

    var searchQuery = ""
        set(value) {
            field = value
            onSearchQueryChanged()
        }

    init {
        getArticles()
    }

    fun onArticleClicked(article: Article) {

    }

    fun loadNextPage() {
        if (loadArticlesJob?.isActive?.not() == true) {
            loadArticlesJob = viewModelScope.launch {
                loadArticle(searchQuery, forceRefresh = false)
            }
        }
    }

    fun onRefresh() {
        loadArticlesJob?.cancel()
        viewModelScope.launch {
            loadArticle(searchQuery, forceRefresh = true)
        }
    }

    private fun onSearchQueryChanged() {
        loadArticlesJob?.cancel()
        loadArticlesJob = viewModelScope.launch {
            delay(USER_SEARCH_INPUT_DELAY)
            loadArticle(searchQuery, forceRefresh = false)
        }
    }

    private fun getArticles() {
        viewModelScope.launch {
            getArticlesUseCase.invoke().collect {
                _articles.value = it
            }
        }
    }

    private suspend fun loadArticle(searchQuery: String, forceRefresh: Boolean) {
        _isLoading.value = true
        val result = loadArticlesUseCase(searchQuery, forceRefresh)
        if (result is Result.Failure) {
            Log.d("taaaag", result.exception.toString())
            _exception.value = result.exception
        }
        _isLoading.value = false
    }
}