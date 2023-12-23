package com.radha.taskone.di

import com.radha.taskone.data.repository.PhotoRepositoryImpl
import com.radha.taskone.domain.remote.PhotoApi
import com.radha.taskone.domain.repository.PhotoRepository
import com.radha.taskone.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideApi(): PhotoApi {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(PhotoApi::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideRepository(photoApi: PhotoApi): PhotoRepository {
//        return PhotoRepositoryImpl(photoApi)
//    }
//
//}