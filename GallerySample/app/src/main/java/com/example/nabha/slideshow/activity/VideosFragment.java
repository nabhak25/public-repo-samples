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
import com.example.nabha.slideshow.adapter.VideosGridViewAdapter;
import com.example.nabha.slideshow.utils.Constants;
import com.example.nabha.slideshow.model.VideoInfo;
import com.example.nabha.slideshow.utils.iLibrarySelectionUIHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nabha on 25/07/17.
 */

public class VideosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private VideosGridViewAdapter mVideosAdapter;
    private ArrayList<VideoInfo> videoInfos = new ArrayList<>();
    private iLibrarySelectionUIHandler libraryUIHandler;

    public VideosFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.VideoColumns._ID, MediaStore.Video.VideoColumns.DURATION};
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int timeInSec = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION))) / 1000;
                VideoInfo videoInfo = new VideoInfo(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID)), Constants.MediaType.VIDEO,String.valueOf(timeInSec + "sec"));
                videoInfos.add(videoInfo);
            }
            cursor.close();
        }

        Collections.sort(videoInfos, new Comparator<VideoInfo>() {
            @Override
            public int compare(VideoInfo o1, VideoInfo o2) {
                return o1.getUri().compareTo(o2.getUri());
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.videos_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mVideosAdapter = new VideosGridViewAdapter(getActivity(), videoInfos, libraryUIHandler);
        mRecyclerView.setAdapter(mVideosAdapter);
        mRecyclerView.setItemAnimator(null);
//        mVideosAdapter.notifyItemChanged(1);

        return view;
    }

    public void setLibraryUIHandler(iLibrarySelectionUIHandler libraryUIHandler) {
        this.libraryUIHandler = libraryUIHandler;
    }
}
