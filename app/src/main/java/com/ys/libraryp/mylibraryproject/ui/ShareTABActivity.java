package com.ys.libraryp.mylibraryproject.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.libraryp.mylibraryproject.R;
import com.ys.libraryp.mylibraryproject.fragment.OneFragment;
import com.ys.libraryp.mylibraryproject.fragment.ThreeFragment;
import com.ys.libraryp.mylibraryproject.fragment.TwoFragment;
import com.ys.libraryp.mylibraryproject.view.NoScrollViewPager.MyViewPager;


public class ShareTABActivity extends AppCompatActivity implements  View.OnClickListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    MyViewPager mViewPager;
    View line;
    TextView tab1,tab2,tab3;
    /**
     *  get window size
     */
    private DisplayMetrics outMetrics;
    private float widthDP;
    private int nowItem = 1;//当前显示的fragment
    private List<Fragment> fragmentList;
    private Fragment oneFragment;
    private Fragment twoFragment;
    private Fragment threeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_tab);
        line = findViewById(R.id.line);
        initTab();
        initView();
        initViewPager();

    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        oneFragment = new OneFragment();
        fragmentList.add(oneFragment);
        twoFragment = new TwoFragment();
        fragmentList.add(twoFragment);
        threeFragment = new ThreeFragment();
        fragmentList.add(threeFragment);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (MyViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private boolean leftOrRight = false;
            private boolean isFirst = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("yueshan", "positionOffset==" + positionOffset + ";===" + positionOffsetPixels);
                if (positionOffsetPixels != 0) {
                    if (isFirst) {
                        isFirst = false;
                        if (positionOffsetPixels > 100) {//向左滑
                            leftOrRight = false;
                        } else {//向右滑
                            leftOrRight = true;
                        }
                    }
                    translateTab(positionOffsetPixels / 3,leftOrRight);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("yueshan", "position===" + position);
//                Toast.makeText(ShareTABActivity.this, "当前fragment====" + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0 && flag) {
                    flag = false;
                    from = 0;
                    nowItem = mViewPager.getCurrentItem() + 1;
                } else {
                    if (state == 1) {
                        isFirst = true;
                    }
                    flag = true;
                }
            }
        });
    }

    private boolean flag = false;

    private void initView() {
        tab1 = (TextView) findViewById(R.id.tab1);
        tab1.setOnClickListener(this);
        tab2 = (TextView) findViewById(R.id.tab2);
        tab2.setOnClickListener(this);
        tab3 = (TextView) findViewById(R.id.tab3);
        tab3.setOnClickListener(this);
    }

    /**
     *  初始化tab
     */
    private void initTab() {
        outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPX = outMetrics.widthPixels;
        float den = outMetrics.density;
        widthDP = widthPX/3;
        Log.i("yueshan","widthDP ==" + widthDP+";;widthPX==="+widthPX+";;;den=="+den);
        line.setLayoutParams(new LinearLayout.LayoutParams((int) widthDP, 3));
    }

    float from = 0;
    private void translateTab(float offset,boolean lorR){
        float fromX = 0;
        float toX = 0;
        if(lorR){
            fromX = widthDP*(nowItem-1)+from;
            toX =  widthDP*(nowItem-1)+offset;
        }else{
            if(from == 0){
                from = offset;
            }
            fromX = widthDP*(nowItem-2)+from;
            toX =  widthDP*(nowItem-2)+offset;
        }
        from = offset;
        TranslateAnimation  animation = new TranslateAnimation(fromX,toX,0,0);
        animation.setDuration(100);
        animation.setFillAfter(true);
        line.startAnimation(animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_tab, menu);
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
            mViewPager.setIsCanScroll(!mViewPager.getIsCanScroll());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab1:
                nowItem = 1;
                mViewPager.setCurrentItem(nowItem -1 );
                break;
            case R.id.tab2:
                nowItem = 2;
                mViewPager.setCurrentItem(nowItem - 1);
                break;
            case R.id.tab3:
                nowItem = 3;
                mViewPager.setCurrentItem(nowItem - 1);
                break;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static int sectionNumber = 1;
        private TextView section_label;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle bundle =getArguments();
            View rootView = inflater.inflate(R.layout.fragment_share_tab, container, false);
            section_label = (TextView) rootView.findViewById(R.id.section_label);
            section_label.setText(ARG_SECTION_NUMBER+bundle.getInt(ARG_SECTION_NUMBER));
            return rootView;
        }
    }

}
