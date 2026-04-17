package com.example.conversordemoedas.domain.repository

import com.example.conversordemoedas.domain.model.CurrencyConversion

interface CurrencyRepository {

    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversion>
}