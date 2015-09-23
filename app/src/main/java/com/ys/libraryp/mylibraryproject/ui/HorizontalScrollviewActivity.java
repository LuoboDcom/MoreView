package com.ys.libraryp.mylibraryproject.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.view.FlowingView.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class HorizontalScrollviewActivity extends ActionBarActivity {

    private GridView  mGridView;
    private LinearLayout content;
    private FlowLayout flowLayout;
    private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置", "语音", "天气", "浏览器", "视频" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scrollview);
//        initView();
        initAddView();
        initFlowView();
    }

    private void initFlowView() {
        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        flowLayout.setHorizontalSpacing(10);
        flowLayout.setVerticalSpacing(20);
        for (int i = 0; i < iconName.length; i++) {
            String s = iconName[i];
            LinearLayout ll = new LinearLayout(this);
            ll.setPadding(5, 5, 5, 5);
            TextView tv = new TextView(this);
            tv.setText(s);
            tv.setTextColor(getResources().getColor(R.color.holo_blue_dark));
            tv.setBackgroundResource(R.drawable.tv_bg);
            tv.setTextSize(14);
            tv.setPadding(5, 5, 5, 5);
            tv.setSingleLine();
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    v.setBackgroundResource(R.color.lighter_gray);
                }
            });
            flowLayout.addView(tv, i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void initAddView() {
        content = (LinearLayout) findViewById(R.id.title_content);
        for (int i = 0; i < iconName.length; i++) {
            String s = iconName[i];
            LinearLayout ll = new LinearLayout(this);
            ll.setPadding(5, 5, 5, 5);
            TextView tv = new TextView(this);
            tv.setText(s);
            tv.setTextColor(getResources().getColor(R.color.holo_blue_dark));
            tv.setBackgroundResource(R.drawable.tv_bg);
            tv.setTextSize(14);
            tv.setPadding(5, 5, 5, 5);
            tv.setSingleLine();
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    v.setBackgroundResource(R.color.lighter_gray);
                }
            });
            ll.addView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            content.addView(ll, i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void initView() {
//        mGridView = (GridView) findViewById(R.id.gridview);
//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,Arrays.asList(iconName));
//        mGridView.setNumColumns(iconName.length);
//        mGridView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_horizontal_scrollview, menu);
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
}
