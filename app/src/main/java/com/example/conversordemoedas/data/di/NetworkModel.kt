package com.example.conversordemoedas.data.di

import com.example.conversordemoedas.data.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

private const val API_KEY = "016ed657978fef2bf942d681"


@Module
@InstallIn(SingletonComponent::class)
object NetworkModel {

    @Provides
    @Singleton
    fun providesHttpClient() : HttpClient {
        return HttpClient {
            expectSuccess = true

            defaultRequest {
                url("https://v6.exchangerate-api.com/v6/$API_KEY/")
            }

            install(ContentNegotiation){
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(Logging){
                logger = Logger.SIMPLE
            }
        }
    }

    @Provides
    @Singleton
    fun provideKtorClient(
        client: HttpClient
    ): KtorClient {
        return  KtorClient(client)
    }
}