package com.example.nabha.ui_elements_sample_two.MultipleFragment;
import android.app.Fragment;
import android.view.LayoutInflater;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nabha.ui_elements_sample_two.R;

/**
 * Created by nabha on 20/07/17.
 */

public class FragmentTwo extends Fragment {

    public FragmentTwo() {

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, null);

        return view;
    }
}
