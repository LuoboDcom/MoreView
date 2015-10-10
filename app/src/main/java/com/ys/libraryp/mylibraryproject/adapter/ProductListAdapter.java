package com.ys.libraryp.mylibraryproject.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.bean.ProductInfo;

import java.util.List;

/**  适配器
 * Created by ys on 2015/9/22 0022.
 */
public class ProductListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;
    private static final int TYPE_ADAPTEE_OFFSET = 2;
    private boolean hasFooter;
    private boolean hasMoreData;

    private List<T> datas;
    private DisplayImageOptions options;
    private ImageLoadingListener imageLoadingListener;

    public boolean isHasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        if(this.hasFooter != hasFooter) {
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(boolean hasMoreData) {
        if(this.hasMoreData != hasMoreData) {
            this.hasMoreData = hasMoreData;
            notifyDataSetChanged();
        }
    }

    public ProductListAdapter(List<T> datas,ImageLoadingListener imageLoadingListener){
        this.datas = datas;
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

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
            return new FooterViewHolder(view);
        }else {
            //create new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_view, parent, false);
            //set the view's size ,margins,paddings and layout parameters
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }
    }

    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder) {
            Log.i("adapter", "instanceof ProductInfo");
            ProductInfo pro = (ProductInfo) datas.get(position);
            ((ViewHolder)holder).itemView.setTag(position);
            ((ViewHolder)holder).card_tv1.setText(pro.getName());
            ((ViewHolder)holder).card_tv2.setText("￥" + pro.getPrice());
            ((ViewHolder)holder).card_img.setTag(pro.getImageUrl());
            ImageLoader.getInstance().displayImage(pro.getImageUrl(), ((ViewHolder) holder).card_img, options, imageLoadingListener);
            Log.i("adapter", "onBindViewHolder");
        }else{
            if(hasMoreData){
                ((FooterViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).tv_footer.setText("正在加载...");
            }else{
                ((FooterViewHolder) holder).progressBar.setVisibility(View.GONE);
                ((FooterViewHolder) holder).tv_footer.setText("没有更多数据了");
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas == null? 0:datas.size()+(hasFooter ? 1:0);
    }

    public int getBasicItemCount(){
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getBasicItemCount() && hasFooter ){
            return  TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    public void setHasMoreDataAndFooter(boolean hasMoreData,boolean hasFooter){
        if(this.hasMoreData != hasMoreData || this.hasFooter != hasFooter){
            this.hasMoreData = hasMoreData;
            this.hasFooter = hasFooter;
        }
        notifyDataSetChanged();
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

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_footer;
        private ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tv_footer = (TextView) itemView.findViewById(R.id.loadmore_tv);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

    }
}
