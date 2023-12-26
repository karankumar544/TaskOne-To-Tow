package com.radha.taskone.data.paging

import android.util.Log
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.radha.taskone.domain.model.Photo
import com.radha.taskone.persentation.basic.FirstFragmentDirections

class MYPagingDataAdapter(diffCallback: DiffUtil.ItemCallback<Photo>) :
    PagingDataAdapter<Photo, PhotoViewHolder>(
        diffCallback
    ) {
    private lateinit var mListener: OnClickListener

    interface OnClickListener {
        fun onClick(position: Int)
    }


    override fun onBindViewHolder(
        holder: PhotoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            item?.let { photo ->
                Log.e("ramji", "ramji")
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(photo)
                it.findNavController().navigate(action)
            }
        }
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewHolder {
        return PhotoViewHolder.create(parent)
    }

//    fun setOnClickListener(onClickListener: OnClickListener) {
//        mListener = onClickListener
//    }

}

object UserComparator : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}