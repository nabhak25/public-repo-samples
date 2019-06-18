package com.example.nabha.slideshow.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Mukesh Kumar.
 */

public class BitmapUtils {
	private static final int COMPRESS_AMOUNT = 60;
	
	public static Bitmap compress(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_AMOUNT, out);
		
		byte[] byteArray = out.toByteArray();
		
		return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	}
	
	public static  void createDirAndSaveFile(Bitmap bitmap, String fileName) {
		File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "VZSlideShow");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File thumbFile = new File(dir.getPath() + File.separator + fileName);
		if (thumbFile.exists()) {
			thumbFile.delete();
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(thumbFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_AMOUNT, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
