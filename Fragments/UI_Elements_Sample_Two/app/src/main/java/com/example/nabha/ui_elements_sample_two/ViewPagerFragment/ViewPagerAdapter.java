package com.example.nabha.ui_elements_sample_two.ViewPagerFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.BaseAdapter;

import com.example.nabha.ui_elements_sample_two.R;

/**
 * Created by nabha on 21/07/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int[] images = { R.drawable.moon, R.drawable.beach, R.drawable.rainbow,
            R.drawable.waterfalls, R.drawable.desert, R.drawable.oceans,
            R.drawable.garden };


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerFragment.viewPagerFragmentInstance(position, images[position]);
    }


    @Override
    public int getCount() {
        return images.length;
    }
}
