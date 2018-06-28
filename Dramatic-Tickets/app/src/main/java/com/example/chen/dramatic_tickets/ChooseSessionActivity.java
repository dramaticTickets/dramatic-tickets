package com.example.chen.dramatic_tickets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class ChooseSessionActivity extends AppCompatActivity {

    private TabLayout tabLayout = null;
    private MyViewPager viewPager;
    private Fragment[] mFragmentArrays = new Fragment[3];
    private String[] mTabTitles = new String[3];

    TabFragment jintian = TabFragment.newInstance("jintian");
    TabFragment mingtian =TabFragment.newInstance("mingtian");
    TabFragment houtian = TabFragment.newInstance("houtian");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_session);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (MyViewPager) findViewById(R.id.tab_viewpager);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow));
        viewPager.setOffscreenPageLimit(3);
        initView();
    }

    private void initView() {
        mTabTitles[0] = "今天06-26";
        mTabTitles[1] = "明天06-27";
        mTabTitles[2] = "后天06-28";

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = jintian;
        mFragmentArrays[1] = mingtian;
        mFragmentArrays[2] = houtian;
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }

        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}


