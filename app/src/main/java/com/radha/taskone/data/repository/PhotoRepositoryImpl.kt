package com.radha.taskone.data.repository

import com.radha.taskone.domain.remote.PhotoApi
import com.radha.taskone.domain.remote.dto.Photos
import com.radha.taskone.domain.repository.PhotoRepository
import retrofit2.Response

class PhotoRepositoryImpl(
    private val photoApi: PhotoApi
):PhotoRepository{
    override suspend fun getPhotos(): Response<Photos> {
        return photoApi.getPhotos()
    }
}