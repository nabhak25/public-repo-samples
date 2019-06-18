package com.example.nabha.custompopup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nabha.custompopup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = SecondFragment.class.getSimpleName();

    private TextView mTextView;
    private String textValue = "";
    private Button mCancelButton;

    private ICancelButtonListener mListener;

    public interface ICancelButtonListener {
        void onCancel();
    }


    public SecondFragment() {
        // Required empty public constructor
    }

    public void setListener(ICancelButtonListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_second, container, false);
        mTextView = root.findViewById(R.id.textView);
        mCancelButton = root.findViewById(R.id.cancelBtn);
        mCancelButton.setOnClickListener(this);
        validateIntent();
        setTextValue();
        return root;
    }

    private void setTextValue() {
        mTextView.setText(textValue);
    }

    private void validateIntent() {
        Bundle args = getArguments();
        if (args != null) {
          textValue = args.getString("Text");
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Cancel tapped");
        if (mListener != null) mListener.onCancel();
    }
}
