package app.android.newsapp.data.network.repository

interface NewsRepository {
    suspend fun getNewsList()
}