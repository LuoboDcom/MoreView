package com.ys.libraryp.mylibraryproject.view.FlowingView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 *  流式布局
 * Created by ys on 2015/9/16 0016.
 */

/**
 * 从控件创建过程说起

 1.当这个流式布局在被加载如内存并显示在屏幕上这一过程中，首先会调用view.measure(w,h)这个方法，表示测量view的宽度与高度，其中参数w与h分别表示这个控件的父控件的宽高。
 2.在view.measure()方法的调用过程中又会调用view本身的一个回调方法,onMeasure()，这个是view自身的一个回调方法，用于让开发者在自定义View的时候重新计算自身的大小。一般会在这个方法中循环遍历，计算出这个控件的全部子孙控件的宽高。
 3.在View的宽高计算完成以后，考虑将这个控件显示到屏幕的指定位置上，此时view的onLayout()方法会被调用。 一般同时会在这个方法中计算出全部子孙控件在这个控件中的位置。
 可能基本流程有些枯燥，接下来结合代码看看。

 */
public class FlowLayout extends ViewGroup {
    private float mVerticalSpacing; //每个item纵向间距
    private float mHorizontalSpacing; //每个item横向间距

    public FlowLayout(Context context) {
        super(context);
    }
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setHorizontalSpacing(float pixelSize) {
        mHorizontalSpacing = pixelSize;
    }
    public void setVerticalSpacing(float pixelSize) {
        mVerticalSpacing = pixelSize;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int selfWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;

        //通过计算每一个子控件的高度，得到自己的高度
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);
            LayoutParams childLayoutParams = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                            childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                            childLayoutParams.height));
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > selfWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }
        }

        int wantedHeight = childTop + lineHeight + paddingBottom;
        setMeasuredDimension(selfWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        //根据子控件的宽高，计算子控件应该出现的位置。
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }
}