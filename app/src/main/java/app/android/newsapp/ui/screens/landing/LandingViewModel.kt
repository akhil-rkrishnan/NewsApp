package app.android.newsapp.ui.screens.landing

import androidx.annotation.VisibleForTesting
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

/**
 * View model class for Landing screen
 **/
@HiltViewModel
class LandingViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val eventBus: EventBus
) : ViewModel() {

    // Mutable state flow to hold the News state
    private val _newsState = MutableStateFlow(NewsState())
    val newsState = _newsState.asStateFlow()

    // Variable to store the newsSource
    private var newsSource: String = BuildConfig.NEWS_SOURCE

    init {
        loadNewsList()
    }

    /**
     * Method to load news list from server
     **/
    fun loadNewsList() {
        updateLoading(true)
        viewModelScope.launch {
            newsRepository.getNewsList(newsSource).apply {
                ifSuccess { response ->
                    _newsState.update {
                        NewsState(
                            totalItems = response.totalResults,
                            status = response.status,
                            articles = response.articles,
                            providerName = response.articles.let { list ->
                                if (list.isNotEmpty())
                                    list.first().source.name
                                else ""
                            }
                        )
                    }
                    updateLoading(false)
                }

                ifFailed { uiText, errorBody ->
                    _newsState.update { NewsState(apiErrorBody = errorBody) }
                    eventBus.sendToast(uiText)
                    updateLoading(false)
                }
            }

        }
    }

    /**
     * Method to set the selected article from the list of news
     * @param article News article
     **/
    fun setSelectedArticle(article: NewsResponse.Article) {
        _newsState.update { it.copy(selectedArticle = article) }
    }

    /**
     * Method to upload the loading value
     * @param value true or false
     **/
    private fun updateLoading(value: Boolean) {
        _newsState.update { it.copy(isLoading = value) }
    }

    //This method is used only for unit testing
    @VisibleForTesting
    fun setSource(source: String) {
        newsSource = source
        loadNewsList()
    }

}