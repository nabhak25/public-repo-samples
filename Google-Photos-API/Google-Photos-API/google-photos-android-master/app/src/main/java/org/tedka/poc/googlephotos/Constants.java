package org.tedka.poc.googlephotos;

public class Constants {
    /** Codes for onActivityResult identifications */
    public static final int REQUEST_ACCOUNT_PICKER = 1000;
    public static final int REQUEST_AUTHORIZATION = 1001;
    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    public static final int REQ_SIGN_IN_REQUIRED = 1003;

    /** Parameter name to store preferred google account in shared pref */
    public static final String PREF_ACCOUNT_NAME = "accountName";

    /** 'Scopes' required to request Google permissions for our specific case */
    public final static String GPHOTOS_SCOPE
            = "oauth2:https://www.googleapis.com/auth/drive.photos.readonly";
//            = "oauth2:https://picasaweb.google.com/data/";

    /** Google API KEY */
    public static String URL_EXTN_API_KEY = "&key=AIzaSyBUsOiB15O-RhI_GUIA-mOa1DVPXHOIYW0";
//    public static String URL_EXTN_API_KEY = "key=AIzaSyBUsOiB15O-RhI_GUIA-mOa1DVPXHOIYW0";

    /** URLs and extensions required for the API calls */
    public static String URL_FILES = "https://www.googleapis.com/drive/v2/files?appDataContents=photos";
//    public static String URL_FILES = "https://picasaweb.google.com/data/feed/api/user/default/albumid/albumid";

    /** Specify the fields required, so as to reduce response data size */
    public static String URL_FILES_FIELDS = "&fields=items(mimeType,selfLink,thumbnailLink,title,hasThumbnail),nextLink";
//    public static String URL_FILES_FIELDS = "";

    /** Used to construct the URL for a full-size image */
    public static String URL_FILE_EXTN = "?alt=media";

    /** Header information required for added authorization to API calls */
    public static String HEADER_NAME_AUTH = "Authorization";
    public static String HEADER_AUTH_VAL_PRFX = "Bearer ";

    /** JSON field names from the Google API result */
    public static String JSON_FIELD_ITEMS = "items";
//    public static String JSON_FIELD_ITEMS = "files";
    public static String JSON_FIELD_NEXT_LINK = "nextLink";
    public static String JSON_FIELD_SELF_LINK = "selfLink";
    public static String JSON_FIELD_TITLE = "title";
    public static String JSON_FIELD_THUMBNAIL = "thumbnailLink";
    public static String JSON_FIELD_MIME_TYPE = "mimeType";
    public static String JSON_FIELD_MIME_IMG = "image";
    public static String JSON_FIELD_MIME_VIDEO = "video";
    public static String JSON_FIELD_HAS_THUMBNAIL = "hasThumbnail";
}
