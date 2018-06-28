package com.example.chen.dramatic_tickets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class BaseFragment extends Fragment {
    private ArrayList<Map<String,Object>> cinemaData= new ArrayList<Map<String,Object>>();
    private String userName="a", password="q", confirmPassword, phoneNumber;

    private boolean loginIn = false;

    private int[] showingMovieSmallPosters, notShownMovieSmallPosters;
    private String[] showingMovieName,notShowMovieName;

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
            final LinearLayout mGallery1,mGallery2;//1，2分别为热映电影和即将上映的展示框


            view = inflater.inflate(R.layout.fragment_home, null);
            //电影海报轮播器
            MyRollPagerView mRollViewPager = (MyRollPagerView) view.findViewById(R.id.roll_view_pager);
            //设置海报轮播播放时间间隔
            mRollViewPager.setPlayDelay(3000);
            //设置海报轮播透明度
            mRollViewPager.setAnimationDurtion(500);
            //设置海报轮播适配器
            TestNormalAdapter myAdapter = new TestNormalAdapter(mRollViewPager);
            mRollViewPager.setAdapter(myAdapter);


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
                    R.mipmap.poster_small_player,
                    R.mipmap.poster_small_avengers,
                    R.mipmap.poster_small_deadpool,
                    R.mipmap.poster_small_jueji
            };

            showingMovieName = new String[] {//此处对应上面的海报
                    "player", "avengers", "deadpool", "jueji"
            };

            for (int i = 0; i < showingMovieSmallPosters.length; i++) {
                View smallView = inflater.inflate(R.layout.gallery_item, mGallery1, false);
                ImageView img = (ImageView) smallView.findViewById(R.id.movie_poster);
                img.setImageResource(showingMovieSmallPosters[i]);
                Button btn = (Button) smallView.findViewById(R.id.buyTicket);

                final int finalI = i;
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //此处跳到电影详情
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), MovieDetail.class);

                        String selectedMovieName = showingMovieName[finalI];
                        intent.putExtra("selectedMovieName", selectedMovieName);
                        getActivity().startActivity(intent);

                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //此处跳到选择电影详情
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), ChooseCinema.class);
                        intent.putExtra("MovieName", showingMovieName[finalI]);
                        getActivity().startActivity(intent);
                    }
                });
                mGallery1.addView(smallView);
            }

            //设置“即将上映”滑动浏览框
            mGallery2 = (LinearLayout) view.findViewById(R.id.gallery_id2);
            notShownMovieSmallPosters = new int[] {//获取电影海报图片
                    R.mipmap.poster_small_jail,
                    R.mipmap.poster_small_island,
                    R.mipmap.poster_small_super,
                    R.mipmap.poster_small_dino
            };

            notShowMovieName = new String[] {//此处对应上面的海报
                    "jail", "island", "super", "dino"
            };

            for (int i = 0; i < notShownMovieSmallPosters.length; i++) {
                View smallView = inflater.inflate(R.layout.gallery_no_button, mGallery2, false);
                ImageView img = (ImageView) smallView.findViewById(R.id.movie_poster);
                img.setImageResource(notShownMovieSmallPosters[i]);

                final int finalI = i;
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), notShowMovieDetail.class);

                        String selectedMovieName = notShowMovieName[finalI];
                        intent.putExtra("selectedMovieName", selectedMovieName);
                        getActivity().startActivity(intent);
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
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ChooseMovie.class);

                    String selectCinemaName = cinemaData.get(position).get("name").toString();
                    intent.putExtra("CinemaName", selectCinemaName);
                    getActivity().startActivity(intent);
                    Toast.makeText(getActivity(), "您点击了影院"+selectCinemaName, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //此处应检验是否登录
            //若为登录则显示一下界面：
            if(loginIn == false) {
                view = inflater.inflate(R.layout.fragment_not_login, null);
                Button login, register;
                login = (Button) view.findViewById(R.id.login);
                register = (Button) view.findViewById(R.id.register);

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SignInActivity.class);

                        //此处传入数据作检测用，实际应用数据库的账号密码
                        intent.putExtra("usrNameLogin", userName);
                        intent.putExtra("passwordLogin", password);
                        startActivityForResult(intent, 2);
                    }
                });

                //跳转注册界面
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SignUpActivity.class);
                        startActivityForResult(intent, 1);
                    }
                });
            } else {
                //登陆成功
                view = inflater.inflate(R.layout.user_center, null);
                TextView mView;


                //getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentByTag("我的")).commit();

                mView = (TextView) view.findViewById(R.id.userName);
                mView.setText(userName);
                //取消登录
                Button loginOut;
                loginOut = (Button) view.findViewById(R.id.loginOut);
                loginOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginIn = false;
                    }
                });
            }
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
                R.mipmap.poster_player,
                R.mipmap.poster_avenge,
                R.mipmap.poster_deadpool,
                R.mipmap.poster_jueji
        };

        public TestNormalAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            final int nowPosition = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MovieDetail.class);

                    String selectedMovieName = showingMovieName[nowPosition];
                    intent.putExtra("selectedMovieName", selectedMovieName);
                    getActivity().startActivity(intent);
                }
            });
            return view;
        }


        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if(resultCode == 1) {
                userName = intent.getStringExtra("usrName").toString();
                password = intent.getStringExtra("password").toString();
                confirmPassword = intent.getStringExtra("confirmPassword").toString();
                phoneNumber = intent.getStringExtra("phoneNumber").toString();
            }
        }

        if (requestCode == 2) {
            if(resultCode == 2) {
                Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();

                loginIn =true;
                TextView mView;
                mView = (TextView) getView().findViewById(R.id.text1);
                mView.setText("!!!!!");
                LayoutInflater inflate = getActivity().getLayoutInflater();
                View view = null;
                view = inflate.inflate(R.layout.user_center, null);

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), null, "正在加载中。。。");
                //停1秒
                new Thread(){
                    public void run(){
                        SystemClock.sleep(1000);
                        dialog.dismiss();
                    }
                }.start();

                onResume();
                /*LayoutInflater inflate = getActivity().getLayoutInflater();
                View view = null;
                view = inflate.inflate(R.layout.user_center, null);*/
            } else {
                Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        LayoutInflater inflater =(LayoutInflater) getActivity().getLayoutInflater();
        String tag = getArguments().getString("info");
        View view = null;
        if(tag == "我的") {
            //此处应检验是否登录
            //若为登录则显示一下界面：
            if (loginIn == false) {
                Toast.makeText(getActivity(), "1111", Toast.LENGTH_SHORT).show();
                view = inflater.inflate(R.layout.fragment_not_login, null);
                Button login, register;
                login = (Button) view.findViewById(R.id.login);
                register = (Button) view.findViewById(R.id.register);

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SignInActivity.class);

                        //此处传入数据作检测用，实际应用数据库的账号密码
                        intent.putExtra("usrNameLogin", userName);
                        intent.putExtra("passwordLogin", password);
                        startActivityForResult(intent, 2);
                    }
                });

                //跳转注册界面
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SignUpActivity.class);
                        startActivityForResult(intent, 1);
                    }
                });
            } else {

                Toast.makeText(getActivity(), "22222", Toast.LENGTH_SHORT).show();
                //登陆成功
                view = inflater.inflate(R.layout.user_center, null);
                TextView mView;
                mView = (TextView) view.findViewById(R.id.userName);
                mView.setText(userName);
                //取消登录
                Button loginOut;
                loginOut = (Button) view.findViewById(R.id.loginOut);
                loginOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginIn = false;
                    }
                });
            }
        }*/
    }
}
