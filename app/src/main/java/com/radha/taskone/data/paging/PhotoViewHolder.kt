package com.radha.taskone.data.paging

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.radha.taskone.R
import com.radha.taskone.domain.model.Photo
import com.radha.taskone.persentation.basic.FirstFragmentDirections

class PhotoViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    private var newPhotos: Photo? = null
    var photo1: ImageView
    var title: TextView
    var progress: ProgressBar

    init {
        item.setOnClickListener {

        }
        photo1 = item.findViewById(R.id.image)
        title = item.findViewById(R.id.title)
        progress = item.findViewById(R.id.imageProgress)
    }

    fun bind(item: Photo?) {
        item?.let { photo ->
            title.text = photo.title
            Glide.with(itemView.context).load(photo.url_s)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                }).into(photo1)
        }
    }

    companion object {
        fun create(parent: ViewGroup): PhotoViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.single_row, parent, false)
            return PhotoViewHolder(view)
        }
    }

    fun updateScore(item: Photo?) {
        newPhotos = item
    }
}