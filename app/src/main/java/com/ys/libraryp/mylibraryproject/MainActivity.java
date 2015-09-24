package com.ys.libraryp.mylibraryproject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ys.libraryp.mylibraryproject.ui.DrawerLayoutActivity;
import com.ys.libraryp.mylibraryproject.ui.HorizontalScrollviewActivity;
import com.ys.libraryp.mylibraryproject.ui.PullRefreshActivity;
import com.ys.libraryp.mylibraryproject.ui.ShareTABActivity;
import com.ys.libraryp.mylibraryproject.ui.SlideMenuActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private ArrayAdapter<String>   adapter;
    private String[] strings = {"A","B","C","D","E"};
    private String[] a = {"F","G","H"};
    private List<String> stringList;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            stringList.addAll(new ArrayList<>(Arrays.asList(a)));
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        listView = (ListView) findViewById(R.id.listView);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.black);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                handler.sendMessageDelayed(new Message(),3000);
                handler.sendEmptyMessageDelayed(0,3000);
            }
        });
        String s = getApplication().getResources().getString(R.string.time_error);
        Log.i("1",s);
        initData();
    }

    private void initData() {
        stringList = new ArrayList<>(Arrays.asList(strings));
        adapter = new ArrayAdapter<>(this,R.layout.adapter_item_text,R.id.tv_title,stringList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.menu_qq_friend:
                startActivity(new Intent(MainActivity.this,ShareTABActivity.class));
                break;
            case R.id.menu_qzone:
                startActivity(new Intent(MainActivity.this, HorizontalScrollviewActivity.class));
                break;
            case R.id.menu_weibo:
                startActivity(new Intent(MainActivity.this, DrawerLayoutActivity.class));
                break;
            case R.id.menu_wx_circle:
                startActivity(new Intent(MainActivity.this, PullRefreshActivity.class));
                break;
            case R.id.menu_wx_friend:
                startActivity(new Intent(MainActivity.this, SlideMenuActivity.class));
                break;
        }
//        return true;
        return super.onOptionsItemSelected(item);
    }
}
