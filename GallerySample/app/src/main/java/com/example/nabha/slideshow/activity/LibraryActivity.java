package com.example.nabha.slideshow.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.nabha.slideshow.R;
import com.example.nabha.slideshow.utils.iLibrarySelectionUIHandler;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity implements iLibrarySelectionUIHandler {

    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Button mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mDoneButton = (Button) findViewById(R.id.btnDone);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        PhotosFragment photosFragment = new PhotosFragment();
        photosFragment.setLibraryUIHandler(this);
        VideosFragment videosFragment = new VideosFragment();
        videosFragment.setLibraryUIHandler(this);

        viewPagerAdapter.addFragment(photosFragment, "PHOTOS");
        viewPagerAdapter.addFragment(videosFragment, "VIDEOS");
        viewPagerAdapter.addFragment(photosFragment, "INSTAGRAM");
        viewPagerAdapter.addFragment(videosFragment, "FACEBOOK");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onItemSelected() {
        mDoneButton.setEnabled(true);
    }

    @Override
    public void onItemDeselected() {
        mDoneButton.setEnabled(false);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void showPlayer(View view) {
        Intent playerIntent = new Intent(this, PlayerActivity.class);
        startActivity(playerIntent);
    }

}
