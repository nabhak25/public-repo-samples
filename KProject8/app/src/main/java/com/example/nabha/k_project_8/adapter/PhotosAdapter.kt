package com.example.nabha.k_project_8.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.nabha.k_project_8.R
import com.example.nabha.k_project_8.model.Photo
import com.example.nabha.k_project_8.utils.ThumbnailGenerator

class PhotosAdapter(val photosList: ArrayList<Photo>, val listener: IClickListener) :
        RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>(), ThumbnailGenerator.IImageReceiverCallback{
    private val thumbnailGenerator = ThumbnailGenerator(this)
    private var bitmap : Bitmap? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        thumbnailGenerator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, photosList[position].path, position.toString())
        if (bitmap != null) {
            holder.thumbnail?.setImageBitmap(bitmap)
        } else {
            holder.thumbnail?.setColorFilter(Color.BLACK)
        }
        holder.thumbnail?.setOnClickListener {
            listener.onItemClick(photosList[position].path, position)
        }
    }

    override fun onThumbnailReceived(thumbnail: Bitmap, path: String, position: Int) {
        bitmap = thumbnail
        notifyItemChanged(position)
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView? = itemView.findViewById(R.id.thumbnailImage)
    }

    interface IClickListener {
        fun onItemClick(path: String, position: Int)
    }
}