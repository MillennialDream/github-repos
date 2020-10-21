package com.sbnri.assignment.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.sbnri.assignment.model.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Repo>)

    @Query("SELECT * FROM repos")
    fun repos(): PagingSource<Int, Repo>

    @Query("DELETE FROM repos")
    suspend fun deleteAll()
}
