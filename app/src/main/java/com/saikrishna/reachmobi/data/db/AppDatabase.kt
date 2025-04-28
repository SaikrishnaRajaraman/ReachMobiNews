package com.saikrishna.reachmobi.data.db

import androidx.room.Database
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import com.saikrishna.reachmobi.data.dao.NewsItemDao
import com.saikrishna.reachmobi.data.model.NewsItem


@Database(
    entities = [NewsItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsItemDao(): NewsItemDao
}