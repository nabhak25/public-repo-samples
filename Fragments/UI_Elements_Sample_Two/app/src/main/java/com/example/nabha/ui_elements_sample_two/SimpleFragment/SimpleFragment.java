package com.example.nabha.ui_elements_sample_two.SimpleFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nabha.ui_elements_sample_two.R;

/**
 * Created by nabha on 20/07/17.
 */

public class SimpleFragment extends Fragment {

    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.simple_fragment, container, false);
        Button cakeButton = (Button) view.findViewById(R.id.cakeButton);
        cakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast simpleToast = Toast.makeText(getActivity(), "Beautiful waterfalls!!", Toast.LENGTH_SHORT);
                simpleToast.show();
            }
        });

        return view;
    }
}
