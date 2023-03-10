package app.android.newsapp.data.network.repository

import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.utils.ApiResult
import app.android.newsapp.utils.ErrorBody
import app.android.newsapp.utils.UiText

/**
 * Test class for fake news repository
 **/
class FakeNewsRepository : NewsRepository {

    init {
        // by default error will be set to false. if we need to test the fail case manually set the boolean from the test class.
        setError(false)
    }


    companion object {
        const val TECH_CRUNCH = "techcrunch"
        const val WALL_STREET_JOURNAL = "the-wall-street-journal"
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

    /**
     * Method to get fake news based on source
     * @param source News source
     **/
    private fun getNews(source: String): NewsResponse {
        return when (source) {
            TECH_CRUNCH -> {
                val articles = arrayListOf<NewsResponse.Article>()
                repeat(10) {
                    articles.add(
                        NewsResponse.Article(
                            author = "TechCrunch author-$it",
                            description = "TechCrunch description-$it",
                            content = "TechCrunch content-$it",
                            publishedAt = "TechCrunch publishedAt-${System.currentTimeMillis()}",
                            source = NewsResponse.Article.Source(
                                id = source,
                                name = "TechCrunch"
                            ), title = "TechCrunch Title-$it",
                            url = "https://techcrunch.com/2023/03/08/envisics-raises-50m-at-a-500m-valuation-for-its-in-car-holographic-tech/$it",
                            urlToImage = "https://techcrunch.com/wp-content/uploads/2023/03/Img-7.jpg?resize=1200,67$it"
                        )
                    )
                }
                NewsResponse(articles = articles, status = "success", totalResults = articles.size)
            }
            WALL_STREET_JOURNAL -> {
                val articles = arrayListOf<NewsResponse.Article>()
                repeat(25) {
                    articles.add(
                        NewsResponse.Article(
                            author = "Wall street author-$it",
                            description = "Wall street description-$it",
                            content = "Wall street content-$it",
                            publishedAt = "Wall street publishedAt-${System.currentTimeMillis()}",
                            source = NewsResponse.Article.Source(
                                id = source,
                                name = "The Wall Street Journal"
                            ), title = "Wall street Title-$it",
                            url = "https://www.wsj.com/articles/ukraine-hit-in-one-of-russias-biggest-missile-barrages-this-year-ae9d4b91?mod=hp_lead_pos2/$it",
                            urlToImage = "https://images.wsj.net/im-7392$it/social"
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


