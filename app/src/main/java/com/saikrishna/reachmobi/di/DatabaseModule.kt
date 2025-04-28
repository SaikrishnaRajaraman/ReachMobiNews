package com.saikrishna.reachmobi.di

import android.content.Context
import androidx.room.Room
import com.saikrishna.reachmobi.data.dao.NewsItemDao
import com.saikrishna.reachmobi.data.db.AppDatabase
import com.saikrishna.reachmobi.data.local.NewsItemLocalDataSourceImpl
import com.saikrishna.reachmobi.data.remote.NewsItemRemoteDataSourceImpl
import com.saikrishna.reachmobi.data.repository.NewsItemLocalDataSource
import com.saikrishna.reachmobi.data.repository.NewsItemRemoteDataSource
import com.saikrishna.reachmobi.domain.repository.NewsItemRepository
import com.saikrishna.reachmobi.domain.repository.NewsItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRemoteDataSource(
        impl: NewsItemRemoteDataSourceImpl
    ): NewsItemRemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(impl: NewsItemLocalDataSourceImpl): NewsItemLocalDataSource

    @Binds
    abstract fun bindNewsRepo(impl: NewsItemRepositoryImpl): NewsItemRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "reachmobi.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNewsItemDao(db: AppDatabase): NewsItemDao =
        db.newsItemDao()
}


