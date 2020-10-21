package com.sbnri.assignment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sbnri.assignment.model.RemoteKey
import com.sbnri.assignment.model.Repo

@Database(
    entities = [RemoteKey::class, Repo::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDb : RoomDatabase() {
    companion object {
        fun create(context: Context, useInMemory: Boolean): GithubDb {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, GithubDb::class.java)
            } else {
                Room.databaseBuilder(context, GithubDb::class.java, "github.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun repoDao(): RepoDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}