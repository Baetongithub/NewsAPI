package kg.news.newsapi.di

import kg.news.newsapi.ui.home.MainRepository
import org.koin.dsl.module

val repoModule = module { single { MainRepository(get()) } }