package com.ys.libraryp.mylibraryproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.ys.libraryp.mylibraryproject.MyApplication;
import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.bean.ImageInfo;
import com.ys.libraryp.mylibraryproject.bean.ProductInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25 0025.
 */
public class WaterFallAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<T> list;
    private DisplayImageOptions options;
    private ImageLoadingListener imageLoadingListener;

    public WaterFallAdapter(List<T> list,ImageLoadingListener imageLoadingListener){
        this.list = list;
        this.imageLoadingListener = imageLoadingListener;

//        Display currentDisplay = getWindowManager().getDefaultDisplay();
//        int dw = currentDisplay.getWidth();
        BitmapFactory.Options  decodeOptions = new BitmapFactory.Options();
        decodeOptions.outHeight = 160;
        decodeOptions.outWidth = 160;

        //初始化 图片属性数据
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .decodingOptions(decodeOptions)
                .displayer(new SimpleBitmapDisplayer())
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
//                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_waterfall,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( final RecyclerView.ViewHolder holder, int position) {
        ProductInfo info = (ProductInfo) list.get(position);
        ((ViewHolder) holder).masonry_item_title.setText(info.getName());
        ((ViewHolder) holder).masonry_item_img.setTag(info.getImageUrl());

//        ImageLoader.getInstance().loadImage(info.getImageUrl(), new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUrl, View view,
//                                          Bitmap loadedImage) {
//                super.onLoadingComplete(imageUrl, view, loadedImage);
//                if (imageUrl.equals(((ViewHolder) holder).masonry_item_img.getTag())) {
//                    ((ViewHolder) holder).masonry_item_img.setImageBitmap(loadedImage);
//                }
//            }
//        });
        ImageLoader.getInstance().displayImage(info.getImageUrl(), ((ViewHolder) holder).masonry_item_img, options, imageLoadingListener);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0:list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView masonry_item_img;
        private TextView masonry_item_title;

        public ViewHolder(View itemView) {
            super(itemView);
            masonry_item_img = (ImageView) itemView.findViewById(R.id.masonry_item_img);
            masonry_item_title = (TextView) itemView.findViewById(R.id.masonry_item_title);
        }
    }
}
