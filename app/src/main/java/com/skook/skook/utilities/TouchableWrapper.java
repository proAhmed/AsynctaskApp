package com.skook.skook.utilities;

/**
 * Created by Ahmed shaban on 8/13/2017.
 */

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class TouchableWrapper extends FrameLayout {

    public TouchableWrapper(Context context) {
        super(context);
    }

    public void setTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    private OnTouchListener onTouchListener;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchListener.onTouch();
                break;
            case MotionEvent.ACTION_UP:
                onTouchListener.onRelease();
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    public interface OnTouchListener {
        public void onTouch();
        public void onRelease();
    }
}
