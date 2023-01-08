package kg.news.newsapi.di

import kg.news.newsapi.ui.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module { viewModel { MainViewModel(get()) } }