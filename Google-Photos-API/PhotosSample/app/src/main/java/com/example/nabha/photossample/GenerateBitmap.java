package com.example.nabha.photossample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GenerateBitmap {

    private URL mUrl;
    private Callback mCallback;
    private GenerateBitmapTask mTask;
    private int mPosition;

     GenerateBitmap(int position, URL url, Callback callback) {
        mPosition = position;
        mUrl = url;
        mCallback = callback;
        mTask = new GenerateBitmapTask();
    }

    public interface Callback {
        void onBitmapReceived(int position, Bitmap bitmap);
    }

    public void execute() {
        mTask.execute();
    }

    class GenerateBitmapTask extends AsyncTask<Void, Void, HashMap<Integer, Bitmap>> {

        @Override
        protected HashMap<Integer, Bitmap> doInBackground(Void... voids) {
            HashMap<Integer, Bitmap> map = new HashMap<>();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(mUrl.openConnection().getInputStream());
                map.put(mPosition, bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return map;
        }

        @Override
        protected void onPostExecute(HashMap<Integer, Bitmap> map) {
            super.onPostExecute(map);
            for (Map.Entry<Integer, Bitmap> entry : map.entrySet()) {
                mCallback.onBitmapReceived(entry.getKey(), entry.getValue());
            }
        }
    }
}
