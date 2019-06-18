package com.example.nabha.slideshow.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Mukesh Kumar.
 */


public class FetchThumbnailBitmap extends AsyncTask<String, Void, Bitmap> {
	
	private iVideoThumbnailUpdate iVideoThumbnailUpdate;
	private Bitmap bitmap;
	private int position;
	
	public void setiVideoThumbnailUpdate(iVideoThumbnailUpdate iVideoThumbnailUpdate, int position) {
		this.iVideoThumbnailUpdate = iVideoThumbnailUpdate;
		this.position = position;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		File file = new File(Environment.getExternalStorageDirectory() + File.separator + "VZSlideShow");
		if (!file.exists()) {
			file.mkdirs();
		}
		File thumbFile = new File(file.getPath() + File.separator + params[1]);
		if (thumbFile.exists())
			bitmap = BitmapFactory.decodeFile(thumbFile.getPath());
		else {
			bitmap = ThumbnailUtils.createVideoThumbnail(params[0], MediaStore.Video.Thumbnails.MINI_KIND);
			if (bitmap != null) {
				bitmap = BitmapUtils.compress(bitmap);
				BitmapUtils.createDirAndSaveFile(bitmap, params[1]);
			}
		}
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
//		TODO: Handle such scenarios! Corrupted?
		if (bitmap != null) {
			iVideoThumbnailUpdate.updateThumbnail(bitmap, position);
		}
	}
}