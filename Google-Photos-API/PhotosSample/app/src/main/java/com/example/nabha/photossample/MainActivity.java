package com.example.nabha.photossample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Entry;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.GphotoFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = Utils.TAG;
    private final int PICK_ACCOUNT_REQUEST = 1;
    private final int REQUEST_AUTHENTICATE = 2;
    private static final String API_PREFIX = "https://picasaweb.google.com/data/feed/api/user/";
    private static final String KIND_ALBUM = "?kind=album";
    private static final String ALBUM_PREFIX = "/albumid/";

    PicasawebService picasaService;
    Button selectAccount;
    AccountManager am;
    Account[] list;
    String selectedAccountName;
    Account selectedAccount;

    ImageView picture;
    PhotosAdapter mPhotosAdpater;
    RelativeLayout mButtonLayout;
    RecyclerView mPhotosRecycler;
    List<PhotoEntry> mPhotoEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am = (AccountManager) getSystemService(ACCOUNT_SERVICE);

        selectAccount = findViewById(R.id.selectAccount);
        selectAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = am.getAccounts();
                Log.d(TAG, "Got {} accounts " + list.length);
                for (Account a:list) {
                    Log.i(TAG, "Name: " + a.name + "Type: " + a.type);
                }

                Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                        false, null, null, null, null);
                startActivityForResult(intent, PICK_ACCOUNT_REQUEST);
            }
        });
        picture = (ImageView) findViewById(R.id.picture);
        mButtonLayout = findViewById(R.id.buttonHolder);
        mPhotosRecycler = findViewById(R.id.recyclerView);
        mPhotosRecycler.setLayoutManager(new GridLayoutManager(this, 3));
    }

    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {

        switch(requestCode) {
            case PICK_ACCOUNT_REQUEST:
                if (resultCode == RESULT_OK) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    Log.i(TAG, "Selected Account 1{} " + accountName);
                    selectedAccount =  null;
                    for (Account a:list) {
                        if (a.name.equals(accountName)) {
                            selectedAccount = a;
                            break;
                        }
                    }
                    selectedAccountName = accountName;
                    selectedAccount = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
                    Log.d(TAG, "Selected Account 2{} " + selectedAccountName);

                    am.getAuthToken(selectedAccount,                     // Account retrieved using getAccountsByType()
                            "lh2",            // Auth scope - lh2 - https://picasaweb.google.com/data/
                            null,                        // Authenticator-specific options
                            this,                           // Your activity
                            new OnTokenAcquired(),          // Callback called when a token is successfully acquired
                            null);    // Callback called if an error occ
                }
                break;
            case REQUEST_AUTHENTICATE:
                if (resultCode == RESULT_OK) {
                    am.getAuthToken(
                            selectedAccount,                     // Account retrieved using getAccountsByType()
                            "lh2",            // Auth scope - lh2 - https://picasaweb.google.com/data/
                            null,                        // Authenticator-specific options
                            this,                           // Your activity
                            new OnTokenAcquired(),          // Callback called when a token is successfully acquired
                            null);    // Callback called if an error occ
                }
                break;
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_dashboard, menu);
        return true;
    }*/
    public <T extends GphotoFeed> T getFeed(String feedHref,
                                            Class<T> feedClass) throws IOException, ServiceException {
        Log.i(TAG, "Get Feed URL: " + feedHref);
        return picasaService.getFeed(new URL(feedHref), feedClass);
    }

    public List<AlbumEntry> getAlbums(String userId) throws IOException,
            ServiceException {

        String albumUrlString = API_PREFIX + userId + KIND_ALBUM;
//        String albumUrlString = API_PREFIX + "default" + KIND_ALBUM;
        UserFeed userFeed = getFeed(albumUrlString, UserFeed.class);

        List<GphotoEntry> entries = userFeed.getEntries();
        List<AlbumEntry> albums = new ArrayList<>();
        for (GphotoEntry entry : entries) {
            AlbumEntry ae = new AlbumEntry(entry);
            Log.d(TAG, "Album title {} " + ae.getTitle().getPlainText());
//            Log.d(TAG, "Album date {} " + ae.getDate().toString());
            albums.add(ae);
        }
        return albums;
//        return userFeed.getAlbumEntries();
    }

    /*public List<AlbumEntry> getAlbums(String userId) throws IOException, ServiceException {

//        String albumUrlString = API_PREFIX + userId + KIND_ALBUM;
        String albumUrlString = API_PREFIX + userId;
        String fields = "entry(title,gphoto:id,gphoto:timestamp)";
        Query albumQuery = new Query(new URL(albumUrlString));
        albumQuery.setFields(fields);
        AlbumFeed partialFeed = picasaService.query(albumQuery, AlbumFeed.class);
        List<AlbumEntry> albums = new ArrayList<>();
        for (GphotoEntry partialEntry : partialFeed.getEntries()) {
            if (partialEntry instanceof AlbumEntry) {
//                AlbumEntry partialAlbumEntry = (AlbumEntry) partialEntry;
                AlbumEntry ae = new AlbumEntry(partialEntry);
                albums.add(ae);
                System.out.println(String.format("%s: [title - %s, location - %s]", ae.getGphotoId(), ae.getTitle().getPlainText(), ae.getLocation()));
            }
        }
        return albums;
    }*/

    public List<PhotoEntry> getPhotos(String userId, AlbumEntry album) throws IOException,
            ServiceException{
        AlbumFeed feed = album.getFeed();
        List<PhotoEntry> photos = new ArrayList<>();
        for (GphotoEntry entry : feed.getEntries()) {
            PhotoEntry pe = new PhotoEntry(entry);
            Log.d(TAG, "MEDIA DATA: " + pe.getMediaContents().get(0).getType());
            Log.d(TAG, "MEDIA DATA: " + pe.getMediaContents().get(0).getUrl());
            Log.i(TAG, "******  ******  ******  ****** ******** ***** *****");
            photos.add(pe);
        }
        Log.i(TAG, "Album name: " + album.getName() + " Photos: " + photos.size());
        return photos;
    }

    public List<PhotoEntry> getPhotosWithAlbumId(String userId, String albumId) throws IOException,
    ServiceException {
        String albumUrlString = API_PREFIX + userId + ALBUM_PREFIX + albumId;
        AlbumFeed feed = getFeed(albumUrlString, AlbumFeed.class);

        List<GphotoEntry> entries = feed.getEntries();
        List<PhotoEntry> photos = new ArrayList<>();
        for (GphotoEntry entry : entries) {
            PhotoEntry pe = new PhotoEntry(entry);
//            Log.w(TAG, "getUpdated: " + pe.getExifTags().getTime());
            photos.add(pe);
        }
        Log.d(TAG, "Album title: " + feed.getTitle().getPlainText() + " Photos: " + photos.size());
        return photos;
//        return feed.getPhotoEntries();
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try {
                Bundle b = result.getResult();

                if (b.containsKey(AccountManager.KEY_INTENT)) {
                    Intent intent = b.getParcelable(AccountManager.KEY_INTENT);
                    int flags = intent.getFlags();
                    intent.setFlags(flags);
                    flags &= ~Intent.FLAG_ACTIVITY_NEW_TASK;
                    startActivityForResult(intent, REQUEST_AUTHENTICATE);
                    return;
                }

                if (b.containsKey(AccountManager.KEY_AUTHTOKEN)) {
                    final String authToken = b.getString(AccountManager.KEY_AUTHTOKEN);
                    Log.i(TAG, "Auth token {} " + authToken);
                    picasaService = new PicasawebService("PhotosSample");
                    picasaService.setUserToken(authToken);

                    new AsyncTask<Void, Void, Bitmap>() {
                        @Override
                        protected Bitmap doInBackground(Void... voids) {
                            List<AlbumEntry> albums = null;
                            try {

                                albums = getAlbums(selectedAccountName);
                                Log.i(TAG, "Got {} albums " + albums.size());
                                for (AlbumEntry myAlbum : albums) {
                                    Log.i(TAG, "Album {} " + myAlbum.getTitle().getPlainText());
//                                    Log.i(TAG, "Album {} " + myAlbum.getMediaContents().get(0).getUrl());
                                    List<PhotoEntry> photos = getPhotosWithAlbumId(selectedAccountName, myAlbum.getGphotoId());
                                    mPhotoEntries.addAll(photos);
                                }
                                /*AlbumEntry album = albums.get(0);
//                                List<PhotoEntry> photos = getPhotos(selectedAccountName, album);
                                List<PhotoEntry> photos = getPhotosWithAlbumId(selectedAccountName, album.getGphotoId());
                                mPhotoEntries = photos;*/
                                PhotoEntry photo = mPhotoEntries.get(0);
                                URL photoUrl = new URL(photo.getMediaContents().get(0).getUrl());
                                Bitmap bmp = BitmapFactory.decodeStream(photoUrl.openConnection().getInputStream());
                                return bmp;
                            } catch (ServiceForbiddenException e) {
                                Log.e(TAG, "Token expired, invalidating");
                                am.invalidateAuthToken("com.google", authToken);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ServiceException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                        protected void onPostExecute(Bitmap result) {
                            picture.setImageBitmap(result);
                            mButtonLayout.setVisibility(View.INVISIBLE);
                            mPhotosRecycler.setVisibility(View.VISIBLE);
                            Picasso picasso = Picasso.with(MainActivity.this);
                            mPhotosAdpater = new PhotosAdapter(mPhotoEntries, picasso);
                            mPhotosRecycler.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));
                            mPhotosRecycler.setAdapter(mPhotosAdpater);
                        }
                    }.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");
        try {
            myService.setUserCredentials("bigbet0004@gmail.com", "Admin123*");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }*/
