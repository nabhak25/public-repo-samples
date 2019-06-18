package com.example.nabha.ui_elements_sample.simple_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nabha.ui_elements_sample.Fragment.FragmentActivity;
import com.example.nabha.ui_elements_sample.Gridview.GridViewActivity;
import com.example.nabha.ui_elements_sample.R;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.nabha.ui_elements_sample.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        if (message.matches("")) {
            Toast noMessageToast = Toast.makeText(getApplicationContext(), "Please enter a valid message", Toast.LENGTH_SHORT);
            noMessageToast.show();
        } else {
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

    public void nextActivity(View view) {
        Intent nextActivityIntent = new Intent(this, GridViewActivity.class);
        startActivity(nextActivityIntent);
    }

    public void goToFragmentActivity(View view) {
        Intent fragmentIntent = new Intent(this, FragmentActivity.class);
        startActivity(fragmentIntent);
    }
}
