package com.ys.libraryp.mylibraryproject.fragment;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.ys.libraryp.mylibraryproject.MyApplication;
import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.RecyclerViewOptions.DividerItemDecoration;
import com.ys.libraryp.mylibraryproject.RecyclerViewOptions.SpacesItemDecoration;
import com.ys.libraryp.mylibraryproject.adapter.WaterFallAdapter;
import com.ys.libraryp.mylibraryproject.bean.ImageInfo;
import com.ys.libraryp.mylibraryproject.bean.ProductInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragmentTwo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private WaterFallAdapter waterFallAdapter;
    private List<ProductInfo> productInfoList;
    private int startId = 0;
    private int nums = 20;
    //图片加载回调
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragmentTwo newInstance(String param1, String param2) {
        ListFragmentTwo fragment = new ListFragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_two, container, false);
        initData(startId, nums, true);
        initView();
        return view;
    }

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    boolean isRefreshing = false;
    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        waterFallAdapter = new WaterFallAdapter(productInfoList,animateFirstListener);
        recyclerView.setAdapter(waterFallAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10,2);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isPullUp = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isPullUp = dy > 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Log.i("request", "SCROLL_STATE_IDLE");
                    ImageLoader.getInstance().pause();
                }else{
                    ImageLoader.getInstance().resume();
                }

                if (isPullUp && !isRefreshing) {
                    Log.i("request", "isRefreshing");
//                    int lastPos = mLayoutManager.findLastVisibleItemPosition();
//                    if(lastPos > productInfoList.size() -2){

//                    int[] into = mGridLayoutManager.findLastVisibleItemPositions(null);
//                    if (into[0] > productInfoList.size() - 3) {//最后一个位置的时

                    int lastPro =  gridLayoutManager.findLastCompletelyVisibleItemPosition();
                    if (lastPro > productInfoList.size() - 2) {//最后一个位置的时

                        // 候加载更多
                        Log.i("request", "isRefreshing initData");
//                        mAdapter.setHasFooter(true);
                        isRefreshing = true;
                        startId += nums;
                        initData(startId, nums, false);
                    }
                }
            }
        });
    }

//    private void initData() {
//        imageInfoList = new ArrayList<>();
//        ImageInfo imageInfo1 = new ImageInfo("一","第一张图",R.drawable.share_to_more_icon);
//        imageInfoList.add(imageInfo1);
//        ImageInfo imageInfo2 = new ImageInfo("二","第二张图",R.drawable.share_to_qq_friend_icon);
//        imageInfoList.add(imageInfo2);
//        ImageInfo imageInfo3 = new ImageInfo("三","第三张图",R.drawable.share_to_qzone_icon);
//        imageInfoList.add(imageInfo3);
//        ImageInfo imageInfo4 = new ImageInfo("四","第四张图",R.drawable.share_to_weibo_icon);
//        imageInfoList.add(imageInfo4);
//        ImageInfo imageInfo5 = new ImageInfo("五","第五张图",R.drawable.share_to_wx_circle_icon);
//        imageInfoList.add(imageInfo5);
//    }

    private void initData(int startId,int nums,boolean isClear) {
        if(productInfoList == null){
            productInfoList = new ArrayList<>();
        }
        if(!productInfoList.isEmpty() && isClear){
            productInfoList.clear();
        }
        /** 网络请求数据：商品列表 */
        String url = "http://app.cycang.com/index.php?c=page&a=page"; //"http://192.168.1.168/cycang_app/index.php?c=page&a=page";
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
                                waterFallAdapter.notifyDataSetChanged();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();

                        }
                        isRefreshing = false;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("request","error=="+error.toString());
                isRefreshing = false;
            }
        }
        );
        MyApplication.getInstance().addRequest(jsonObjectRequest,"request");
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

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
