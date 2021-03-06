package com.ys.libraryp.mylibraryproject.ui;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.adapter.MyAdapter;
import com.ys.libraryp.mylibraryproject.bean.ImageInfo;
import com.ys.libraryp.mylibraryproject.fragment.ListFragmentOne;
import com.ys.libraryp.mylibraryproject.fragment.ListFragmentThree;
import com.ys.libraryp.mylibraryproject.fragment.ListFragmentTwo;

import java.util.ArrayList;
import java.util.List;

/**
 *   RecyclerView
 */
public class SlideMenuActivity extends ActionBarActivity {

    private FragmentTabHost mTabHost;
    private View indicator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realTabContent);

        //添加tab名称和图标
        indicator = getIndicatorView("一");
        mTabHost.addTab(mTabHost.newTabSpec("one").setIndicator(indicator), ListFragmentOne.class, null);
        indicator = getIndicatorView("二");
        mTabHost.addTab(mTabHost.newTabSpec("two").setIndicator(indicator), ListFragmentTwo.class,null);
        indicator = getIndicatorView("三");
        mTabHost.addTab(mTabHost.newTabSpec("three").setIndicator(indicator), ListFragmentThree.class,null);
    }

    private View getIndicatorView(String name){
        TextView tv = new TextView(this);
        tv.setPadding(20,20,20,20);
        tv.setText(name);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_slide_menu, menu);
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
