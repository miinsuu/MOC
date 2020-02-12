package com.momeokji.moc.Helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NonSwipeableViewPager extends ViewPager {
    private  boolean isEnableSwipe = false;

    public NonSwipeableViewPager(Context context) {
        super(context);
    }
    public NonSwipeableViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isEnableSwipe = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isEnableSwipe) {
            return super.onTouchEvent(event);
        }
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isEnableSwipe) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setEnableSwipe(boolean isEnableSwipe) {
        this.isEnableSwipe = isEnableSwipe;
    }
}
