package com.sbnri.assignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sbnri.assignment.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE type = :keyType")
    suspend fun remoteKeyByType(keyType: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE type = :keyType")
    suspend fun deleteByType(keyType: String)
}