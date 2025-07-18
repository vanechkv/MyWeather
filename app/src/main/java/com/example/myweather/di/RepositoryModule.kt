package com.example.myweather.di

import com.example.myweather.search.data.repository.SearchRepositoryImpl
import com.example.myweather.search.domain.repository.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }
}