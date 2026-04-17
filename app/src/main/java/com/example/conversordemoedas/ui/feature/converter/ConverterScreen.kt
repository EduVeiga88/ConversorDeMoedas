package com.example.conversordemoedas.ui.feature.converter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.conversordemoedas.R
import com.example.conversordemoedas.ui.components.CurrencyField
import com.example.conversordemoedas.ui.feature.converter.model.ConverterFormEvent
import com.example.conversordemoedas.ui.feature.converter.model.ConverterFormState
import com.example.conversordemoedas.ui.feature.converter.model.ConverterViewModel
import com.example.conversordemoedas.ui.theme.ConversorDeMoedasTheme

@Composable
fun ConverterScreen() {
    val viewModel = viewModel<ConverterViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val conversionState by viewModel.conversionState.collectAsStateWithLifecycle()


    ConverterContent(
        uiState = uiState,
        onFormEvent = viewModel::onFormEvent,
        conversionState = conversionState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterContent(
    uiState: ConverterFormState,
    conversionState: ConverterViewModel.ConversionState,
    onFormEvent: (ConverterFormEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .imePadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Conversor de moedas",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column {
                    CurrencyField(
                        currencies = uiState.fromCurrenciesList,
                        selectedCurrency = uiState.fromCurrencySelected,
                        currencyAmount = uiState.fromCurrencyAmount,
                        onSelectedCurrency = {
                            onFormEvent(ConverterFormEvent.onFromCurrencySeleted(it))
                        },
                        onCurrencyAmountChanged = {
                            onFormEvent(ConverterFormEvent.onFromCurrencyAmountChanged(it))
                        },
                    )
                    Spacer(modifier = Modifier.padding(8.dp))

                    CurrencyField(
                        currencies = uiState.toCurrenciesList,
                        selectedCurrency = uiState.toCurrencySelected,
                        currencyAmount = uiState.toCurrencyAmount,
                        onSelectedCurrency = {
                            onFormEvent(ConverterFormEvent.onToCurrencySeleted(it))
                        },
                        onCurrencyAmountChanged = {

                        }
                    )
                }
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = Color.LightGray
                    )

                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_downward),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ){
                when(conversionState){
                    ConverterViewModel.ConversionState.Idle ->{

                    }

                    ConverterViewModel.ConversionState.Loading ->{
                        CircularProgressIndicator()
                    }

                    ConverterViewModel.ConversionState.Sucess -> {
                        Text(
                            "Conversão realizada com sucesso",
                            color = Color.Green
                        )

                    }

                    is ConverterViewModel.ConversionState.Error -> {
                        Text(
                            text = conversionState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onFormEvent(ConverterFormEvent.sendConverterForm)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(
                    text = "Converter moeda"
                )
            }

        }

    }
}

@Preview
@Composable
private fun ConverterContentPreview() {
    ConversorDeMoedasTheme {
        ConverterContent(
            uiState = ConverterFormState(
                fromCurrenciesList = listOf("USD", "EUR", "BRL"),
                toCurrenciesList = listOf("BRL", "USD", "EUR"),
                fromCurrencySelected = "USD",
                toCurrencySelected = "BRL"
            ),
            onFormEvent = {},
            conversionState = ConverterViewModel.ConversionState.Idle
        )
    }

}