package com.example.nabha.k_project_8.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.AsyncTask
import java.io.IOException

class ThumbnailGenerator(private val callback: IImageReceiverCallback) : AsyncTask<String, Void, Bitmap>() {

    private var mediaPath = ""
    private var position = 0

    private fun getImageBitmap(filepath: String) : Bitmap? {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filepath, options)
        options.inSampleSize = calculateInSampleSize(options, AppUtils.DEFAULT_THUMBNAIL_SIZE,
                AppUtils.DEFAULT_THUMBNAIL_SIZE)
        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeFile(filepath, options)

        var exif : ExifInterface? = null
        var orientationString : String? = null
        try {
            exif = ExifInterface(filepath)
            orientationString = exif.getAttribute(ExifInterface.TAG_ORIENTATION)
        } catch (ignored: IOException) {}

        val orientation = if (orientationString != null) {
            orientationString.toInt()
        } else {
            ExifInterface.ORIENTATION_NORMAL
        }

        var rotationAngle = 0
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            rotationAngle = 90
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            rotationAngle = 180
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            rotationAngle = 270

        val w = bitmap.width
        val h = bitmap.height
        val matrix = Matrix()
        matrix.postRotate(rotationAngle.toFloat())

        val temp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true)
        val bm565 = temp.copy(Bitmap.Config.RGB_565, false)
        temp.recycle()
        if (!bitmap.isRecycled) bitmap.recycle()
        return bm565
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int) : Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var sampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / sampleSize >= reqHeight && halfWidth / sampleSize >= reqWidth) {
                sampleSize *= 2
            }
        }
        return sampleSize
    }

    override fun doInBackground(vararg params: String): Bitmap? {
        mediaPath = params[0]
        position = params[1].toInt()
        return getImageBitmap(mediaPath)
    }

     override fun onPostExecute(result: Bitmap) {
        callback.onThumbnailReceived(result, mediaPath, position)
    }

    interface IImageReceiverCallback {
        fun onThumbnailReceived(thumbnail: Bitmap, path: String, position: Int)
    }
}