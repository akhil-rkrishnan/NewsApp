package app.android.newsapp.data.network.repository

import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.utils.ApiResult
/**
 * Interface for News Repository
 **/
interface NewsRepository {
    suspend fun getNewsList(source: String): ApiResult<NewsResponse>
}