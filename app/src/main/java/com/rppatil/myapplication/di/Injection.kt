package com.rppatil.myapplication.di

import androidx.lifecycle.ViewModelProvider
import com.rppatil.myapplication.model.DataSource
import com.rppatil.myapplication.model.Repository
import com.rppatil.myapplication.viewmodel.ViewModelFactory

object Injection {

    private val dataSource: DataSource = Repository()
    private val viewModelFactory = ViewModelFactory(dataSource)

    fun providerRepository(): DataSource {
        return dataSource
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }
}