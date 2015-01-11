package com.fsck.ertebat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.fsck.ertebat.activity.ertebatActivityCommon.ertebatActivityMagic;
import com.fsck.ertebat.activity.misc.SwipeGestureDetector.OnSwipeGestureListener;


public class ertebatFragmentActivity extends Activity implements ertebatActivityMagic {

    private ertebatActivityCommon mBase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mBase = ertebatActivityCommon.newInstance(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        mBase.preDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void setupGestureDetector(OnSwipeGestureListener listener) {
        mBase.setupGestureDetector(listener);
    }
}
