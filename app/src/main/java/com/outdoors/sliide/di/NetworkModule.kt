package com.outdoors.sliide.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.outdoors.sliide.network.GoRestAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val app_key="Bearer " +"YOUR API KEY"

    @Singleton
    @Provides
    fun provideOKHttpClientLoggingInterceptor(): HttpLoggingInterceptor {

        return  HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    @Singleton
    @Provides
    fun provideOKHttpClientInterceptor(): Interceptor {

        return object:Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {

                val original = chain.request()

                val newRequest = original.newBuilder()
                    .addHeader("Authorization", app_key)
                    .build()

                return chain.proceed(newRequest)
            }
        }
    }

    @Singleton
    @Provides
    fun provideOKHttpClient(logInterceptor: HttpLoggingInterceptor,interceptor: Interceptor): OkHttpClient {
        return  OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor(interceptor)
            .build()
    }
    @Singleton
    @Provides
    fun provideJsonConfig():Json{
        return Json{
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, json:Json) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gorest.co.in/public-api/")
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkService(retrofit: Retrofit): GoRestAPI {
        return retrofit.create(GoRestAPI::class.java)
    }
}