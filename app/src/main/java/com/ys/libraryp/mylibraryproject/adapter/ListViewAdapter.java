package com.ys.libraryp.mylibraryproject.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.bean.ProductInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/10/9 0009.
 */
public class ListViewAdapter extends BaseAdapter{

    private List<ProductInfo> list;
    private DisplayImageOptions options;
    private ImageLoadingListener imageLoadingListener;

    public ListViewAdapter(List<ProductInfo> list,ImageLoadingListener imageLoadingListener){
        this.list = list;
        this.imageLoadingListener = imageLoadingListener;
        //初始化 图片属性数据
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
//                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_view, parent, false);
            holder.card_tv1 = (TextView) convertView.findViewById(R.id.card_tv1);
            holder.card_tv2 = (TextView) convertView.findViewById(R.id.card_tv2);
            holder.card_img = (ImageView) convertView.findViewById(R.id.card_img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ProductInfo pro =  list.get(position);
        holder.card_tv1.setText(pro.getName());
        holder.card_tv2.setText("￥" + pro.getPrice());
        holder.card_img.setTag(pro.getImageUrl());
//        ImageLoader.getInstance().displayImage(pro.getImageUrl(), holder.card_img, options, imageLoadingListener);
        ImageLoader.getInstance().loadImage(pro.getImageUrl(),options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
//                holder.card_img.setImageResource(R.drawable.ic_stub);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                if(holder.card_img.getTag().equals(imageUri)){
                    holder.card_img.setImageBitmap(loadedImage);
                }
            }
        });
        return convertView;
    }

    public static class ViewHolder{
        private TextView card_tv1,card_tv2;
        private ImageView card_img;
        public ViewHolder() {

        }
    }

}
