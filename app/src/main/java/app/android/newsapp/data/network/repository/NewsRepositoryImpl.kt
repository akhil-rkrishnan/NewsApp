package app.android.newsapp.data.network.repository

import app.android.newsapp.data.network.NewsApi
import app.android.newsapp.utils.ApiResult
import app.android.newsapp.utils.initApiCall

/**
 * Class for News repository
 **/
class NewsRepositoryImpl(private val api: NewsApi) : NewsRepository {
    override suspend fun getNewsList(source: String) = initApiCall {
        ApiResult.Success(api.getNewsList(source))
    }

}