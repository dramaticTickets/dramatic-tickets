package com.example.chen.dramatic_tickets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BaseFragment extends Fragment {
    private ArrayList<Map<String,Object>> cinemaData= new ArrayList<Map<String,Object>>();

    public static BaseFragment newInstance(String info) {
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String tag = getArguments().getString("info");
        View view = null;
        if (tag == "首页") {
            LinearLayout mGallery1,mGallery2;//1，2分别为热映电影和即将上映的展示框
            int[] showingMovieSmallPosters, notShownMovieSmallPosters;


            view = inflater.inflate(R.layout.fragment_home, null);
            //电影海报轮播器
            MyRollPagerView mRollViewPager = (MyRollPagerView) view.findViewById(R.id.roll_view_pager);
            //设置海报轮播播放时间间隔
            mRollViewPager.setPlayDelay(3000);
            //设置海报轮播透明度
            mRollViewPager.setAnimationDurtion(500);
            //设置海报轮播适配器
            mRollViewPager.setAdapter(new TestNormalAdapter(mRollViewPager));

            //设置搜索框监听器
            SearchView searchview = (SearchView) view.findViewById(R.id.searchview);
            searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //点击搜索后，显示搜索结果
                    return false;
                }

                //搜索内部改变回调，newText就是搜索框里的内容框
                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });

            //设置“热映电影”滑动浏览框
            mGallery1 = (LinearLayout) view.findViewById(R.id.gallery_id);
            showingMovieSmallPosters = new int[] {//获取电影海报图片
                    R.drawable.poster_small_player,
                    R.drawable.poster_small_avengers,
                    R.drawable.poster_small_deadpool,
                    R.drawable.poster_small_jueji
            };
            for (int i = 0; i < showingMovieSmallPosters.length; i++) {
                View smallView = inflater.inflate(R.layout.gallery_item, mGallery1, false);
                ImageView img = (ImageView) smallView.findViewById(R.id.movie_poster);
                img.setImageResource(showingMovieSmallPosters[i]);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "点击跳转电影详情", Toast.LENGTH_SHORT).show();
                    }
                });
                mGallery1.addView(smallView);
            }

            //设置“即将上映”滑动浏览框
            mGallery2 = (LinearLayout) view.findViewById(R.id.gallery_id2);
            notShownMovieSmallPosters = new int[] {//获取电影海报图片
                    R.drawable.poster_small_jail,
                    R.drawable.poster_small_island,
                    R.drawable.poster_small_super,
                    R.drawable.poster_small_dino
            };
            for (int i = 0; i < notShownMovieSmallPosters.length; i++) {
                View smallView = inflater.inflate(R.layout.gallery_no_button, mGallery2, false);
                ImageView img = (ImageView) smallView.findViewById(R.id.movie_poster);
                img.setImageResource(notShownMovieSmallPosters[i]);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "点击跳转电影详情", Toast.LENGTH_SHORT).show();
                    }
                });
                mGallery2.addView(smallView);
            }

        } else if (tag == "影院") {
            view = inflater.inflate(R.layout.fragment_cinema, null);

            final ListView cinemaList = (ListView) view.findViewById(R.id.cinemaList);
            initCinema();
            cinemaList.setAdapter(new SimpleAdapter(getActivity(),cinemaData,R.layout.cinema_item,
                    new String[]{"name","cinemaAddress","minPrice"},new int[]{R.id.name,R.id.cinemaAddress,R.id.minPrice}));
            cinemaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), "您点击了影院"+(position+1), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //此处应检验是否登录
            //若为登录则显示一下界面：
            view = inflater.inflate(R.layout.fragment_not_login, null);
            Button login,register;
            login = (Button) view.findViewById(R.id.login);
            register = (Button) view.findViewById(R.id.register);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "点击跳转登录界面", Toast.LENGTH_SHORT).show();
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "点击跳转注册界面", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }

    private void initCinema() {//初始化影院信息
        Map<String,Object> item = new HashMap<String,Object>();
        Map<String,Object> item2 = new HashMap<String,Object>();
        Map<String,Object> item3 = new HashMap<String,Object>();
        Map<String,Object> item4 = new HashMap<String,Object>();
        Map<String,Object> item5 = new HashMap<String,Object>();
        item.put("name", "金逸珠江国际影城广州大学城店");
        item.put("cinemaAddress", "番禺区小谷街道贝岗村中二横路1号GOGO新天地商业广场二期二楼");
        item.put("minPrice","34元");
        cinemaData.add(item);

        item2.put("name", "广东科学中心巨幕影院");
        item2.put("cinemaAddress", "番禺区广州大学城西六路168号");
        item2.put("minPrice","29.9元");
        cinemaData.add(item2);

        item3.put("name", "广州万达影城万胜广场店");
        item3.put("cinemaAddress", "海珠区新港东路1236号万胜广场五层");
        item3.put("minPrice","24.9元");
        cinemaData.add(item3);

        item4.put("name", "星河影城番禺南村店");
        item4.put("cinemaAddress", "番禺区番禺南村兴业大道之一人人佳购物广场2楼");
        item4.put("minPrice","32.9元");
        cinemaData.add(item4);

        item5.put("name", "广州hmv映联万和国际影城(南丰汇店)");
        item5.put("cinemaAddress", "海珠区新港东路618号南丰汇广场3楼");
        item5.put("minPrice","29.9元");
        cinemaData.add(item5);
    }

    private class TestNormalAdapter extends LoopPagerAdapter {
        private int[] imgs = {//顶部轮播海报图片
                R.drawable.poster_player,
                R.drawable.poster_avenge,
                R.drawable.poster_deadpool,
                R.drawable.poster_jueji
        };

        public TestNormalAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }

}
