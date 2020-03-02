package com.momeokji.moc.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class BackPressEditText extends androidx.appcompat.widget.AppCompatEditText {

    public BackPressEditText(Context context) {
        super(context);
    }

    public BackPressEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BackPressEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus();
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
