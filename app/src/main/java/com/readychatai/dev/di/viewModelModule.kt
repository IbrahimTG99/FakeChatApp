package com.readychatai.dev.di

import com.readychatai.dev.presentation.ChatAppSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ChatAppSharedViewModel)
}
