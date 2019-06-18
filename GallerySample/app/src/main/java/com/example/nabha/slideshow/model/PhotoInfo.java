package com.example.nabha.slideshow.model;

import com.example.nabha.slideshow.utils.Constants;

/**
 * Created by nabha on 25/07/17.
 */

public class PhotoInfo {

    String uri;
    String id;
    Constants.MediaType mediaType;

    public PhotoInfo(String uri, String id, Constants.MediaType mediaType) {
        this.uri = uri;
        this.id = id;
        this.mediaType = mediaType;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
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

    public boolean isPhoto() {
        return mediaType == Constants.MediaType.IMAGE;
    }
}
