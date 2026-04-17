package com.example.conversordemoedas.ui.feature.converter.model

sealed interface ConverterFormEvent {
    data class onFromCurrencySeleted(val currency: String) : ConverterFormEvent
    data class onToCurrencySeleted(val currency: String) : ConverterFormEvent
    data class onFromCurrencyAmountChanged(val amount: String) : ConverterFormEvent
    data object sendConverterForm : ConverterFormEvent
}