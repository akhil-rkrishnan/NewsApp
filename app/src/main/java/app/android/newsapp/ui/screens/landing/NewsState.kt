package app.android.newsapp.ui.screens.landing

import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.utils.ErrorBody

/**
 * data class to hold the news state
 **/
data class NewsState(
    val totalItems: Int = 0,
    val status: String = "none",
    val articles: List<NewsResponse.Article> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val apiErrorBody: ErrorBody? = null,
    val selectedArticle: NewsResponse.Article? = null,
    val providerName: String = ""
)