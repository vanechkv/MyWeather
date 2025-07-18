package com.example.myweather.di

import com.example.myweather.search.domain.impl.SearchInteractorImpl
import com.example.myweather.search.domain.interactor.SearchInteractor
import org.koin.dsl.module

val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}