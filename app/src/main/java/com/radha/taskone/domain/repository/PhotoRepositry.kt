package com.radha.taskone.domain.repository

import androidx.paging.PagingData
import com.radha.taskone.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getPhotos(text:String,pageSize:Int): Flow<PagingData<Photo>>
}