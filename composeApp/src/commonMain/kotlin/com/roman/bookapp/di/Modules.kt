package com.roman.bookapp.di

import com.roman.bookapp.book.data.network.KtorRemoteBookDataSource
import com.roman.bookapp.book.data.network.RemoteBookDataSource
import com.roman.bookapp.book.data.repository.BookRepository
import com.roman.bookapp.book.domain.IBookRepository
import com.roman.bookapp.book.presentation.SelectedBookViewModel
import com.roman.bookapp.book.presentation.book_detail.BookDetailViewModel
import com.roman.bookapp.book.presentation.book_list.BookListViewModel
import com.roman.bookapp.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(engine = get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::BookRepository).bind<IBookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}