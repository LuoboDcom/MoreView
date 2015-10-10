package com.ys.libraryp.mylibraryproject.RecyclerViewOptions;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2015/9/25 0025.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
    private int space;
    private int numCount;

    public SpacesItemDecoration(int space,int numCount) {
        this.space=space;
        this.numCount = numCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
//        if(parent.getChildAdapterPosition(view)<numCount){
            outRect.top=space;
//        }
    }
}
