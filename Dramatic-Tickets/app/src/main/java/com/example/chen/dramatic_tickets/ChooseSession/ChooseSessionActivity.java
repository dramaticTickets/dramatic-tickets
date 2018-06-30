package com.example.chen.dramatic_tickets.ChooseSession;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.Adapter.MyViewPager;
import com.example.chen.dramatic_tickets.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChooseSessionActivity extends AppCompatActivity {

    private TabLayout tabLayout = null;
    private MyViewPager viewPager;
    private Fragment[] mFragmentArrays = new Fragment[3];
    private String[] mTabTitles = new String[3];
    private String MovieName, CinemaName;


    TabFragment jintian = TabFragment.newInstance("jintian");
    TabFragment mingtian =TabFragment.newInstance("mingtian");
    TabFragment houtian = TabFragment.newInstance("houtian");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_session);
        Bundle extras = ChooseSessionActivity.this.getIntent().getExtras();
        if(extras != null) {
            MovieName = extras.getString("MovieName");
            CinemaName = extras.getString("CinemaName");
        }
        TextView textView, textView3;
        textView = (TextView) findViewById(R.id.cinema_name);
        textView.setText(CinemaName);
        textView3 = (TextView) findViewById(R.id.cinema_address);
        if(CinemaName.equals("金逸珠江国际影城广州大学城店") ) {
            textView3.setText("番禺区小谷街道贝岗村中二横路1号GOGO新天地商业广场二期二楼");
        }
        if(CinemaName.equals("广东科学中心巨幕影院")) {
            textView3.setText("番禺区广州大学城西六路168号");
        }
        if(CinemaName.equals("广州万达影城万胜广场店")) {
            textView3.setText("海珠区新港东路1236号万胜广场五层'");
        }
        if(CinemaName.equals("星河影城番禺南村店")) {
            textView3.setText("番禺区番禺南村兴业大道之一人人佳购物广场2楼");
        }
        if(CinemaName.equals("广州hmv映联万和国际影城(南丰汇店)")) {
            textView.setText("海珠区新港东路618号南丰汇广场3楼");
        }

        TextView textView1, textView2;
        textView1 = (TextView) findViewById(R.id.movie_chinese_name);
        textView.setText(CinemaName);
        textView2 = (TextView) findViewById(R.id.star_rate);
        ImageView imageView = (ImageView) findViewById(R.id.movie_poster);

        if(MovieName.equals("头号玩家")) {
            textView1.setText("头号玩家");
            textView2.setText("9.5");
            imageView.setImageResource(R.mipmap.poster_small_player);
        }
        if(MovieName.equals("复仇者联盟")) {
            textView1.setText("复仇者联盟");
            textView2.setText("8.8");
            imageView.setImageResource(R.mipmap.poster_small_avengers);
        }
        if(MovieName.equals("死侍")) {
            textView1.setText("死侍2");
            textView2.setText("8.8");
            imageView.setImageResource(R.mipmap.poster_small_deadpool);
        }
        if(MovieName.equals("爵迹")) {
            textView1.setText("爵迹");
            textView2.setText("3.8");
            imageView.setImageResource(R.mipmap.poster_small_jueji);
        }



        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (MyViewPager) findViewById(R.id.tab_viewpager);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow));
        viewPager.setOffscreenPageLimit(3);
        initView();
    }

    private void initView() {

        //获得日期
        Date date = new Date();
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        Calendar hotian = Calendar.getInstance();
        hotian.add(Calendar.DATE, 2);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String todayD = sdf.format(today.getTime());
        String mingtianD = sdf.format(tomorrow.getTime());
        String houtianD = sdf.format(hotian.getTime());

        mTabTitles[0] = "今天"+todayD;
        mTabTitles[1] = "明天"+mingtianD;
        mTabTitles[2] = "后天"+houtianD;

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


