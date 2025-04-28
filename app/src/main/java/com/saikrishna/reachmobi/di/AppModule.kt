package com.saikrishna.reachmobi.di

import com.saikrishna.reachmobi.BuildConfig
import com.saikrishna.reachmobi.data.remote.ApiService
import com.saikrishna.reachmobi.domain.repository.NewsItemRepository
import com.saikrishna.reachmobi.domain.usecase.GetNewsItemsUseCase
import com.saikrishna.reachmobi.utils.APIConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.NEWS_API_KEY}")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(APIConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideGetItemsUseCase(repository: NewsItemRepository) : GetNewsItemsUseCase {
        return GetNewsItemsUseCase(repository)
    }
}