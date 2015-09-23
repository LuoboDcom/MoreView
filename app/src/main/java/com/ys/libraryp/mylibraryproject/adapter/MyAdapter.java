package com.ys.libraryp.mylibraryproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ys.libraryp.mylibraryproject.R;

/**  适配器
 * Created by ys on 2015/9/22 0022.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private String[] datas;
    public MyAdapter(String[] datas){
        this.datas = datas;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_view,parent,false);
        //set the view's size ,margins,paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.tv.setText(datas[position]);
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
