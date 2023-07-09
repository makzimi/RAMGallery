package com.makzimi.ramgallery.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.makzimi.ramgallery.common.CustomViewModelFactory
import com.makzimi.ramgallery.data.AppDatabase
import com.makzimi.ramgallery.gallery.data.GalleryDao
import com.makzimi.ramgallery.gallery.data.GalleryLocalDataSource
import com.makzimi.ramgallery.gallery.data.GalleryRemoteDataSource
import com.makzimi.ramgallery.gallery.data.GalleryRepository
import com.makzimi.ramgallery.network.RAMService
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

    private fun provideGalleryRemoteDataSource(context: Context): GalleryRemoteDataSource {
        return GalleryRemoteDataSource(provideRAMService(context))
    }

    private fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    private fun provideGalleryDao(context: Context): GalleryDao {
        return provideAppDatabase(context).galleryDao()
    }

    private fun provideGalleryLocalDataSource(context: Context): GalleryLocalDataSource {
        return GalleryLocalDataSource(
            provideGalleryDao(context),
            Executors.newSingleThreadExecutor()
        )
    }

    private fun provideGalleryRepository(context: Context): GalleryRepository {
        return GalleryRepository(
            provideGalleryRemoteDataSource(context),
            provideGalleryLocalDataSource(context)
        )
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return CustomViewModelFactory(provideGalleryRepository(context))
    }

}