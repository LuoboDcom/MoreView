package com.ys.libraryp.mylibraryproject.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.ys.libraryp.mylibraryproject.MyApplication;
import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.adapter.ProductListAdapter;
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
 * Activities that contain this fragment must implement the
 * {@link ListFragmentThree.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragmentThree extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<ProductInfo> productInfoList;
    private int startId = 0;
    private int nums = 20;

    //图片加载回调
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragmentThree.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragmentThree newInstance(String param1, String param2) {
        ListFragmentThree fragment = new ListFragmentThree();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragmentThree() {
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
        view = inflater.inflate(R.layout.fragment_list_three, container, false);
        initData(startId, nums, true);
        initView();
        return view;
    }

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mrefreshLayout);
        refreshLayout.setColorSchemeResources(R.color.holo_blue_light, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_red_light);
        refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.black);
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /** 下拉刷新 */
                startId = 0;
                initData(startId, nums, true);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView);
        mRecyclerView.setHasFixedSize(true);//可以提高效率
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new ProductListAdapter<>(productInfoList,animateFirstListener);
        mAdapter.setHasMoreData(true);//一开始设置有更多数据
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isPullUp = false;
            boolean isRefreshing = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isPullUp = dy > 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.i("request", "SCROLL_STATE_IDLE");
                }

                if (isPullUp && !isRefreshing && mAdapter.isHasMoreData()) {
                    Log.i("request", "isRefreshing");
//                    int lastPos = mLayoutManager.findLastVisibleItemPosition();
//                    if(lastPos > productInfoList.size() -2){

//                    int[] into = mGridLayoutManager.findLastVisibleItemPositions(null);
//                    if (into[0] > productInfoList.size() - 3) {//最后一个位置的时

                   int lastPro =  gridLayoutManager.findLastVisibleItemPosition();
                    if (lastPro > productInfoList.size() - 3) {//最后一个位置的时

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

    private void initData(int startId,int nums,boolean isClear) {
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
                                if(productInfoList.size() == 20 ){
                                    Log.i("request", "respone== 200000000" );
                                    mAdapter.setHasMoreDataAndFooter(true, true);
                                }else{
                                    Log.i("request", "respone < 200000000" );
                                    mAdapter.setHasMoreDataAndFooter(false,true);
                                }
                                if(refreshLayout.isRefreshing()){
                                    refreshLayout.setRefreshing(false);
                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            if(refreshLayout.isRefreshing()){
                                refreshLayout.setRefreshing(false);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("request","error=="+error.toString());
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
        }
        );
        MyApplication.getInstance().addRequest(jsonObjectRequest,"request");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AnimateFirstDisplayListener.displayedImages.clear();
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
