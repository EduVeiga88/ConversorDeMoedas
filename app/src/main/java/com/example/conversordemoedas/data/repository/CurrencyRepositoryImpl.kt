package com.example.conversordemoedas.data.repository

import com.example.conversordemoedas.data.network.KtorClient
import com.example.conversordemoedas.domain.model.CurrencyConversion
import com.example.conversordemoedas.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val ktorClient: KtorClient,
) : CurrencyRepository {

    override suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversion> {
       return runCatching {
            val response = ktorClient.convertCurrency(
                fromCurrency = fromCurrency,
                toCurrency = toCurrency,
                amount = amount
            )

            CurrencyConversion(
                baseCode = response.baseCode,
                targetCode = response.targetCode,
                convertionRate = response.convertionRate.toString(),
                convertionResult = response.convertionResult.toString()
            )
        }
    }
}