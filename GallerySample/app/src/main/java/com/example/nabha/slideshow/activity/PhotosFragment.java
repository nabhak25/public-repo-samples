package com.example.nabha.slideshow.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nabha.slideshow.R;
import com.example.nabha.slideshow.adapter.PhotosGridViewAdapter;
import com.example.nabha.slideshow.utils.Constants;
import com.example.nabha.slideshow.model.PhotoInfo;
import com.example.nabha.slideshow.utils.iLibrarySelectionUIHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nabha on 25/07/17.
 */

public class PhotosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PhotosGridViewAdapter mPhotosAdapter;
    private ArrayList<PhotoInfo> photos = new ArrayList<>();
    private iLibrarySelectionUIHandler libraryUIHandler;


    public PhotosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns._ID};
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
//                int timeInSec = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION))) / 1000;
                PhotoInfo photoInfo = new PhotoInfo(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)), Constants.MediaType.IMAGE);
                photos.add(photoInfo);
            }
            cursor.close();
        }

        Collections.sort(photos, new Comparator<PhotoInfo>() {
            @Override
            public int compare(PhotoInfo o1, PhotoInfo o2) {
                return o1.getUri().compareTo(o2.getUri());
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.photos_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mPhotosAdapter = new PhotosGridViewAdapter(getActivity(), photos, libraryUIHandler);
        mRecyclerView.setAdapter(mPhotosAdapter);
        mRecyclerView.setItemAnimator(null);
//        mVideosAdapter.notifyItemChanged(1);

        return view;
    }

    public void setLibraryUIHandler(iLibrarySelectionUIHandler libraryUIHandler) {
        this.libraryUIHandler = libraryUIHandler;
    }
}
