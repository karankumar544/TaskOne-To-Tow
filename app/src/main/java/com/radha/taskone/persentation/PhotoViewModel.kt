package com.radha.taskone.persentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.radha.taskone.domain.remote.dto.Photos
import com.radha.taskone.domain.repository.PhotoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import retrofit2.Response

class PhotoViewModel(
    private val photoRepository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY_Photo = "Photo"
        const val DEFAULT_Photo = "cat"
    }

    init {
        if (!savedStateHandle.contains(KEY_Photo)) {
            savedStateHandle.set(KEY_Photo, DEFAULT_Photo)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val newPhoto = savedStateHandle.getLiveData<String>(KEY_Photo)
        .asFlow()
        .flatMapLatest { photoRepository.getPhotos(it, 2) }
        .cachedIn(viewModelScope)

    fun showSearchPhoto(subreddit: String) {
        if (!shouldShowSearchPhoto(subreddit)) return
        savedStateHandle.set(KEY_Photo, subreddit)
    }
    private fun shouldShowSearchPhoto(subreddit: String): Boolean {
        return savedStateHandle.get<String>(KEY_Photo) != subreddit
    }

}