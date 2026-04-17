package com.example.conversordemoedas.ui.feature.converter.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conversordemoedas.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConverterFormState())
    val uiState = _uiState.asStateFlow()

    private val _conversionState = MutableStateFlow<ConversionState>(ConversionState.Idle)
    val conversionState = _conversionState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                fromCurrenciesList = listOf("USD","EUR","BRL"),
                toCurrenciesList = listOf("BRL","USD","EUR"),
                fromCurrencySelected = "USD",
                toCurrencySelected = "BRL"
            )
        }
    }

    fun onFormEvent(event: ConverterFormEvent) {
        when (event) {
            is ConverterFormEvent.onFromCurrencySeleted -> {
                _uiState.update {
                    it.copy(fromCurrencySelected = event.currency)
                }
            }

            is ConverterFormEvent.onToCurrencySeleted -> {
                _uiState.update {
                    it.copy(toCurrencySelected = event.currency)
                }
            }

            is ConverterFormEvent.onFromCurrencyAmountChanged -> {
                _uiState.update {
                    it.copy(fromCurrencyAmount = event.amount)
                }
            }

            ConverterFormEvent.sendConverterForm -> {
                convertCurrency()
            }
        }
    }

    private fun convertCurrency(){
        viewModelScope.launch {
            val fromCurrency = _uiState.value.fromCurrencySelected
            val toCurrency = _uiState.value.toCurrencySelected
            val amount = _uiState.value.fromCurrencyAmount.toDoubleOrNull()

            if (fromCurrency.isNotBlank() && toCurrency.isNotBlank() && amount != null ){

                currencyRepository.convertCurrency(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency,
                    amount = amount
                ).fold(
                    onSuccess = { currencyConversion ->
                        _uiState.update {
                            it.copy(toCurrencyAmount = currencyConversion.convertionResult )
                        }

                        _conversionState.update {
                            ConversionState.Sucess
                        }

                    },
                    onFailure = { error ->
                        _conversionState.update {
                            ConversionState.Error(message = error.message ?: "Erro desconhecido")
                        }
                    }
                )

            } else {
                _conversionState.update {
                    ConversionState.Error( message = "Valores inválidos")
                }
            }
        }
    }

    sealed interface ConversionState {
        object Idle : ConversionState
        object Loading : ConversionState
        object Sucess : ConversionState
        data class Error(val message: String) : ConversionState
    }
}