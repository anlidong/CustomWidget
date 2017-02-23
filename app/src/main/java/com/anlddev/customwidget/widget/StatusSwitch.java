package com.anlddev.customwidget.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.anlddev.customwidget.R;

/**
 * 三状态开关
 * Created by anld on 2017/1/12.
 */

public class StatusSwitch extends FrameLayout {

    private ViewDragHelper mDragger;

    private View child;

    private int childWidth;
    private int width;

    private int mode = -1;

    private OnModeChangedListener listener;

    private boolean isLayout = false;

    public void setOnModeChangedListener(OnModeChangedListener listener) {
        this.listener = listener;
    }

    public void setMode(final int mode) {
        if (this.mode != mode) {
            if (listener != null) {
                listener.onModeChanged(mode);
            }
            this.mode = mode;
        }
        post(new Runnable() {
            @Override
            public void run() {
                switch (mode) {
                    case OnModeChangedListener.MODE_LOW:
                        if (mDragger.smoothSlideViewTo(child, 0, 0)) {
                            ViewCompat.postInvalidateOnAnimation(StatusSwitch.this);
                        }
                        break;
                    case OnModeChangedListener.MODE_NORMAL:
                        if (mDragger.smoothSlideViewTo(child, width / 2 - childWidth / 2, 0)) {
                            ViewCompat.postInvalidateOnAnimation(StatusSwitch.this);
                        }
                        break;
                    case OnModeChangedListener.MODE_HIGH:
                        if (mDragger.smoothSlideViewTo(child, width - childWidth, 0)) {
                            ViewCompat.postInvalidateOnAnimation(StatusSwitch.this);
                        }
                        break;
                }
            }
        });
    }

    public int getMode() {
        return mode;
    }

    private void interiorSetMode(int mode) {
        if (listener != null && this.mode != mode) {
            listener.onModeChanged(mode);
        }
        this.mode = mode;

        switch (mode) {
            case OnModeChangedListener.MODE_LOW:
                if (child.getLeft() != 0 && mDragger.smoothSlideViewTo(child, 0, 0)) {
                    ViewCompat.postInvalidateOnAnimation(StatusSwitch.this);
                }
                break;
            case OnModeChangedListener.MODE_NORMAL:
                if (child.getLeft() != width / 2 - childWidth / 2
                        && mDragger.smoothSlideViewTo(child, width / 2 - childWidth / 2, 0)) {
                    ViewCompat.postInvalidateOnAnimation(StatusSwitch.this);
                }
                break;
            case OnModeChangedListener.MODE_HIGH:
                if (child.getLeft() != width - childWidth
                        && mDragger.smoothSlideViewTo(child, width - childWidth, 0)) {
                    ViewCompat.postInvalidateOnAnimation(StatusSwitch.this);
                }
                break;
        }
    }

    public StatusSwitch(final Context context, AttributeSet attrs) {
        super(context, attrs);
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(R.mipmap.pic_knob);
        iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(iv);
        mDragger = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == StatusSwitch.this.child;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //拖拽左边界
                if (left < 0) {
                    left = 0;
                } else if (left > width - childWidth) {
                    left = width - childWidth;
                }

                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                //拖拽上边界
                return 0;
            }


            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //手指释放时自动弹回
                if (child.getLeft() <= width / 4) {
                    interiorSetMode(OnModeChangedListener.MODE_LOW);
                } else if (child.getLeft() >= width / 4 * 3 - childWidth) {
                    interiorSetMode(OnModeChangedListener.MODE_HIGH);
                } else {
                    interiorSetMode(OnModeChangedListener.MODE_NORMAL);
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (x >= 0 && x <= childWidth) {
                    if (mode != OnModeChangedListener.MODE_LOW) {
                        interiorSetMode(OnModeChangedListener.MODE_LOW);
                    }
                } else if (x >= width / 2 - childWidth / 2 && x <= width / 2 + childWidth / 2) {
                    if (mode != OnModeChangedListener.MODE_NORMAL) {
                        interiorSetMode(OnModeChangedListener.MODE_NORMAL);
                    }
                } else if (x >= width - childWidth && x <= width) {
                    if (mode != OnModeChangedListener.MODE_HIGH) {
                        interiorSetMode(OnModeChangedListener.MODE_HIGH);
                    }
                }
                break;
        }

        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        //回弹时刷新界面
        if (mDragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isLayout) {
            super.onLayout(changed, l, t, r, b);
            childWidth = child.getWidth();
            width = getWidth();
            isLayout = true;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child = getChildAt(0);
    }

    public interface OnModeChangedListener {
        int MODE_LOW = 1;//模式:低
        int MODE_NORMAL = 2;//模式:中
        int MODE_HIGH = 3;//模式:高

        void onModeChanged(int mode);
    }
}