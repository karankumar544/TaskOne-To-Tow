package com.radha.taskone.domain.repository

import com.radha.taskone.domain.remote.dto.Photos
import retrofit2.Response

interface PhotoRepository {

    suspend fun getPhotos(): Response<Photos>
}