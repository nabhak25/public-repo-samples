package com.example.nabha.photossample;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gdata.data.photos.PhotoEntry;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private static final String TAG = Utils.TAG;
    private List<PhotoEntry> mPhotoEntries;
    private Picasso mPicasso;

    PhotosAdapter(List<PhotoEntry> photoEntries, Picasso picasso) {
        mPhotoEntries = photoEntries;
        mPicasso = picasso;
    }


    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        mPicasso.load(mPhotoEntries.get(position).getMediaContents().get(0).getUrl())
                .placeholder(android.R.drawable.gallery_thumb)
                .error(android.R.drawable.gallery_thumb)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mPhotoEntries.size();
    }

    /*private Bitmap getBitmap(String urlString) {
        Bitmap bmp = null;
        try {
            URL photoUrl = new URL(urlString);
            bmp = BitmapFactory.decodeStream(photoUrl.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }*/

    private void getBitmap(final PhotoViewHolder holder, String urlString) {
        try {
            URL photoUrl = new URL(urlString);
            new GenerateBitmap(holder.getAdapterPosition(), photoUrl, new GenerateBitmap.Callback() {
                @Override
                public void onBitmapReceived(int position, Bitmap bitmap) {
                    if (holder.getAdapterPosition() == position) {
                        holder.imageView.setImageBitmap(bitmap);
                    }
                }
            }).execute();
        } catch (Exception ignored) {}
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_image);
        }
    }
}
