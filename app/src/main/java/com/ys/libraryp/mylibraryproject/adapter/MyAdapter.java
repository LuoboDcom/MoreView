package com.ys.libraryp.mylibraryproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.bean.ImageInfo;

import java.util.List;

/**  适配器
 * Created by ys on 2015/9/22 0022.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<ImageInfo> datas;
    public MyAdapter(List<ImageInfo> datas){
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
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        ImageInfo info = datas.get(position);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
                int i = index.intValue();
                Log.i("ys","i==="+i);
                datas.remove(i);
                notifyItemRemoved(index.intValue());
                notifyDataSetChanged();
            }
        });
        holder.card_tv1.setText(info.getImageAlt());
        holder.card_tv2.setText(info.getImageDes());
        holder.card_img.setImageResource(info.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return datas == null? 0:datas.size();
    }

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView card_tv1,card_tv2;
        private ImageView card_img;
        public ViewHolder(View itemView) {
            super(itemView);
            card_tv1 = (TextView) itemView.findViewById(R.id.card_tv1);
            card_tv2 = (TextView) itemView.findViewById(R.id.card_tv2);
            card_img = (ImageView) itemView.findViewById(R.id.card_img);
        }
    }
}
