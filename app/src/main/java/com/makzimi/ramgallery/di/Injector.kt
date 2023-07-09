package com.makzimi.ramgallery.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.makzimi.ramgallery.common.CustomViewModelFactory
import com.makzimi.ramgallery.data.AppDatabase
import com.makzimi.ramgallery.gallery.data.CharactersDao
import com.makzimi.ramgallery.gallery.data.CharactersLocalDataSource
import com.makzimi.ramgallery.gallery.data.CharactersRemoteDataSource
import com.makzimi.ramgallery.gallery.data.CharactersRepository
import com.makzimi.ramgallery.gallery.data.RAMService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

object Injector {

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun provideRAMService(context: Context): RAMService {
        val retrofit =
            Retrofit.Builder()
                .client(provideOkHttpClient(context))
                .baseUrl(RAMService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(RAMService::class.java)
    }

    private fun provideGalleryRemoteDataSource(context: Context): CharactersRemoteDataSource {
        return CharactersRemoteDataSource(provideRAMService(context))
    }

    private fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    private fun provideGalleryDao(context: Context): CharactersDao {
        return provideAppDatabase(context).galleryDao()
    }

    private fun provideGalleryLocalDataSource(context: Context): CharactersLocalDataSource {
        return CharactersLocalDataSource(
            provideGalleryDao(context),
            Executors.newSingleThreadExecutor()
        )
    }

    private fun provideGalleryRepository(context: Context): CharactersRepository {
        return CharactersRepository(
            provideGalleryRemoteDataSource(context),
            provideGalleryLocalDataSource(context)
        )
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return CustomViewModelFactory(provideGalleryRepository(context))
    }

}