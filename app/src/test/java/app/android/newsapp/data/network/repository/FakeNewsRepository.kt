package app.android.newsapp.data.network.repository

import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.utils.ApiResult


class FakeNewsRepository : NewsRepository {

    override suspend fun getNewsList(source: String): ApiResult<NewsResponse> {
        return ApiResult.Success(getNews(source))
    }

    private fun getNews(source: String): NewsResponse {

        return if (source == "techcrunch") {
            val articles = arrayListOf<NewsResponse.Article>()
            repeat(10) {
                articles.add(
                    NewsResponse.Article(
                        author = "author-$it",
                        description = "description-$it",
                        content = "content-$it",
                        publishedAt = "publishedAt-${System.currentTimeMillis()}",
                        source = NewsResponse.Article.Source(
                            id = source,
                            name = "TechCruch"
                        ), title = "Title-$it",
                        url = "url-$it",
                        urlToImage = "urlToImage-$it"
                    )
                )
            }
            NewsResponse(articles = articles, status = "success", totalResults = articles.size)
        } else if (source == "the-wall-street-journal") {
            val articles = arrayListOf<NewsResponse.Article>()
            repeat(25) {
                articles.add(
                    NewsResponse.Article(
                        author = "author-$it",
                        description = "description-$it",
                        content = "content-$it",
                        publishedAt = "publishedAt-${System.currentTimeMillis()}",
                        source = NewsResponse.Article.Source(
                            id = source,
                            name = "The Wall Street Journal"
                        ), title = "Title-$it",
                        url = "url-$it",
                        urlToImage = "urlToImage-$it"
                    )
                )
            }
            NewsResponse(articles = articles, status = "success", totalResults = articles.size)
        } else {
            NewsResponse(
                articles = emptyList(),
                status = "failed",
                totalResults = 0
            )
        }

    }
}


