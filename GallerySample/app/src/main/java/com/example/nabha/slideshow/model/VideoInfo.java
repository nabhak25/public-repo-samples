package com.example.nabha.slideshow.model;

import com.example.nabha.slideshow.utils.Constants;

/**
 * Created by nabha on 25/07/17.
 */

public class VideoInfo {

    String uri;
    String id;
    Constants.MediaType mediaType;
    String duration;

   public VideoInfo(String uri, String id, Constants.MediaType mediaType, String duration) {
        this.uri = uri;
        this.id = id;
        this.mediaType = mediaType;
        this.duration = duration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public void setMediaType(Constants.MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Constants.MediaType getMediaType() {
        return mediaType;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isVideo() {
        return mediaType == Constants.MediaType.VIDEO;
    }


}
