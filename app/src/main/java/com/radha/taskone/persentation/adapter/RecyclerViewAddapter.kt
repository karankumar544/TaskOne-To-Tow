package com.radha.taskone.persentation.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.radha.taskone.R
import com.radha.taskone.domain.model.Photo

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var photo: List<Photo> = emptyList()
    private lateinit var mListener: OnClickListener

    interface OnClickListener {
        fun onClick(position: Int)
    }

    class ViewHolder(item: View, private val onClickListener: OnClickListener) :
        RecyclerView.ViewHolder(item) {
        var photo: ImageView
        var title: TextView
        var progress: ProgressBar

        init {
            item.setOnClickListener {
                onClickListener.onClick(adapterPosition)
            }
            photo = item.findViewById(R.id.image)
            title = item.findViewById(R.id.title)
            progress = item.findViewById(R.id.imageProgress)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun getItemCount(): Int = photo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title.text = photo[position].title
        Glide.with(holder.itemView.context).load(photo[position].url_s)
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
                    holder.progress.visibility = View.GONE
                    return false
                }

            })
            .into(holder.photo)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        mListener = onClickListener
    }

    fun setPhoto(photo: List<Photo>) {
        this.photo = photo
        notifyDataSetChanged()
    }
}
