package app.android.newsapp.ui.screens.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.android.newsapp.BuildConfig
import app.android.newsapp.core.EventBus
import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.data.network.repository.NewsRepository
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

    init {
        loadNewsList()
    }

    fun loadNewsList() {
        viewModelScope.launch {
            newsRepository.getNewsList(BuildConfig.SOURCE).apply {
                ifSuccess { response ->
                    _newsFlow.update { response.articles.sortedBy { it.publishedAt } }
                }
                ifFailed {
                    eventBus.sendToast(it)
                }
            }
        }
    }

    fun setSelectedArticle(article: NewsResponse.Article) {
        selectedArticle = article
    }
}