package com.ys.libraryp.mylibraryproject.view.HoveringScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2015/9/7 0007.
 */
public class HoveringScrollView extends FrameLayout{

    private ViewGroup mContentView,mTopView;
    private View mTopContent;
    private int mTopViewTop;

    public HoveringScrollView(Context context) {
        super(context);
        init();
    }

    public HoveringScrollView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        init();
    }

    public HoveringScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        post(new Runnable() {
            @Override
            public void run() {
                mContentView = (ViewGroup) getChildAt(0);
                removeAllViews();

                MyScrollView scrollView = new MyScrollView(getContext(), HoveringScrollView.this);
                scrollView.addView(mContentView);
                addView(scrollView);

            }
        });
    }

    public void setTopView(final int id) {
        post(new Runnable() {
            @Override
            public void run() {
                mTopView = (ViewGroup) mContentView.findViewById(id);

                int height = mTopView.getChildAt(0).getMeasuredHeight();
                ViewGroup.LayoutParams params = mTopView.getLayoutParams();
                params.height = height;
                mTopView.setLayoutParams(params);
                mTopViewTop = mTopView.getTop();
                mTopContent = mTopView.getChildAt(0);
            }
        });
    }

    static class MyScrollView extends ScrollView {

        private HoveringScrollView mScrollView;

        public MyScrollView(Context context, HoveringScrollView scrollView) {
            super(context);
            mScrollView = scrollView;
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            mScrollView.onScroll(t);
        }

    }

    public void onScroll(final int scrollY) {
        post(new Runnable() {
            @Override
            public void run() {
                if (mTopView == null) return;

                if (scrollY >= mTopViewTop
                        && mTopContent.getParent() == mTopView) {
                    mTopView.removeView(mTopContent);
                    addView(mTopContent);
                } else if (scrollY < mTopViewTop
                        && mTopContent.getParent() == HoveringScrollView.this) {
                    removeView(mTopContent);
                    mTopView.addView(mTopContent);
                }

            }
        });
    }
}

