package app.android.newsapp.di

import android.content.Context
import app.android.newsapp.BuildConfig
import app.android.newsapp.data.network.NewsApi
import app.android.newsapp.data.network.connection.NetworkConnection
import app.android.newsapp.data.network.repository.NewsRepository
import app.android.newsapp.data.network.repository.NewsRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofitInstance(
        gson: Gson,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideNewsRepository(): NewsRepository = NewsRepositoryImpl()

    @Singleton
    @Provides
    fun provideNetworkConnection(@ApplicationContext context: Context) = NetworkConnection(context)
}

