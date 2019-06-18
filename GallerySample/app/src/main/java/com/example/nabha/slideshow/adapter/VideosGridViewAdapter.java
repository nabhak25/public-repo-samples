package com.example.nabha.slideshow.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nabha.slideshow.R;
import com.example.nabha.slideshow.utils.Constants;
import com.example.nabha.slideshow.utils.FetchThumbnailBitmap;
import com.example.nabha.slideshow.model.VideoInfo;
import com.example.nabha.slideshow.utils.iLibrarySelectionUIHandler;
import com.example.nabha.slideshow.utils.iVideoThumbnailUpdate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nabha on 25/07/17.
 */

public class VideosGridViewAdapter extends RecyclerView.Adapter<VideosGridViewAdapter.MyViewHolder>{

    ArrayList<VideoInfo> videos = new ArrayList<>();
    SparseBooleanArray sparseBooleanArray;
    private LruCache<String, Bitmap> bitmapLruCache;
    private iLibrarySelectionUIHandler libraryUIHandler;

    public VideosGridViewAdapter(Context context, ArrayList<VideoInfo> videos, iLibrarySelectionUIHandler libraryUIHandler) {
        this.libraryUIHandler = libraryUIHandler;
        this.videos = videos;
        sparseBooleanArray = new SparseBooleanArray();
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int maxSize = maxMemory / 8;
        bitmapLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public ArrayList<VideoInfo> getSelectedItem() {
        ArrayList<VideoInfo> mSelectedFilePath = new ArrayList<>();
        for (int i = 0; i < videos.size(); i++) {
            if (sparseBooleanArray.get(i, false)) {
                mSelectedFilePath.add(videos.get(i));
            }
        }
        return mSelectedFilePath;
    }

    @Override
    public VideosGridViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout, null, false);
        return new MyViewHolder(view);
    }

    private HashMap<Integer, Integer> positionCount = new HashMap<>();

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.imageView.setImageResource(0);
        String path = videos.get(position).getUri();
        int count = 0;
        if (positionCount.get(position) != null) {
            count = positionCount.get(position);
        }
        count++;
        positionCount.put(position, count);
        if (videos.get(position).getMediaType() == Constants.MediaType.VIDEO) {
            Bitmap bitmap = bitmapLruCache.get(videos.get(position).getUri());
            if (bitmap == null) {
                FetchThumbnailBitmap fetchThumbnailBitmap = new FetchThumbnailBitmap();
                fetchThumbnailBitmap.setiVideoThumbnailUpdate(new iVideoThumbnailUpdate() {
                    @Override
                    public void updateThumbnail(Bitmap bitmap, int mSelectedPosition) {
                        if (holder.getAdapterPosition() == mSelectedPosition && bitmap != null) {
                            holder.imageView.setImageBitmap(bitmap);
                            bitmapLruCache.put(videos.get(mSelectedPosition).getUri(), bitmap);
                        }
                    }
                }, position);
                fetchThumbnailBitmap.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, path, videos.get(position).getId());
            } else
                holder.imageView.setImageBitmap(bitmap);
            holder.textView.setText(String.valueOf(videos.get(position).getDuration()));
            holder.textView.setVisibility(View.VISIBLE);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    if (getSelectedItem().size() == 1) {
                        libraryUIHandler.onItemDeselected();
                    }
                    holder.checkBox.setChecked(false);
                    holder.checkBox.setVisibility(View.GONE);
                    sparseBooleanArray.put(position, false);
                } else {
                    libraryUIHandler.onItemSelected();
                    holder.checkBox.setChecked(true);
                    holder.checkBox.setVisibility(View.VISIBLE);
                    sparseBooleanArray.put(position, true);
                }
            }
        });
        if (sparseBooleanArray.get(position, false)) {
            holder.checkBox.setChecked(true);
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setChecked(false);
            holder.checkBox.setVisibility(View.GONE);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setVisibility(View.VISIBLE);
                } else {
                    holder.checkBox.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder/* implements iVideoThumbnailUpdate */ {

        ImageView imageView;
        TextView textView;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnailImage);
            textView = (TextView) itemView.findViewById(R.id.durationLabel);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

//        @Override
//        public void updateVideoThumbnail(Bitmap bitmap, int position) {
//            if (getAdapterPosition() == position) {
//                imageView.setImageBitmap(bitmap);
//            }
//        }
    }
}
