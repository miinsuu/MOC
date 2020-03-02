package com.momeokji.moc.CustomView;

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
            return this.isEnableSwipe && super.onTouchEvent(event);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
            return this.isEnableSwipe && super.onInterceptTouchEvent(event);
    }

    public void setEnableSwipe(boolean isEnableSwipe) {
        this.isEnableSwipe = isEnableSwipe;
    }
}
