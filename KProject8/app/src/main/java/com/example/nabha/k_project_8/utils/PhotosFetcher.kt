package com.example.nabha.k_project_8.utils

import android.content.Context
import android.provider.MediaStore
import com.example.nabha.k_project_8.model.Photo

class PhotosFetcher(private val context: Context) {

    fun fetchPhotos(): ArrayList<Photo>? {
        val mediaFiles = ArrayList<Photo>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATE_ADDED,
                MediaStore.Images.ImageColumns.SIZE)
        val cursor = context.contentResolver.query(
                uri,
                projection,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_ADDED + " DESC"
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val id = cursor.getInt(columnIndex)
                val imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                val photo = Photo(imagePath)
                photo.id = id
                photo.dateAdded = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_ADDED))
                photo.size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE))
                mediaFiles.add(photo)
            }
            cursor.close()
        }
        return mediaFiles
    }

}