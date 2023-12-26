package com.radha.taskone.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.radha.taskone.data.paging.PhotoPagingResource
import com.radha.taskone.domain.model.Photo
import com.radha.taskone.domain.remote.PhotoApi
import com.radha.taskone.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow

class PhotoRepositoryImpl(
    private val photoApi: PhotoApi,
) : PhotoRepository {
    override suspend fun getPhotos(text: String,pageSize:Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                PhotoPagingResource(
                    photoApi = photoApi, text = text,
                )
            }
        ).flow
    }

}