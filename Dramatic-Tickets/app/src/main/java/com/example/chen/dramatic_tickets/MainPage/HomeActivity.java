package com.example.chen.dramatic_tickets.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.chen.dramatic_tickets.Adapter.BottomNavigationViewHelper;
import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.Adapter.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    BaseFragment shouye = BaseFragment.newInstance("首页");
    BaseFragment yingyuan =BaseFragment.newInstance("影院");
    BaseFragment wode = BaseFragment.newInstance("我的");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*
        addFragment(shouye,"shouye");
        addFragment(yingyuan,"yingyuan");
        addFragment(wode,"wode");
        */
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //默认的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_cinema:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_mine:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(shouye);
        adapter.addFragment(yingyuan);
        adapter.addFragment(wode);
        viewPager.setAdapter(adapter);
    }

    //给fragment加标签
    private void addFragment(BaseFragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.viewpager, fragment, tag);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //Fragment fragment = getSupportFragmentManager().findFragmentByTag(BaseFragment.newInstance("影院"));
    }

    @Override
    public void onResume() {
        super.onResume();
        //adapter.notifyAll();
        //HomeActivity.this.reLoadFragView();
    }
}