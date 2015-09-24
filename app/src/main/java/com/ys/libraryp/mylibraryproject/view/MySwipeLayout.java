package com.ys.libraryp.mylibraryproject.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2015/9/23 0023.
 */
public class MySwipeLayout extends SwipeRefreshLayout{

    private final String LOG_TAG = MySwipeLayout.class.getName();
    private static final int INVALID_POINTER = -1;

    public MySwipeLayout(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MySwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private int mActivePointerId;
    private float mInitialDownX;
    private int mTouchSlop;
    private boolean mIsBeingDragged = false;

    private float xDistance;
    private float yDistance;
    private float xLast;
    private float yLast;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    private float getMotionEventX(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getX(ev, index);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0.0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);

                if(xDistance > yDistance)
                    return false;
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);

//        myInterceptTouchEvent(ev);
//        if(!mIsBeingDragged){
//            return super.onInterceptTouchEvent(ev);
//        }else{
//            return false;
//        }
    }



    public void myInterceptTouchEvent(MotionEvent ev){
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev,0);
//                Log.i(LOG_TAG,"mActivePointerId======"+mActivePointerId);
                mIsBeingDragged = false;
                final float initialDownX = getMotionEventX(ev, mActivePointerId);
                if (initialDownX == -1) {
                    return;
                }
                mInitialDownX = initialDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
//                Log.i(LOG_TAG,"pointerIndex====="+pointerIndex);
                if (pointerIndex < 0) {
//                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return;
                }
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float xDiff = x - mInitialDownX;
//                Log.i(LOG_TAG,"x====="+x);
                if(!mIsBeingDragged){
                    Log.i(LOG_TAG,"xDiff=="+xDiff+"----mTouchSlop=="+mTouchSlop);
                    if (xDiff > mTouchSlop) {
                        mIsBeingDragged = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }
    }
}
