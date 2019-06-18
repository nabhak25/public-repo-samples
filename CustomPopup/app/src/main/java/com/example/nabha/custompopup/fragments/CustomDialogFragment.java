package com.example.nabha.custompopup.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.nabha.custompopup.R;

public class CustomDialogFragment extends DialogFragment implements SecondFragment.ICancelButtonListener {

    private SecondFragment mSecondFragment;
    private FrameLayout mContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_dialog, container, false);
        mContainer = v.findViewById(R.id.container);
        initializeFragment();
        loadFragment();
        return v;
    }

    private void loadFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(mContainer.getId(), mSecondFragment);
        transaction.commit();
    }

    private void initializeFragment() {
        mSecondFragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString("Text", "I am Groot!");
        mSecondFragment.setArguments(args);
        mSecondFragment.setListener(this);
    }

    @Override
    public void onCancel() {
        dismiss();
    }
}
