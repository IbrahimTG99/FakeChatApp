package com.readychatai.dev.data.room.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.readychatai.dev.data.room.converters.Converters
import com.readychatai.dev.data.room.dao.CategoryDao
import com.readychatai.dev.data.room.dao.ChatCategoryCrossRefDao
import com.readychatai.dev.data.room.dao.ChatDao
import com.readychatai.dev.data.room.dao.MessageDao
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.ChatCategoryCrossRef
import com.readychatai.dev.data.room.entities.Message

@Database(
    entities = [Chat::class, Message::class, Category::class, ChatCategoryCrossRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun categoryDao(): CategoryDao
    abstract fun chatCategoryCrossRefDao(): ChatCategoryCrossRefDao

    companion object {

        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getDatabase(application: Application): ChatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    ChatDatabase::class.java,
                    "chat-db"
                )
                    .addMigrations()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun provideChatDao(chatDataBase: ChatDatabase): ChatDao = chatDataBase.chatDao()
        fun provideMessageDao(chatDataBase: ChatDatabase): MessageDao = chatDataBase.messageDao()
        fun provideCategoryDao(chatDataBase: ChatDatabase): CategoryDao = chatDataBase.categoryDao()
        fun provideCategoryCrossRefDao(chatDataBase: ChatDatabase): ChatCategoryCrossRefDao = chatDataBase.chatCategoryCrossRefDao()
    }
}