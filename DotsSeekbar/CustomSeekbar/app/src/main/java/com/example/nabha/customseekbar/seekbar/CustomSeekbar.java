package com.example.nabha.customseekbar.seekbar;

/**
 * Created by nabha on 13/11/17.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;

import static com.example.nabha.customseekbar.seekbar.CustomSeekbar.TextPosition.BELOW_SECTION_MARK;
import static com.example.nabha.customseekbar.seekbar.CustomSeekbar.TextPosition.BOTTOM_SIDES;
import static com.example.nabha.customseekbar.seekbar.CustomSeekbar.TextPosition.SIDES;

/**
 * A beautiful and powerful Android custom seek bar, which has a bubble view with progress
 * appearing upon when seeking. Highly customizable, mostly demands has been considered.
 * <p/>
 * Created by woxingxiao on 2016-10-27.
 */
public class CustomSeekbar extends View {

    static final int NONE = -1;

    @IntDef({NONE, SIDES, BOTTOM_SIDES, BELOW_SECTION_MARK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextPosition {
        int SIDES = 0, BOTTOM_SIDES = 1, BELOW_SECTION_MARK = 2;
    }

    private float mMin; // min
    private float mMax; // max
    private float mProgress; // real time value
    private boolean isFloatType; // support for float type output
    private int mTrackSize; // height of right-track(on the right of thumb)
    private int mSecondTrackSize; // height of left-track(on the left of thumb)
    private int mThumbRadius; // radius of thumb
    private int mThumbRadiusOnDragging; // radius of thumb when be dragging
    private int mTrackColor; // color of right-track
    private int mSecondTrackColor; // color of left-track
    private int mThumbColor; // color of thumb
    private int mSectionCount; // shares of whole progress(max - min)
    private boolean isShowSectionMark; // show demarcation points or not
    private boolean isAutoAdjustSectionMark; // auto scroll to the nearest section_mark or not
    private boolean isShowSectionText; // show section-text or not
    private int mSectionTextSize; // text size of section-text
    private int mSectionTextColor; // text color of section-text
    @CustomSeekbar.TextPosition
    private int mSectionTextPosition = NONE; // text position of section-text relative to track
    private int mSectionTextInterval; // the interval of two section-text
    private boolean isShowThumbText; // show real time progress-text under thumb or not
    private int mThumbTextSize; // text size of progress-text
    private int mThumbTextColor; // text color of progress-text
    private boolean isShowProgressInFloat; // show bubble-progress in float or not
    private boolean isTouchToSeek; // touch anywhere on track to quickly seek
    private boolean isSeekBySection; // seek by section, the progress may not be linear
    private long mAnimDuration; // duration of animation
    private boolean isAlwaysShowBubble; // bubble shows all time

    private int mBubbleColor;// color of bubble
    private int mBubbleTextSize; // text size of bubble-progress
    private int mBubbleTextColor; // text color of bubble-progress

    private float mDelta; // max - min
    private float mSectionValue; // (mDelta / mSectionCount)
    private float mThumbCenterX; // X coordinate of thumb's center
    private float mTrackLength; // pixel length of whole track
    private float mSectionOffset; // pixel length of one section
    private boolean isThumbOnDragging; // is thumb on dragging or not
    private int mTextSpace; // space between text and track
    private boolean triggerBubbleShowing;
    private boolean triggerSeekBySection;

    private CustomSeekbar.OnProgressChangedListener mProgressListener; // progress changing listener
    private float mLeft; // space between left of track and left of the view
    private float mRight; // space between right of track and left of the view
    private Paint mPaint;
    private Rect mRectText;

    private WindowManager mWindowManager;
    private CustomSeekbar.BubbleView mBubbleView;
    private int mBubbleRadius;
    private float mBubbleCenterRawSolidX;
    private float mBubbleCenterRawSolidY;
    private float mBubbleCenterRawX;
    private WindowManager.LayoutParams mLayoutParams;
    private int[] mPoint = new int[2];
    private boolean isTouchToSeekAnimEnd = true;
    private float mPreSecValue; // previous SectionValue

    public CustomSeekbar(Context context) {
        this(context, null);
    }

    public CustomSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar, defStyleAttr, 0);
        mMin = a.getFloat(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_min, 0.0f);
        mMax = a.getFloat(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_max, 100.0f);
        mProgress = a.getFloat(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_progress, mMin);
        isFloatType = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_is_float_type, false);
        mTrackSize = a.getDimensionPixelSize(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_track_size, Utils.dp2px(2));
        mSecondTrackSize = a.getDimensionPixelSize(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_second_track_size,
                mTrackSize + Utils.dp2px(2));
        mThumbRadius = a.getDimensionPixelSize(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_thumb_radius,
                mSecondTrackSize + Utils.dp2px(2));
        mThumbRadiusOnDragging = a.getDimensionPixelSize(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_thumb_radius,
                mSecondTrackSize * 2);
        mSectionCount = a.getInteger(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_section_count, 10);
        mTrackColor = a.getColor(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_track_color,
                ContextCompat.getColor(context, com.xw.repo.bubbleseekbar.R.color.colorPrimary));
        mSecondTrackColor = a.getColor(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_second_track_color,
                ContextCompat.getColor(context, com.xw.repo.bubbleseekbar.R.color.colorAccent));
        mThumbColor = a.getColor(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_thumb_color, mSecondTrackColor);
        isShowSectionText = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_show_section_text, false);
        mSectionTextSize = a.getDimensionPixelSize(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_section_text_size, Utils.sp2px(14));
        mSectionTextColor = a.getColor(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_section_text_color, mTrackColor);
        isSeekBySection = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_seek_by_section, false);
        int pos = a.getInteger(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_section_text_position, NONE);
        if (pos == 0) {
            mSectionTextPosition = SIDES;
        } else if (pos == 1) {
            mSectionTextPosition = BOTTOM_SIDES;
        } else if (pos == 2) {
            mSectionTextPosition = BELOW_SECTION_MARK;
        } else {
            mSectionTextPosition = NONE;
        }
        mSectionTextInterval = a.getInteger(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_section_text_interval, 1);
        isShowThumbText = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_show_thumb_text, false);
        mThumbTextSize = a.getDimensionPixelSize(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_thumb_text_size, Utils.sp2px(14));
        mThumbTextColor = a.getColor(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_thumb_text_color, mSecondTrackColor);
        mBubbleColor = a.getColor(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_bubble_color, mSecondTrackColor);
        mBubbleTextSize = a.getDimensionPixelSize(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_bubble_text_size, Utils.sp2px(14));
        mBubbleTextColor = a.getColor(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_bubble_text_color, Color.WHITE);
        isShowSectionMark = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_show_section_mark, false);
        isAutoAdjustSectionMark = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_auto_adjust_section_mark, false);
        isShowProgressInFloat = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_show_progress_in_float, false);
        int duration = a.getInteger(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_anim_duration, -1);
        mAnimDuration = duration < 0 ? 200 : duration;
        isTouchToSeek = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_touch_to_seek, false);
        isAlwaysShowBubble = a.getBoolean(com.xw.repo.bubbleseekbar.R.styleable.BubbleSeekBar_bsb_always_show_bubble, false);
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mRectText = new Rect();

        mTextSpace = Utils.dp2px(2);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        // init BubbleView
        mBubbleView = new CustomSeekbar.BubbleView(context);
        mBubbleView.setProgressText(isShowProgressInFloat ?
                String.valueOf(getProgressFloat()) : String.valueOf(getProgress()));
        mBubbleView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("Custom: ", "onTouch:  DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("Custom: ", "onTouch:   MOVE");
                        break;
                }
                return false;
            }
        });

        initConfigByPriority();
        calculateRadiusOfBubble();
    }

    private void initConfigByPriority() {
        if (mMin == mMax) {
            mMin = 0.0f;
            mMax = 100.0f;
        }
        if (mMin > mMax) {
            float tmp = mMax;
            mMax = mMin;
            mMin = tmp;
        }
        if (mProgress < mMin) {
            mProgress = mMin;
        }
        if (mProgress > mMax) {
            mProgress = mMax;
        }
        if (mSecondTrackSize < mTrackSize) {
            mSecondTrackSize = mTrackSize + Utils.dp2px(2);
        }
        if (mThumbRadius <= mSecondTrackSize) {
            mThumbRadius = mSecondTrackSize + Utils.dp2px(2);
        }
        if (mThumbRadiusOnDragging <= mSecondTrackSize) {
            mThumbRadiusOnDragging = mSecondTrackSize * 2;
        }
        if (mSectionCount <= 0) {
            mSectionCount = 10;
        }
        mDelta = mMax - mMin;
        mSectionValue = mDelta / mSectionCount;

        if (mSectionValue < 1) {
            isFloatType = true;
        }
        if (isFloatType) {
            isShowProgressInFloat = true;
        }
        if (mSectionTextPosition != NONE) {
            isShowSectionText = true;
        }
        if (isShowSectionText) {
            if (mSectionTextPosition == NONE) {
                mSectionTextPosition = SIDES;
            }
            if (mSectionTextPosition == BELOW_SECTION_MARK) {
                isShowSectionMark = true;
            }
        }
        if (mSectionTextInterval < 1) {
            mSectionTextInterval = 1;
        }
        if (isAutoAdjustSectionMark && !isShowSectionMark) {
            isAutoAdjustSectionMark = false;
        }
        if (isSeekBySection) {
            mPreSecValue = mMin;
            if (mProgress != mMin) {
                mPreSecValue = mSectionValue;
            }
            isShowSectionMark = true;
            isAutoAdjustSectionMark = true;
            isTouchToSeek = false;
        }
        if (isAlwaysShowBubble) {
            setProgress(mProgress);
        }

        mThumbTextSize = isFloatType || isSeekBySection || (isShowSectionText && mSectionTextPosition ==
                BELOW_SECTION_MARK) ? mSectionTextSize : mThumbTextSize;
    }

    /**
     * Calculate radius of bubble according to the Min and the Max
     */
    private void calculateRadiusOfBubble() {
        mPaint.setTextSize(mBubbleTextSize);

        // 计算滑到两端气泡里文字需要显示的宽度，比较取最大值为气泡的半径
        String text;
        if (isShowProgressInFloat) {
            text = float2String(mMin);
        } else {
            text = getMinText();
        }
        mPaint.getTextBounds(text, 0, text.length(), mRectText);
        int w1 = (mRectText.width() + mTextSpace * 2) >> 1;

        if (isShowProgressInFloat) {
            text = float2String(mMax);
        } else {
            text = getMaxText();
        }
        mPaint.getTextBounds(text, 0, text.length(), mRectText);
        int w2 = (mRectText.width() + mTextSpace * 2) >> 1;

        mBubbleRadius = Utils.dp2px(14); // default 14dp
        int max = Math.max(mBubbleRadius, Math.max(w1, w2));
        mBubbleRadius = max + mTextSpace;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = mThumbRadiusOnDragging * 2; // 默认高度为拖动时thumb圆的直径
        if (isShowThumbText) {
            mPaint.setTextSize(mThumbTextSize);
            mPaint.getTextBounds("j", 0, 1, mRectText); // “j”是字母和阿拉伯数字中最高的
            height += mRectText.height() + mTextSpace; // 如果显示实时进度，则原来基础上加上进度文字高度和间隔
        }
        if (isShowSectionText && mSectionTextPosition >= BOTTOM_SIDES) { // 如果Section值在track之下显示，比较取较大值
            mPaint.setTextSize(mSectionTextSize);
            mPaint.getTextBounds("j", 0, 1, mRectText);
            height = Math.max(height, mThumbRadiusOnDragging * 2 + mRectText.height() + mTextSpace);
        }
        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec), height);

        mLeft = getPaddingLeft() + mThumbRadiusOnDragging;
        mRight = getMeasuredWidth() - getPaddingRight() - mThumbRadiusOnDragging;

        if (isShowSectionText) {
            mPaint.setTextSize(mSectionTextSize);

            if (mSectionTextPosition == SIDES) {
                String text = getMinText();
                mPaint.getTextBounds(text, 0, text.length(), mRectText);
                mLeft += (mRectText.width() + mTextSpace);

                text = getMaxText();
                mPaint.getTextBounds(text, 0, text.length(), mRectText);
                mRight -= (mRectText.width() + mTextSpace);
            } else if (mSectionTextPosition >= BOTTOM_SIDES) {
                String text = getMinText();
                mPaint.getTextBounds(text, 0, text.length(), mRectText);
                float max = Math.max(mThumbRadiusOnDragging, mRectText.width() / 2f);
                mLeft = getPaddingLeft() + max + mTextSpace;

                text = getMaxText();
                mPaint.getTextBounds(text, 0, text.length(), mRectText);
                max = Math.max(mThumbRadiusOnDragging, mRectText.width() / 2f);
                mRight = getMeasuredWidth() - getPaddingRight() - max - mTextSpace;
            }
        } else if (isShowThumbText && mSectionTextPosition == NONE) {
            mPaint.setTextSize(mThumbTextSize);

            String text = getMinText();
            mPaint.getTextBounds(text, 0, text.length(), mRectText);
            float max = Math.max(mThumbRadiusOnDragging, mRectText.width() / 2f);
            mLeft = getPaddingLeft() + max + mTextSpace;

            text = getMaxText();
            mPaint.getTextBounds(text, 0, text.length(), mRectText);
            max = Math.max(mThumbRadiusOnDragging, mRectText.width() / 2f);
            mRight = getMeasuredWidth() - getPaddingRight() - max - mTextSpace;
        }

        mTrackLength = mRight - mLeft;
        mSectionOffset = mTrackLength * 1f / mSectionCount;

        mBubbleView.measure(widthMeasureSpec, heightMeasureSpec);

        locatePositionOnScreen();
    }

    /**
     * In fact there two parts of the BubbleSeeBar, they are the BubbleView and the SeekBar.
     * <p>
     * The BubbleView is added to Window by the WindowManager, so the only connection between
     * BubbleView and SeekBar is their origin raw coordinates on the screen.
     * <p>
     * It's easy to compute the coordinates(mBubbleCenterRawSolidX, mBubbleCenterRawSolidY) of point
     * when the Progress equals the Min. Then compute the pixel length increment when the Progress is
     * changing, the result is mBubbleCenterRawX. At last the WindowManager calls updateViewLayout()
     * to update the LayoutParameter.x of the BubbleView.
     * <p>
     * 气泡BubbleView实际是通过WindowManager动态添加的一个视图，因此与SeekBar唯一的位置联系就是它们在屏幕上的
     * 绝对坐标。
     * 先计算进度mProgress为mMin时BubbleView的中心坐标（mBubbleCenterRawSolidX，mBubbleCenterRawSolidY），
     * 然后根据进度来增量计算横坐标mBubbleCenterRawX，再动态设置LayoutParameter.x，就实现了气泡跟随滑动移动。
     */
    private void locatePositionOnScreen() {
        getLocationOnScreen(mPoint);

        mBubbleCenterRawSolidX = mPoint[0] + mLeft - mBubbleView.getMeasuredWidth() / 2f;
        mBubbleCenterRawX = mBubbleCenterRawSolidX + mTrackLength * (mProgress - mMin) / mDelta;
        mBubbleCenterRawSolidY = mPoint[1] - mBubbleView.getMeasuredHeight();
        mBubbleCenterRawSolidY -= Utils.dp2px(24);
        if (Utils.isMIUI()) {
            mBubbleCenterRawSolidY += Utils.dp2px(4);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float xLeft = getPaddingLeft();
        float xRight = getMeasuredWidth() - getPaddingRight();
        float yTop = getPaddingTop() + mThumbRadiusOnDragging;

        // draw sectionText SIDES or BOTTOM_SIDES
        if (isShowSectionText) {
            mPaint.setTextSize(mSectionTextSize);
            mPaint.setColor(mSectionTextColor);

            if (mSectionTextPosition == SIDES) {
                float y_ = yTop + mRectText.height() / 2f;

                String text = getMinText();
                mPaint.getTextBounds(text, 0, text.length(), mRectText);
                canvas.drawText(text, xLeft + mRectText.width() / 2f, y_, mPaint);
                xLeft += mRectText.width() + mTextSpace;

                text = getMaxText();
                mPaint.getTextBounds(text, 0, text.length(), mRectText);
                canvas.drawText(text, xRight - mRectText.width() / 2f, y_, mPaint);
                xRight -= (mRectText.width() + mTextSpace);

            } else if (mSectionTextPosition >= CustomSeekbar.TextPosition.BOTTOM_SIDES) {
                float y_ = yTop + mThumbRadiusOnDragging + mTextSpace;

                String text = getMinText();
                mPaint.getTextBounds(text, 0, text.length(), mRectText);
                y_ += mRectText.height();
                xLeft = mLeft;
                if (mSectionTextPosition == CustomSeekbar.TextPosition.BOTTOM_SIDES) {
                    canvas.drawText(text, xLeft, y_, mPaint);
                }

                text = getMaxText();
                mPaint.getTextBounds(text, 0, text.length(), mRectText);
                xRight = mRight;
                if (mSectionTextPosition == CustomSeekbar.TextPosition.BOTTOM_SIDES) {
                    canvas.drawText(text, xRight, y_, mPaint);
                }
            }
        } else if (isShowThumbText && mSectionTextPosition == NONE) {
            xLeft = mLeft;
            xRight = mRight;
        }

        if ((!isShowSectionText && !isShowThumbText) || mSectionTextPosition == CustomSeekbar.TextPosition.SIDES) {
            xLeft += mThumbRadiusOnDragging;
            xRight -= mThumbRadiusOnDragging;
        }

        boolean isShowTextBelowSectionMark = isShowSectionText && mSectionTextPosition ==
                CustomSeekbar.TextPosition.BELOW_SECTION_MARK;
        boolean conditionInterval = mSectionCount % 2 == 0;

        if (!isThumbOnDragging || isAlwaysShowBubble) {
            mThumbCenterX = mTrackLength / mDelta * (mProgress - mMin) + xLeft;
        }

        // draw thumbText
        if (isShowThumbText && !isThumbOnDragging && isTouchToSeekAnimEnd) {
            mPaint.setColor(mThumbTextColor);
            mPaint.setTextSize(mThumbTextSize);
            mPaint.getTextBounds("0123456789", 0, "0123456789".length(), mRectText); // compute solid height
            float y_ = yTop + mRectText.height() + mThumbRadiusOnDragging + mTextSpace;

            if (isFloatType || (isShowProgressInFloat && mSectionTextPosition == CustomSeekbar.TextPosition.BOTTOM_SIDES &&
                    mProgress != mMin && mProgress != mMax)) {
                canvas.drawText(String.valueOf(getProgressFloat()), mThumbCenterX, y_, mPaint);
            } else {
                canvas.drawText(String.valueOf(getProgress()), mThumbCenterX, y_, mPaint);
            }
        }

        // draw track
        mPaint.setColor(mSecondTrackColor);
        mPaint.setStrokeWidth(mSecondTrackSize);
        canvas.drawLine(xLeft, yTop, mThumbCenterX, yTop, mPaint);

        // draw second track
        mPaint.setColor(mTrackColor);
        mPaint.setStrokeWidth(mTrackSize);
        canvas.drawLine(mThumbCenterX, yTop, xRight, yTop, mPaint);

        // draw sectionMark & sectionText BELOW_SECTION_MARK
        if (isShowTextBelowSectionMark || isShowSectionMark) {
            float r = (mThumbRadiusOnDragging - Utils.dp2px(2)) / 2f;
            float junction = mTrackLength / mDelta * Math.abs(mProgress - mMin) + mLeft; // 交汇点
            mPaint.setTextSize(mSectionTextSize);
            mPaint.getTextBounds("0123456789", 0, "0123456789".length(), mRectText); // compute solid height

            float x_;
            float y_ = yTop + mRectText.height() + mThumbRadiusOnDragging + mTextSpace;

            for (int i = 0; i <= mSectionCount; i++) {
                x_ = xLeft + i * mSectionOffset;
//                mPaint.setColor(x_ <= junction ? mSecondTrackColor : mTrackColor);
                mPaint.setColor(Color.BLACK);
                // sectionMark
                canvas.drawCircle(x_, yTop, r, mPaint);

                // sectionText belows section
                if (isShowTextBelowSectionMark) {
                    mPaint.setColor(mSectionTextColor);

                    if (mSectionTextInterval > 1) {
                        if (conditionInterval && i % mSectionTextInterval == 0) {
                            float m = mMin + mSectionValue * i;
                            canvas.drawText(isFloatType ? float2String(m) : (int) m + "", x_, y_, mPaint);
                        }
                    } else {
                        float m = mMin + mSectionValue * i;
                        canvas.drawText(isFloatType ? float2String(m) : (int) m + "", x_, y_, mPaint);
                    }
                }
            }
        }

        // draw thumb
        mPaint.setColor(mThumbColor);
        canvas.drawCircle(mThumbCenterX, yTop, isThumbOnDragging ? mThumbRadiusOnDragging : mThumbRadius, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        post(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (!isAlwaysShowBubble)
            return;

        if (visibility != VISIBLE) {
            hideBubble();
        } else {
            if (triggerBubbleShowing) {
                showBubble();
            }
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onDetachedFromWindow() {
        hideBubble();
        mBubbleView = null;
        super.onDetachedFromWindow();
    }

    float dx;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isThumbOnDragging = isThumbTouched(event);
                if (isThumbOnDragging) {
                    if (isSeekBySection && !triggerSeekBySection) {
                        triggerSeekBySection = true;
                    }
                    if (isAlwaysShowBubble && !triggerBubbleShowing) {
                        triggerBubbleShowing = true;
                    }
                    showBubble();
                    invalidate();
                } else if (isTouchToSeek && isTrackTouched(event)) {
                    if (isAlwaysShowBubble) {
                        hideBubble();
                        triggerBubbleShowing = true;
                    }

                    mThumbCenterX = event.getX();
                    if (mThumbCenterX < mLeft) {
                        mThumbCenterX = mLeft;
                    }
                    if (mThumbCenterX > mRight) {
                        mThumbCenterX = mRight;
                    }
                    mProgress = (mThumbCenterX - mLeft) * mDelta / mTrackLength + mMin;
                    mBubbleCenterRawX = mBubbleCenterRawSolidX + mTrackLength * (mProgress - mMin) / mDelta;

                    showBubble();
                    invalidate();
                }

                dx = mThumbCenterX - event.getX();

                break;
            case MotionEvent.ACTION_MOVE:
                if (isThumbOnDragging) {
                    mThumbCenterX = event.getX() + dx;
                    if (mThumbCenterX < mLeft) {
                        mThumbCenterX = mLeft;
                    }
                    if (mThumbCenterX > mRight) {
                        mThumbCenterX = mRight;
                    }
                    mProgress = (mThumbCenterX - mLeft) * mDelta / mTrackLength + mMin;

                    mBubbleCenterRawX = mBubbleCenterRawSolidX + mTrackLength * (mProgress - mMin) / mDelta;
                    mLayoutParams.x = (int) (mBubbleCenterRawX + 0.5f);
                    mWindowManager.updateViewLayout(mBubbleView, mLayoutParams);
                    mBubbleView.setProgressText(isShowProgressInFloat ?
                            String.valueOf(getProgressFloat()) : String.valueOf(getProgress()));

                    invalidate();

                    if (mProgressListener != null) {
                        mProgressListener.onProgressChanged(getProgress(), getProgressFloat());
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isAutoAdjustSectionMark) {
                    if (isTouchToSeek) {
                        mBubbleView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isTouchToSeekAnimEnd = false;
                                autoAdjustSection();
                            }
                        }, isThumbOnDragging ? 0 : 300);
                    } else {
                        autoAdjustSection();
                    }
                } else if (isThumbOnDragging || isTouchToSeek) {
                    mBubbleView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBubbleView.animate()
                                    .alpha(isAlwaysShowBubble ? 1f : 0f)
                                    .setDuration(mAnimDuration)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            if (!isAlwaysShowBubble) {
                                                hideBubble();
                                            }

                                            isThumbOnDragging = false;
                                            invalidate();

                                            if (mProgressListener != null) {
                                                mProgressListener.onProgressChanged(getProgress(),
                                                        getProgressFloat());
                                            }
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {
                                            if (!isAlwaysShowBubble) {
                                                hideBubble();
                                            }

                                            isThumbOnDragging = false;
                                            invalidate();
                                        }
                                    })
                                    .start();

                        }
                    }, !isThumbOnDragging && isTouchToSeek ? 300 : 0);
                }

                if (mProgressListener != null) {
                    mProgressListener.getProgressOnActionUp(getProgress(), getProgressFloat());
                }

                break;
        }

        return isThumbOnDragging || isTouchToSeek || super.onTouchEvent(event);
    }

    /**
     * Detect effective touch of thumb
     */
    private boolean isThumbTouched(MotionEvent event) {
        if (!isEnabled())
            return false;

        float x = mTrackLength / mDelta * (mProgress - mMin) + mLeft;
        float y = getMeasuredHeight() / 2f;
        return (event.getX() - x) * (event.getX() - x) + (event.getY() - y) * (event.getY() - y)
                <= (mLeft + Utils.dp2px(8)) * (mLeft + Utils.dp2px(8));
    }

    /**
     * Detect effective touch of track
     */
    private boolean isTrackTouched(MotionEvent event) {
        if (!isEnabled())
            return false;

        return event.getX() >= getPaddingLeft() && event.getX() <= getMeasuredWidth() - getPaddingRight()
                && event.getY() >= getPaddingTop() && event.getY() <= getPaddingTop() + mThumbRadiusOnDragging * 2;
    }

    /**
     * Showing the Bubble depends the way that the WindowManager adds a Toast type view to the Window.
     * <p>
     * 显示气泡
     * 原理是利用WindowManager动态添加一个与Toast相同类型的BubbleView，消失时再移除
     */
    private void showBubble() {
        if (mBubbleView == null || mBubbleView.getParent() != null) {
            return;
        }

        if (mLayoutParams == null) {
            mLayoutParams = new WindowManager.LayoutParams();
            mLayoutParams.gravity = Gravity.START | Gravity.TOP;
            mLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mLayoutParams.format = PixelFormat.TRANSLUCENT;
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            // MIUI禁止了开发者使用TYPE_TOAST，Android 7.1.1 对TYPE_TOAST的使用更严格
            if (Utils.isMIUI() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
            } else {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            }
        }
        mLayoutParams.x = (int) (mBubbleCenterRawX + 0.5f);
        mLayoutParams.y = (int) (mBubbleCenterRawSolidY + 0.5f);

        mBubbleView.setAlpha(0);
        mBubbleView.setVisibility(VISIBLE);
        mBubbleView.animate().alpha(1f).setDuration(mAnimDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mWindowManager.addView(mBubbleView, mLayoutParams);
                    }
                }).start();
        mBubbleView.setProgressText(isShowProgressInFloat ?
                String.valueOf(getProgressFloat()) : String.valueOf(getProgress()));
    }

    /**
     * Auto scroll to the nearest section mark
     */
    private void autoAdjustSection() {
        int i;
        float x = 0;
        for (i = 0; i <= mSectionCount; i++) {
            x = i * mSectionOffset + mLeft;
            if (x <= mThumbCenterX && mThumbCenterX - x <= mSectionOffset) {
                break;
            }
        }

        BigDecimal bigDecimal = BigDecimal.valueOf(mThumbCenterX);
        float x_ = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        boolean onSection = x_ == x; // 就在section处，不作valueAnim，优化性能

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator valueAnim = null;
        if (!onSection) {
            if (mThumbCenterX - x <= mSectionOffset / 2f) {
                valueAnim = ValueAnimator.ofFloat(mThumbCenterX, x);
            } else {
                valueAnim = ValueAnimator.ofFloat(mThumbCenterX, (i + 1) * mSectionOffset + mLeft);
            }
            valueAnim.setInterpolator(new LinearInterpolator());
            valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mThumbCenterX = (float) animation.getAnimatedValue();
                    mProgress = (mThumbCenterX - mLeft) * mDelta / mTrackLength + mMin;

                    mBubbleCenterRawX = mBubbleCenterRawSolidX + mThumbCenterX - mLeft;
                    mLayoutParams.x = (int) (mBubbleCenterRawX + 0.5f);
                    if (mBubbleView.getParent() != null) {
                        mWindowManager.updateViewLayout(mBubbleView, mLayoutParams);
                    }
                    mBubbleView.setProgressText(isShowProgressInFloat ?
                            String.valueOf(getProgressFloat()) : String.valueOf(getProgress()));

                    invalidate();

                    if (mProgressListener != null) {
                        mProgressListener.onProgressChanged(getProgress(), getProgressFloat());
                    }
                }
            });
        }

        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mBubbleView, View.ALPHA, isAlwaysShowBubble ? 1 : 0);

        if (onSection) {
            animatorSet.setDuration(mAnimDuration).play(alphaAnim);
        } else {
            animatorSet.setDuration(mAnimDuration).playTogether(valueAnim, alphaAnim);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isAlwaysShowBubble) {
                    hideBubble();
                }

                mProgress = (mThumbCenterX - mLeft) * mDelta / mTrackLength + mMin;
                isThumbOnDragging = false;
                isTouchToSeekAnimEnd = true;
                invalidate();

                if (mProgressListener != null) {
                    mProgressListener.getProgressOnFinally(getProgress(), getProgressFloat());
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (!isAlwaysShowBubble) {
                    hideBubble();
                }

                mProgress = (mThumbCenterX - mLeft) * mDelta / mTrackLength + mMin;
                isThumbOnDragging = false;
                isTouchToSeekAnimEnd = true;
                invalidate();
            }
        });
        animatorSet.start();
    }

    /**
     * The WindowManager removes the BubbleView from the Window.
     */
    private void hideBubble() {
        mBubbleView.setVisibility(GONE); // 防闪烁
        if (mBubbleView.getParent() != null) {
            mWindowManager.removeViewImmediate(mBubbleView);
        }
    }

    /**
     * When BubbleSeekBar's parent view is scrollable, must listener to it's scrolling and call this
     * method to correct the offsets.
     */
    public void correctOffsetWhenContainerOnScrolling() {
        locatePositionOnScreen();

        if (mBubbleView.getParent() != null) {
            postInvalidate();
        }
    }

    private String getMinText() {
        return isFloatType ? float2String(mMin) : String.valueOf((int) mMin);
    }

    private String getMaxText() {
        return isFloatType ? float2String(mMax) : String.valueOf((int) mMax);
    }

    public float getMin() {
        return mMin;
    }

    public float getMax() {
        return mMax;
    }

    public void setProgress(float progress) {
        mProgress = progress;

        mBubbleCenterRawX = mBubbleCenterRawSolidX + mTrackLength * (mProgress - mMin) / mDelta;

        if (mProgressListener != null) {
            mProgressListener.onProgressChanged(getProgress(), getProgressFloat());
            mProgressListener.getProgressOnFinally(getProgress(), getProgressFloat());
        }
        if (isAlwaysShowBubble) {
            hideBubble();

            int[] location = new int[2];
            getLocationOnScreen(location);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    showBubble();
                    triggerBubbleShowing = true;
                }
            }, location[0] == 0 && location[1] == 0 ? 200 : 0);
        }

        postInvalidate();
    }

    public int getProgress() {
        if (isSeekBySection && triggerSeekBySection) {
            float half = mSectionValue / 2;

            if (mProgress >= mPreSecValue) { // increasing
                if (mProgress >= mPreSecValue + half) {
                    mPreSecValue += mSectionValue;
                    return Math.round(mPreSecValue);
                } else {
                    return Math.round(mPreSecValue);
                }
            } else { // reducing
                if (mProgress >= mPreSecValue - half) {
                    return Math.round(mPreSecValue);
                } else {
                    mPreSecValue -= mSectionValue;
                    return Math.round(mPreSecValue);
                }
            }
        }

        return Math.round(mProgress);
    }

    public float getProgressFloat() {
        return formatFloat(mProgress);
    }

    public CustomSeekbar.OnProgressChangedListener getOnProgressChangedListener() {
        return mProgressListener;
    }

    public void setOnProgressChangedListener(CustomSeekbar.OnProgressChangedListener onProgressChangedListener) {
        mProgressListener = onProgressChangedListener;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("save_instance", super.onSaveInstanceState());
        bundle.putFloat("progress", mProgress);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mProgress = bundle.getFloat("progress");
            super.onRestoreInstanceState(bundle.getParcelable("save_instance"));
            mBubbleView.setProgressText(isShowProgressInFloat ?
                    String.valueOf(getProgressFloat()) : String.valueOf(getProgress()));
            if (isAlwaysShowBubble) {
                setProgress(mProgress);
            }

            return;
        }

        super.onRestoreInstanceState(state);
    }

    private String float2String(float value) {
        return String.valueOf(formatFloat(value));
    }

    private float formatFloat(float value) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        return bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * Listen to progress onChanged, onActionUp, onFinally
     */
    public interface OnProgressChangedListener {

        void onProgressChanged(int progress, float progressFloat);

        void getProgressOnActionUp(int progress, float progressFloat);

        void getProgressOnFinally(int progress, float progressFloat);
    }

    /**
     * Listener adapter
     * <br/>
     * usage like {@link AnimatorListenerAdapter}
     */
    public static abstract class OnProgressChangedListenerAdapter implements CustomSeekbar.OnProgressChangedListener {

        @Override
        public void onProgressChanged(int progress, float progressFloat) {
        }

        @Override
        public void getProgressOnActionUp(int progress, float progressFloat) {
        }

        @Override
        public void getProgressOnFinally(int progress, float progressFloat) {
        }
    }

    /*******************************************************************************************
     * ************************************  custom bubble view  ************************************
     *******************************************************************************************/
    public class BubbleView extends View {

        private Paint mBubblePaint;
        private Path mBubblePath;
        private RectF mBubbleRectF;
        private Rect mRect;
        private String mProgressText = "";

        BubbleView(Context context) {
            this(context, null);
        }

        BubbleView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            mBubblePaint = new Paint();
            mBubblePaint.setAntiAlias(true);
            mBubblePaint.setTextAlign(Paint.Align.CENTER);

            mBubblePath = new Path();
            mBubbleRectF = new RectF();
            mRect = new Rect();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            setMeasuredDimension(3 * mBubbleRadius, 3 * mBubbleRadius);

            mBubbleRectF.set(getMeasuredWidth() / 2f - mBubbleRadius, 0,
                    getMeasuredWidth() / 2f + mBubbleRadius, 2 * mBubbleRadius);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            mBubblePath.reset();
            float x0 = getMeasuredWidth() / 2f;
            float y0 = getMeasuredHeight() - mBubbleRadius / 3f;
            mBubblePath.moveTo(x0, y0);
            float x1 = (float) (getMeasuredWidth() / 2f - Math.sqrt(3) / 2f * mBubbleRadius);
            float y1 = 3 / 2f * mBubbleRadius;
            mBubblePath.quadTo(
                    x1 - Utils.dp2px(2), y1 - Utils.dp2px(2),
                    x1, y1
            );
            mBubblePath.arcTo(mBubbleRectF, 150, 240);

            float x2 = (float) (getMeasuredWidth() / 2f + Math.sqrt(3) / 2f * mBubbleRadius);
            mBubblePath.quadTo(
                    x2 + Utils.dp2px(2), y1 - Utils.dp2px(2),
                    x0, y0
            );
            mBubblePath.close();

            mBubblePaint.setColor(mBubbleColor);
            canvas.drawPath(mBubblePath, mBubblePaint);

            mBubblePaint.setTextSize(mBubbleTextSize);
            mBubblePaint.setColor(mBubbleTextColor);
            mBubblePaint.getTextBounds(mProgressText, 0, mProgressText.length(), mRect);
            Paint.FontMetrics fm = mBubblePaint.getFontMetrics();
            float baseline = mBubbleRadius + (fm.descent - fm.ascent) / 2f - fm.descent;
            canvas.drawText(mProgressText, getMeasuredWidth() / 2f, baseline, mBubblePaint);
        }

        void setProgressText(String progressText) {
            if (progressText != null && !mProgressText.equals(progressText)) {
                mProgressText = progressText;
                invalidate();
            }
        }
    }

}
