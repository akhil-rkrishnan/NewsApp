package app.android.newsapp.data.network.repository

import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.utils.ApiResult
import app.android.newsapp.utils.ErrorBody
import app.android.newsapp.utils.UiText


class FakeNewsRepository : NewsRepository {
    companion object Error {
        private var shouldReturnError = false
        fun setShouldReturnError(value: Boolean) {
            shouldReturnError = value
        }
    }

    fun setError(value: Boolean) {
        setShouldReturnError(value)
    }

    override suspend fun getNewsList(source: String): ApiResult<NewsResponse> {
        if (shouldReturnError)
            return ApiResult.Failed(
                UiText.DynamicString("Fail test case"), ErrorBody(
                    status = "Load Failed",
                    code = "invalidTestCase",
                    message = "Fail test forced by user"
                )
            )
        return ApiResult.Success(getNews(source))
    }

    private fun getNews(source: String): NewsResponse {
        return when (source) {
            "techcrunch" -> {
                val articles = arrayListOf<NewsResponse.Article>()
                repeat(10) {
                    articles.add(
                        NewsResponse.Article(
                            author = "TechCruch author-$it",
                            description = "TechCruch description-$it",
                            content = "TechCruch content-$it",
                            publishedAt = "TechCruch publishedAt-${System.currentTimeMillis()}",
                            source = NewsResponse.Article.Source(
                                id = source,
                                name = "TechCruch"
                            ), title = "TechCruch Title-$it",
                            url = "TechCruch url-$it",
                            urlToImage = "TechCruch urlToImage-$it"
                        )
                    )
                }
                NewsResponse(articles = articles, status = "success", totalResults = articles.size)
            }
            "the-wall-street-journal" -> {
                val articles = arrayListOf<NewsResponse.Article>()
                repeat(25) {
                    articles.add(
                        NewsResponse.Article(
                            author = "Wall street author-$it",
                            description = "Wall street description-$it",
                            content = "Wall streetcontent-$it",
                            publishedAt = "Wall street publishedAt-${System.currentTimeMillis()}",
                            source = NewsResponse.Article.Source(
                                id = source,
                                name = "Wall street The Wall Street Journal"
                            ), title = "Wall street Title-$it",
                            url = "Wall street url-$it",
                            urlToImage = "Wall street urlToImage-$it"
                        )
                    )
                }
                NewsResponse(articles = articles, status = "success", totalResults = articles.size)
            }
            else -> {
                NewsResponse(
                    articles = emptyList(),
                    status = "failed",
                    totalResults = 0
                )
            }
        }

    }
}


