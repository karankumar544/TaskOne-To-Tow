package com.radha.taskone.data.paging

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.radha.taskone.domain.model.Photo
import com.radha.taskone.domain.remote.PhotoApi
import java.io.IOException

class PhotoPagingResource(
    private val photoApi: PhotoApi, private val text: String
) : PagingSource<Int, Photo>() {

    private var totalPhotoCount = 0
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        totalPhotoCount = 0
        Log.e("radha", "$page")
        return try {
            val newsResponse = photoApi.getPhotos(page = page, text = text)
            if (newsResponse.isSuccessful) {
                Log.e("radha", "hare krishna")
            }
            newsResponse.body()?.let {
                totalPhotoCount += it.photos.photo.size
            }
            val article = newsResponse.body()?.photos?.photo!!
            Log.e("radha", "$article")
            Log.e("radha", "$totalPhotoCount")
            LoadResult.Page(
                data = article,
                nextKey = if (totalPhotoCount == (newsResponse.body()?.photos?.perpage)) null else if (totalPhotoCount == 8) page + 1 else page,
                prevKey = if (page == 1) null else page - 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("radha", "$e")
            LoadResult.Error(
                throwable = e
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}