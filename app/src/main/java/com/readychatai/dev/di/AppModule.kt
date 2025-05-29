package com.readychatai.dev.di

import com.readychatai.dev.data.repository.ChatRepositoryImpl
import com.readychatai.dev.domain.repository.ChatRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::ChatRepositoryImpl){
        bind<ChatRepository>()
    }
}
