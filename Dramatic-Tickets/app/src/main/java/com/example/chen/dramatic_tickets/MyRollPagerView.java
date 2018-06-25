package com.example.chen.dramatic_tickets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jude.rollviewpager.RollPagerView;

/**
 * Created by chen on 2018/6/23.
 */

public class MyRollPagerView extends RollPagerView {
    public MyRollPagerView(Context context) {
        super(context);
    }

    public MyRollPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRollPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }
}
