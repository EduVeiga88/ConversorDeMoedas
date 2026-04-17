package com.example.conversordemoedas.ui.feature.converter.model

data class ConverterFormState (
    val fromCurrenciesList: List<String> = emptyList(),
    val toCurrenciesList: List<String> = emptyList(),
    val fromCurrencySelected: String = "",
    val toCurrencySelected: String = "",
    val fromCurrencyAmount: String = "",
    val toCurrencyAmount: String = "",
)