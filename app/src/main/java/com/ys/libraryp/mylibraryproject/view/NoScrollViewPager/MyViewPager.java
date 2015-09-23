package com.ys.libraryp.mylibraryproject.view.NoScrollViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**   自定义viewpager
 *    开启/关闭 viewpager的滑动功能
 * Created by ys on 2015/9/21 0021.
 */
public class MyViewPager extends ViewPager{
    private Boolean isCanScroll = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isCanScroll) {
            return super.onTouchEvent(ev);
        }else{
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isCanScroll) {
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }

    public Boolean getIsCanScroll() {
        return isCanScroll;
    }

    public void setIsCanScroll(Boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}
