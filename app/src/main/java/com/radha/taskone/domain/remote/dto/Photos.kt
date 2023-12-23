package com.radha.taskone.domain.remote.dto

import com.radha.taskone.domain.model.PhotosX

data class Photos(
    val photos: PhotosX,
    val stat: String
)