package com.example.conversordemoedas.data.di

import com.example.conversordemoedas.data.repository.CurrencyRepositoryImpl
import com.example.conversordemoedas.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModel {

    @Binds
    @Singleton
    fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ) : CurrencyRepository
}