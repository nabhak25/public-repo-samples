package com.example.nabha.slideshow.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;

/**
 * Created by Mukesh Kumar.
 */

public class FetchImageBitmap extends AsyncTask<String, Void, Bitmap> {
	private iVideoThumbnailUpdate iVideoThumbnailUpdate;
	int pos;
	
	public void setiVideoThumbnailUpdate(iVideoThumbnailUpdate iVideoThumbnailUpdate, int pos) {
		this.iVideoThumbnailUpdate = iVideoThumbnailUpdate;
		this.pos = pos;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "VZSlideShow");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File imageFile = new File(dir.getPath() + File.separator + params[1]);
		Bitmap bitmap;
		if (imageFile.exists()) {
			bitmap = BitmapFactory.decodeFile(imageFile.getPath());
		} else {
//			Bitmap oldBitmap;
//			oldBitmap = BitmapFactory.decodeFile(params[0]);
//			oldBitmap = ThumbnailUtils.extractThumbnail(params[0], 300, );
//			oldBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(params[0]), 300, 300);
//			bitmap = scaleDown(oldBitmap, 400, false);
			bitmap = getScaledBitmap(params[0], 300);
//			bitmap = scaleDown(oldBitmap, 300, false);
//			oldBitmap.recycle();

//			oldBitmap = bitmap;
//			bitmap = BitmapUtils.compress(oldBitmap);
//			oldBitmap.recycle();

			BitmapUtils.createDirAndSaveFile(bitmap, params[1]);
		}
		return bitmap;
	}

	private static Bitmap getScaledBitmap(String path, int newSize) {
		File image = new File(path);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
//		options.inInputShareable = true;
//		options.inPurgeable = true;

		BitmapFactory.decodeFile(image.getPath(), options);
		if ((options.outWidth == -1) || (options.outHeight == -1))
			return null;

		int originalSize = (options.outHeight > options.outWidth) ? options.outHeight
				: options.outWidth;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = originalSize / newSize;

		return BitmapFactory.decodeFile(image.getPath(), opts);
	}
	
//	private Bitmap scaleDown(Bitmap realImage, float maxImageSize) {
//		float ratio = Math.min(
//				(float) maxImageSize / realImage.getWidth(),
//				(float) maxImageSize / realImage.getHeight());
//		int width = Math.round((float) ratio * realImage.getWidth());
//		int height = Math.round((float) ratio * realImage.getHeight());
//	}

	private Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
		float ratio = Math.min(
				(float) maxImageSize / realImage.getWidth(),
				(float) maxImageSize / realImage.getHeight());
		int width = Math.round((float) ratio * realImage.getWidth());
		int height = Math.round((float) ratio * realImage.getHeight());
		
		return Bitmap.createScaledBitmap(realImage, width, height, filter);
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
//		TODO: Handle such scenarios!
		if (bitmap != null) {
			iVideoThumbnailUpdate.updateThumbnail(bitmap, pos);
		}
	}
}