package com.example.nabha.custompopup.activity;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nabha.custompopup.R;
import com.example.nabha.custompopup.fragments.CustomDialogFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mShowDialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowDialogButton = findViewById(R.id.showDialog);
        mShowDialogButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Log.i(TAG, "Dialog!!! ");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new CustomDialogFragment();
        dialogFragment.show(ft, "dialog");
    }
}
