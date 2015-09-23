package com.ys.libraryp.mylibraryproject.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.libraryp.mylibraryproject.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View view;
    private SwipeRefreshLayout refreshLayout_one;
    private LinearLayout one_content;
    private WebView mWebView;
    public static final String MAIN_URL =  "http://app.cycang.com/index.php";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OneFragment newInstance(String param1, String param2) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OneFragment() {
        // Required empty public constructor
    }

    private MyHandler myHandler;
    static class MyHandler extends  Handler{
        WeakReference<OneFragment> fragmentWeakReference;
        public MyHandler(OneFragment fragment){
            fragmentWeakReference = new WeakReference<OneFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            OneFragment theFragment = fragmentWeakReference.get();
            if(theFragment == null) return;
            switch(msg.what){
                case 0:
                    theFragment.handleMessage_Zero(msg);
                    break;
                default:
                    break;
            }
        }
    }

    private void handleMessage_Zero(Message msg) {
        refreshLayout_one.setRefreshing(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHandler = new MyHandler(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mWebView != null) {
            Log.i("yueshan", "webView==== is not null");
            one_content.removeView(mWebView);
            mWebView.clearCache(false);
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }

    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    @Override
    public void onStart() {
        super.onStart();

        refreshLayout_one.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (mWebView.getScrollY() == 0)
                            refreshLayout_one.setEnabled(true);
                        else
                            refreshLayout_one.setEnabled(false);

                    }
                });
    }

    @Override
    public void onStop() {
        refreshLayout_one.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    /**
     *  初始化view
     */
    private TextView fragment_one;
    private void initView() {
        fragment_one = (TextView) view.findViewById(R.id.fragment_one);
        fragment_one.setText("fragmentOne");
        fragment_one.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));

        refreshLayout_one = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout_one);
        refreshLayout_one.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refreshLayout_one.setProgressBackgroundColorSchemeResource(android.R.color.black);
        refreshLayout_one.setOnRefreshListener(refreshListener);
        one_content = (LinearLayout) view.findViewById(R.id.one_content);
        initWebView();
        initData();
    }

    private void initData() {
        HashMap<String,String> header = new HashMap<>();
        header.put("Cache-Control","max-age=120");
        mWebView.loadUrl(MAIN_URL);
    }

    private void initWebView() {
        mWebView = new WebView(getActivity());
        mWebView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 设置无边框
//        isDestroy = false;
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        webSettings();
        one_content.addView(mWebView);
    }

    /**
     *  WebView 的配置
     */
    private void webSettings(){
        mWebView.getSettings().setJavaScriptEnabled(true); //加上这句话才能使用javascript方法
        mWebView.requestFocus();
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setSupportZoom(false);

        mWebView.getSettings().setUseWideViewPort(true);//关键点
        mWebView.getSettings().setLoadWithOverviewMode(true);

//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // web内容强制满屏

        //Cache开启和设置
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAppCachePath(getActivity().getApplicationContext().getCacheDir().getPath());
        if(Build.VERSION.SDK_INT < 18) {
            mWebView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        }
        //LocalStorage相关设置
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        String databasePath = mWebView.getContext().getDir("databases", Context.MODE_PRIVATE).getPath();
        mWebView.getSettings().setDatabasePath(databasePath);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.i("yueshan", "newProgress===" + newProgress);
                if (newProgress == 100) {
                    refreshLayout_one.setRefreshing(false);
                } else {
                    if(!refreshLayout_one.isRefreshing()){
                        refreshLayout_one.setRefreshing(true);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                Log.i("yueshan", "Bitmap===" + icon.getHeight());
                super.onReceivedIcon(view, icon);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.i("yueshan", "title===" + title);
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                Log.i("yueshan", "window===" + window);
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                Log.i("yueshan","onLoadResource=====url==="+url);
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                int height = -1;
                if(favicon != null){
                    height = favicon.getHeight();
                }
                Log.i("yueshan","onPageStarted===url"+url+"====favicon=="+height);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("yueshan","onPageFinished====url=="+url);
                super.onPageFinished(view, url);
               // myHandler.sendEmptyMessage(0);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("yueshan","onReceivedError==="+errorCode+"---"+description+"-----"+failingUrl);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                Log.i("yueshan","doUpdateVisitedHistory==url"+url+"----"+isReload);
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("yueshan","shouldOverrideUrlLoading---url"+url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    SwipeRefreshLayout.OnRefreshListener   refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mWebView.reload();
        }
    };

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

}
