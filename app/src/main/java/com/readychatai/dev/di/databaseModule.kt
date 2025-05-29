package com.readychatai.dev.di

import com.readychatai.dev.data.room.database.ChatDatabase.Companion.getDatabase
import com.readychatai.dev.data.room.database.ChatDatabase.Companion.provideCategoryCrossRefDao
import com.readychatai.dev.data.room.database.ChatDatabase.Companion.provideCategoryDao
import com.readychatai.dev.data.room.database.ChatDatabase.Companion.provideChatDao
import com.readychatai.dev.data.room.database.ChatDatabase.Companion.provideMessageDao
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::getDatabase)
    singleOf(::provideChatDao)
    singleOf(::provideMessageDao)
    singleOf(::provideCategoryDao)
    singleOf(::provideCategoryCrossRefDao)
}

