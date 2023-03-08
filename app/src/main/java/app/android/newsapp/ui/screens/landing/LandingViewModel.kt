package app.android.newsapp.ui.screens.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.android.newsapp.BuildConfig
import app.android.newsapp.core.EventBus
import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.data.network.repository.NewsRepository
import app.android.newsapp.utils.ErrorBody
import app.android.newsapp.utils.ifFailed
import app.android.newsapp.utils.ifSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val eventBus: EventBus
) : ViewModel() {

    private val _newsFlow = MutableStateFlow(listOf<NewsResponse.Article>())
    val newsList get() = _newsFlow.asStateFlow()

    var selectedArticle: NewsResponse.Article? = null
        private set
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var apiErrorBody: ErrorBody? by mutableStateOf(null)
        private set

    init {
        loadNewsList()
    }

    fun loadNewsList() {
        updateLoading(true)
        viewModelScope.launch {
            newsRepository.getNewsList(BuildConfig.SOURCE).apply {
                ifSuccess { response ->
                    _newsFlow.update { response.articles.sortedBy { it.publishedAt } }
                    updateLoading(false)
                    apiErrorBody = null
                }
                ifFailed { uiText, errorBody ->
                    eventBus.sendToast(uiText)
                    updateLoading(false)
                    apiErrorBody = errorBody
                }
            }
        }
    }

    fun setSelectedArticle(article: NewsResponse.Article) {
        selectedArticle = article
    }

    fun updateLoading(value: Boolean) {
        isLoading = value
    }
}