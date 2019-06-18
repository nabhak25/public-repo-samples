package com.example.nabha.ui_elements_sample_two.ViewPagerFragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabha.ui_elements_sample_two.R;

/**
 * Created by nabha on 21/07/17.
 */

public class ViewPagerFragment extends Fragment {

    private static final String IMAGE_POSITION = "imagePosition";
    private static final String IMAGE_ID = "imageId";


    public ViewPagerFragment() {

    }
    
    public static ViewPagerFragment viewPagerFragmentInstance(int position, int imageId) {
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMAGE_POSITION, position);
        bundle.putInt(IMAGE_ID, imageId);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);


        int imageId = getArguments().getInt(IMAGE_ID);
        int position = getArguments().getInt(IMAGE_POSITION);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.viewPagerImageView);
        imageView.setImageResource(imageId);

        TextView textView = (TextView) rootView.findViewById(R.id.cardNumber);
        textView.setText("Card Number: " + position);

        return rootView;
    }
}
