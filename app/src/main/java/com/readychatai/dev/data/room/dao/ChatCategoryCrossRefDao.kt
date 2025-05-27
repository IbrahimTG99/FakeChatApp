package com.readychatai.dev.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.readychatai.dev.data.room.entities.ChatCategoryCrossRef

@Dao
interface ChatCategoryCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ref: ChatCategoryCrossRef)

    @Query("SELECT * FROM ChatCategoryCrossRef WHERE chatId = :chatId AND categoryId = :categoryId")
    suspend fun get(chatId: Int, categoryId: Int): ChatCategoryCrossRef?

    @Query("DELETE FROM ChatCategoryCrossRef WHERE chatId = :chatId AND categoryId = :categoryId")
    suspend fun delete(chatId: Int, categoryId: Int)
}
