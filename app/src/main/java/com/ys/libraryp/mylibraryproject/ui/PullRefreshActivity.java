package com.ys.libraryp.mylibraryproject.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.ys.libraryp.mylibraryproject.MyApplication;
import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.adapter.ListViewAdapter;
import com.ys.libraryp.mylibraryproject.bean.ProductInfo;
import com.ys.libraryp.mylibraryproject.view.PullRefresh.RefreshableView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *   下拉加listview侧滑
 */
public class PullRefreshActivity extends ActionBarActivity {

    private RefreshableView refreshView;
    private SwipeMenuListView listView;
    private ListViewAdapter mAdapter;
    private List<ProductInfo> productInfoList;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    private int startId = 0;  //数据起始id
    private int nums = 20;    //每次获取多少数据
    private boolean hasMoreData = false;  //是否有更多数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
//                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();

        initData(startId,nums,true);
        initView();
        initSlideMenu();
    }

    private void initSlideMenu() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                    case 2:
                        createMenu3(menu);
                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18,
                        0x5E)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.ic_action_favorite);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.ic_action_good);
                menu.addMenuItem(item2);
            }

            private void createMenu2(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0,
                        0x3F)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.ic_action_important);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.ic_action_discard);
                menu.addMenuItem(item2);
            }

            private void createMenu3(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1,
                        0xF5)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.ic_action_about);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.ic_action_share);
                menu.addMenuItem(item2);
            }
        };

        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                ApplicationInfo item = mAppList.get(position);
//                switch (index) {
//                    case 0:
//                        // open
//                        break;
//                    case 1:
//                        // delete
////					delete(item);
////                        mAppList.remove(position);
////                        mAdapter.notifyDataSetChanged();
//                        break;
//                }
                return false;
            }
        });
    }

    private int start_index, end_index;
    private void initView() {
        refreshView = (RefreshableView) findViewById(R.id.pullRefresh);
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        View footView = LayoutInflater.from(this).inflate(R.layout.footer_layout,null);
        listView.addFooterView(footView);

        mAdapter = new ListViewAdapter(productInfoList,animateFirstListener);
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true,new AbsListView.OnScrollListener() {
            boolean isRefreshing = false;
            boolean isBottom = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                switch (scrollState) {
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 滑动停止
//                        for (; start_index < end_index; start_index++) {
//                            ImageView img = (ImageView) listView.findViewWithTag(start_index);
//                            ImageLoader.getInstance().displayImage(productInfoList.get(start_index).getImageUrl(), img, options, animateFirstListener);
//                        }
//                        break;
//
//                    default:
//                        break;
//                }

                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING ||
                        scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    if(isBottom && !isRefreshing && hasMoreData){
                        isRefreshing = true;
                        startId += nums;
                        initData(startId,nums,false);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 设置当前屏幕显示的起始index和结束index
//                start_index = firstVisibleItem;
//                end_index = firstVisibleItem + visibleItemCount;
//                if (end_index >= totalItemCount) {
//                    end_index = totalItemCount - 1;
//                }
                int end_index = firstVisibleItem + visibleItemCount;
                if(end_index > totalItemCount - 2){
                    isBottom = true;
                }else{
                    isBottom = false;
                }
            }
        }));
//        listView.setOnScrollListener();

        refreshView.setEnabled(true);
        refreshView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                /** 下拉刷新 */
                startId = 0;
                initData(startId,nums,true);
            }
        }, 1);
    }

        private void initData(int startId, final int nums,boolean isClear) {
            if(productInfoList == null){
                productInfoList = new ArrayList<>();
            }
            if(!productInfoList.isEmpty() && isClear){
                productInfoList.clear();
            }
            /** 网络请求数据：商品列表 */
            String url = "http://192.168.1.168/cycang_app/index.php?c=page&a=page";
            StringBuffer sb = new StringBuffer(url);
            sb.append("&");
            sb.append("start=");
            sb.append(startId);
            sb.append("&");
            sb.append("nums=");
            sb.append(nums);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(sb.toString(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("request", "respone==" + response.toString());
                            try{
                                int code = response.getInt("code");
                                if(code == 200){
                                    JSONObject result =response.getJSONObject("result");
                                    JSONArray data = result.getJSONArray("data");
                                    for (int i = 0; i< data.length();i++) {
                                        JSONObject product = data.getJSONObject(i);
                                        String name = product.getString("name");
                                        String price = product.getString("cover_price");
                                        String imageUrl = product.getString("figure");
                                        ProductInfo pro = new ProductInfo(name,price,imageUrl);
                                        productInfoList.add(pro);
                                    }
                                    if(productInfoList.size() == nums){
                                        hasMoreData = true;
                                    }else{
                                        hasMoreData = false;
                                    }
                                   mAdapter.notifyDataSetChanged();
                                   if(refreshView.isRefreshing()){
                                       refreshView.finishRefreshing();
                                   }
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                hasMoreData = false;
                                if(refreshView.isRefreshing()){
                                    refreshView.finishRefreshing();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("request","error=="+error.toString());
                    hasMoreData = false;
                    if(refreshView.isRefreshing()){
                        refreshView.finishRefreshing();
                    }
                }
            }
            );
            MyApplication.getInstance().addRequest(jsonObjectRequest, "request");
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AnimateFirstDisplayListener.displayedImages.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pull_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            super.onLoadingStarted(imageUri, view);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
//                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
