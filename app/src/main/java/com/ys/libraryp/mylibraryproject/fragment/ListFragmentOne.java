package com.ys.libraryp.mylibraryproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.adapter.MyAdapter;
import com.ys.libraryp.mylibraryproject.bean.ImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragmentOne extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ImageInfo> imageInfoList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragmentOne newInstance(String param1, String param2) {
        ListFragmentOne fragment = new ListFragmentOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragmentOne() {
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
        view = inflater.inflate(R.layout.fragment_list_one, container, false);
        initData();
        initView();
        return view;
    }

    private void initData() {
        imageInfoList = new ArrayList<>();
        ImageInfo imageInfo1 = new ImageInfo("一","第一张图",R.drawable.share_to_more_icon);
        imageInfoList.add(imageInfo1);
        ImageInfo imageInfo2 = new ImageInfo("二","第二张图",R.drawable.share_to_qq_friend_icon);
        imageInfoList.add(imageInfo2);
        ImageInfo imageInfo3 = new ImageInfo("三","第三张图",R.drawable.share_to_qzone_icon);
        imageInfoList.add(imageInfo3);
        ImageInfo imageInfo4 = new ImageInfo("四","第四张图",R.drawable.share_to_weibo_icon);
        imageInfoList.add(imageInfo4);
        ImageInfo imageInfo5 = new ImageInfo("五","第五张图",R.drawable.share_to_wx_circle_icon);
        imageInfoList.add(imageInfo5);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(imageInfoList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
