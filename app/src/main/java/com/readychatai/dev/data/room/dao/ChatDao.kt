package com.readychatai.dev.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.relations.ChatWithCategories
import com.readychatai.dev.data.room.relations.ChatWithMessagesAndCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chat: Chat): Long

    @Update
    suspend fun update(chat: Chat)

    @Delete
    suspend fun delete(chat: Chat)

    @Transaction
    @Query("SELECT * FROM Chat WHERE id = :chatId")
    suspend fun getChatWithMessages(chatId: Int): ChatWithMessagesAndCategories

    @Transaction
    @Query("SELECT * FROM Chat WHERE id = :chatId")
    suspend fun getChatWithCategories(chatId: Int): ChatWithCategories

    @Query("SELECT * FROM Chat")
    suspend fun getAllChats(): List<Chat>

    @Transaction
    @Query("SELECT * FROM Chat")
    fun getAllChatsWithMessagesAndCategoriesFlow(): Flow<List<ChatWithMessagesAndCategories>>

}
