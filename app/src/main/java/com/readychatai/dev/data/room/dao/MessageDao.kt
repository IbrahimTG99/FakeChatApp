package com.readychatai.dev.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.readychatai.dev.data.room.entities.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message): Long

    @Update
    suspend fun update(message: Message)

    @Delete
    suspend fun delete(message: Message)

    @Query("SELECT * FROM Message WHERE chatId = :chatId")
    suspend fun getMessagesForChat(chatId: Int): List<Message>


    @Query("SELECT * FROM Message WHERE chatId = :chatId ORDER BY timestamp ASC")
    fun getMessagesForChatFlow(chatId: Int): Flow<List<Message>>
}
