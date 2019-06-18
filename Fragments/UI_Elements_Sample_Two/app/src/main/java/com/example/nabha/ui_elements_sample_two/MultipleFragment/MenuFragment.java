package com.example.nabha.ui_elements_sample_two.MultipleFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nabha.ui_elements_sample_two.R;

/**
 * Created by nabha on 20/07/17.
 */

public class MenuFragment extends Fragment {

    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    public MenuFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.menu_fragment, container, false);

        fragment = new FragmentOne();
        fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.container, fragment);
        fragmentTransaction.commit();

        Button moon = (Button) rootView.findViewById(R.id.moonButton);
        Button beach = (Button) rootView.findViewById(R.id.beachButton);
        Button rainbow = (Button) rootView.findViewById(R.id.rainbowButton);

        moon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentOne();
                fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });

        beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentTwo();
                fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });

        rainbow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentOther();
                fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}
