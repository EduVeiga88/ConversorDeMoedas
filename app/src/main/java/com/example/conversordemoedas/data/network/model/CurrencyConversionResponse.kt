package com.example.conversordemoedas.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CurrencyConversionResponse(
    val result: String,
    @SerialName("error-type")
    val erroType: String? = null,
    @SerialName("base_code")
    val baseCode: String,
    @SerialName("target_code")
    val targetCode: String,
    @SerialName("conversion_rate")
    val convertionRate: Double,
    @SerialName("conversion_result")
    val convertionResult: Double
)