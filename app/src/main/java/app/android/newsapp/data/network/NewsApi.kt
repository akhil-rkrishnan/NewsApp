package app.android.newsapp.data.network

import app.android.newsapp.BuildConfig
import app.android.newsapp.data.models.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for News Api
 **/
interface NewsApi {
    @GET("top-headlines?apiKey=${BuildConfig.API_KEY}")
    suspend fun getNewsList(@Query("sources") source: String): NewsResponse
}