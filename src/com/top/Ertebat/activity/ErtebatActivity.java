package com.top.Ertebat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.top.Ertebat.activity.ErtebatActivityCommon.ErtebatActivityMagic;
import com.top.Ertebat.activity.misc.SwipeGestureDetector.OnSwipeGestureListener;


public class ErtebatActivity extends Activity implements ErtebatActivityMagic {

    private ErtebatActivityCommon mBase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mBase = ErtebatActivityCommon.newInstance(this);
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
