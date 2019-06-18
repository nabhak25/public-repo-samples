package com.example.nabha.customseekbar.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;

/**
 * Created by nabha on 05/10/17.
 */

public class DotsSeekBar extends AppCompatSeekBar {
    private static final String TAG = DotsSeekBar.class.getSimpleName();
    private void init() {
        paint.setColor(Color.BLUE);
        setThumb(null);
        setBackground(null);
    }

    public DotsSeekBar(Context context) {
        super(context);
        init();
    }

    public DotsSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, 5, paint);
        super.onDraw(canvas);
        float cy = getTopPaddingOffset() + (getHeight() / 2.0f);
        float radius = 5;
        float xOffset = getThumbOffset() + (getPaddingStart() / 2.0f);
        canvas.drawCircle(getWidth() / 2.0f, cy, radius, paint);
        canvas.drawCircle(xOffset, cy, radius, paint);
        canvas.drawCircle(getWidth() - xOffset, cy, radius, paint);
    }
}

