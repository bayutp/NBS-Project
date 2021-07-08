package com.bayuspace.myapplication.di.modules.network

import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.api.MovieApiService
import com.bayuspace.myapplication.di.modules.BaseModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(retrofitModule, networkServiceModule)

    private val networkServiceModule = module {
        single { get<Retrofit>().create(MovieApiService::class.java) }
    }

    private val retrofitModule = module {
        single { provideOkhttp() }
        single { provideRetrofit(get()) }
    }

    private fun provideOkhttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}