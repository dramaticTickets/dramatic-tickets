package com.example.chen.dramatic_tickets.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.R;

public class LoginOrRegister extends AppCompatActivity {
    ImageView mImage;
    String stringUserName, stringPhoneNumber, imagePath, nickNmae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_not_login);

        Button login, register;
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginOrRegister.this, SignInActivity.class);

                startActivityForResult(intent, 3);
            }
        });

        //跳转注册界面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginOrRegister.this, SignUpActivity.class);
                startActivityForResult(intent, 4);
            }
        });

        /*
        if(loginIn == false) {

        } else {
            //登陆成功
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
        }/*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {

            //这里是去登录回跳
            case 3: {
                switch (resultCode) {
                    case 1: {
                        Toast.makeText(LoginOrRegister.this, "登录失败", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2: {
                        stringUserName = intent.getStringExtra("usrName").toString();
                        //此处需要数据库传回来的值处理
                        stringPhoneNumber = intent.getStringExtra("phoneNumber").toString();
                        imagePath = intent.getStringExtra("imagePath").toString();
                        nickNmae = intent.getStringExtra("nickName").toString();

                        Toast.makeText(LoginOrRegister.this, "登陆成功", Toast.LENGTH_SHORT).show();

                        Intent backIntent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putString("usrName", stringUserName);
                        bundle.putString("phoneNumber", stringPhoneNumber);

                        Log.i("ADD1",stringPhoneNumber);
                        bundle.putString("imagePath", imagePath);
                        bundle.putString("nickNmae", nickNmae);

                        backIntent.putExtras(bundle);

                        LoginOrRegister.this.setResult(1, backIntent);
                        LoginOrRegister.this.finish();
                        break;
                    }
                }
                break;
            }

            //这里是去注册回跳
            case 4: {
                switch (resultCode) {
                    case 1: {
                        Toast.makeText(LoginOrRegister.this, "注册成功", Toast.LENGTH_SHORT).show();
                        stringUserName = intent.getStringExtra("usrName").toString();
                        //此处需要数据库传回来的值处理
                        stringPhoneNumber = intent.getStringExtra("phoneNumber").toString();
                        imagePath = intent.getStringExtra("imagePath").toString();
                        nickNmae = intent.getStringExtra("nickName").toString();

                        Intent backIntent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putString("usrName", stringUserName);
                        bundle.putString("phoneNumber", stringPhoneNumber);
                        bundle.putString("imagePath", imagePath);
                        bundle.putString("nickNmae", nickNmae);

                        backIntent.putExtras(bundle);

                        LoginOrRegister.this.setResult(2, backIntent);
                        LoginOrRegister.this.finish();
                        break;
                    }

                }
            }
            break;

        }





        /*final ProgressDialog dialog = ProgressDialog.show(getActivity(), null, "正在加载中。。。");
                        //停1秒
                        new Thread(){
                            public void run(){
                                SystemClock.sleep(1000);
                                dialog.dismiss();
                            }
                        }.start();*/
    }
}