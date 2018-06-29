package com.example.chen.dramatic_tickets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

import static com.example.chen.dramatic_tickets.SignUpActivity.dip2px;


public class BaseFragment extends Fragment {
    private ArrayList<Map<String,Object>> cinemaData= new ArrayList<Map<String,Object>>();
    private String userName="a", password="q", phoneNumber, nickNmae;
    String imagePath;

    private boolean loginSuccess = false;

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
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MovieListActivity.class);
                    intent.putExtra("movieName", query);
                    getActivity().startActivity(intent);
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
            //设置搜索框监听器
            SearchView searchview = (SearchView) view.findViewById(R.id.searchview);
            searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //点击搜索后，显示搜索结果
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), CinemaListActivity.class);
                    intent.putExtra("cinemaName", query);
                    getActivity().startActivity(intent);

                    return false;
                }

                //搜索内部改变回调，newText就是搜索框里的内容框
                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });

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
            view = inflater.inflate(R.layout.personal_info, null);
            android.support.constraint.ConstraintLayout loginR;
            if(!loginSuccess) {
                loginR = (android.support.constraint.ConstraintLayout) view.findViewById(R.id.modifyPersonInfo);
                loginR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), LoginOrRegister.class);

                        //此处调到登录注册界面
                        startActivityForResult(intent, 1);
                    }
                });
            } else {
                TextView mView;
                mView = (TextView) view.findViewById(R.id.userName);
                mView.setText(nickNmae);
                mView = (TextView) view.findViewById(R.id.pensonalSignature);
                mView.setText("账号:"+userName);
                mView.setVisibility(View.VISIBLE);

                Bitmap bitmap1 = readBitmap(getContext(), imagePath);
                bitmap1 = setImgSize(bitmap1,400,400);

                ImageView imageView;
                imageView = (ImageView) view.findViewById(R.id.headPortrait);
                imageView.setImageBitmap(bitmap1);
            }
            /*if(loginIn == false) {
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
            }*/
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
            switch (resultCode) {
                //这里是登陆回跳
                case 1:{
                    userName = intent.getStringExtra("usrName").toString();
                    //此处需要数据库传回来的值处理
                    //imagePath = intent.getStringExtra("imagePath").toString();
                    //phoneNumber = intent.getStringExtra("phoneNumber").toString();
                    //nickNmae = intent.getStringExtra("nickNmae").toString();

                    Log.i("ADD","111");

                    TextView mView;
                    mView = (TextView) getView().findViewById(R.id.userName);
                    mView.setText(userName);
                    //mView = (TextView) getView().findViewById(R.id.pensonalSignature);
                   // mView.setText("账号："+userName);
                    //mView.setVisibility(View.VISIBLE);

                    loginSuccess = true;
                    break;
                }


                //这里是注册回跳
                case 2: {
                    userName = intent.getStringExtra("usrName").toString();
                    //此处需要数据库传回来的值处理
                    imagePath = intent.getStringExtra("imagePath").toString();
                    phoneNumber = intent.getStringExtra("phoneNumber").toString();
                    nickNmae = intent.getStringExtra("nickNmae").toString();

                    TextView mView;
                    mView = (TextView) getView().findViewById(R.id.userName);
                    mView.setText(nickNmae);
                    mView = (TextView) getView().findViewById(R.id.pensonalSignature);
                    mView.setText("账号:"+userName);
                    mView.setVisibility(View.VISIBLE);

                    Bitmap bitmap1 = readBitmap(getContext(), imagePath);
                    bitmap1 = setImgSize(bitmap1,400,400);

                    ImageView imageView;
                    imageView = (ImageView) getView().findViewById(R.id.headPortrait);
                    imageView.setImageBitmap(bitmap1);

                    loginSuccess = true;
                    break;
                }
            }
        }
    }

    public Bitmap setImgSize(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 从指定路径读取图片文件，并进行缩放
     * @param context
     * @param imgPath
     * @return
     */
    private static Bitmap readBitmap(Context context, String imgPath) {

        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 如果设置为true,仅仅返回图片的宽和高,宽和高是赋值给opts.outWidth,opts.outHeight;
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgPath, opts);
            // 得到原图的宽和高
            int srcWidth = opts.outWidth;
            int srHeigth = opts.outHeight;

            //获取缩放的比例(我这里设置的是80dp)
            int scale = 1;
            int sx = srcWidth/dip2px(context, 80); // 3000/320= 9;
            int sy = srHeigth/dip2px(context, 80); // 2262/480= 5;
            Log.i("ADD", sx+"    "+ sy);
            if(sx > sy && sx > 1){
                scale = sx;
            }
            if(sy > sx && sy > 1){
                scale = sy;
            }

            // 设置为false表示,让decodeFile返回一个bitmap类型的对象
            opts.inJustDecodeBounds = false;
            // 设置缩放的比例,设置后,decodeFile方法就会按照这个比例缩放图片,加载到内存
            opts.inSampleSize= scale;
            // 缩放图片加载到内存(一般加载图片到内存，都要根据手机屏幕宽高对图片进行适当缩放，否则可能出现OOM异常)
            Bitmap bitmap =  BitmapFactory.decodeFile(imgPath, opts);

            return bitmap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
