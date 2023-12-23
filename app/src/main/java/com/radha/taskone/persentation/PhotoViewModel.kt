package com.radha.taskone.persentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radha.taskone.domain.remote.dto.Photos
import com.radha.taskone.domain.repository.PhotoRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class PhotoViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photos: MutableLiveData<Response<Photos>> by lazy {
        MutableLiveData<Response<Photos>>()
    }

    val photos: LiveData<Response<Photos>> = _photos

    init {
        viewModelScope.launch {
            try {
                val photos = photoRepository.getPhotos()
                _photos.value = photos
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("error", e.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("error", e.toString())
            }
        }

    }

}