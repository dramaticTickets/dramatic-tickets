package com.example.chen.dramatic_tickets.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.User;
import com.example.chen.dramatic_tickets.service.DramaticService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignInActivity extends AppCompatActivity {
    ImageView mImage;
    String userNameLogin = "a", passwordLogin = "q";
    private boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*测试是否检测成功
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            userNameLogin = extras.getString("usrNameLogin");
            passwordLogin = extras.getString("passwordLogin");
        }*/

        mImage= (ImageView) findViewById(R.id.pic2);

        Button login = (Button) findViewById(R.id.login);
        clickLogin(login);
    }

    //此函数用于检验注册是否合法，然后再传给数据库
    public void clickLogin(Button login) {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resultcode = 0;

                TextInputLayout userName, password;
                userName = (TextInputLayout) findViewById(R.id.userName);
                password = (TextInputLayout) findViewById(R.id.password);
                EditText edit1, edit2, edit3, edit4;
                edit1 = userName.getEditText();
                edit2 = password.getEditText();

                String stringUserName, stringPassword, stringConfirmPassword, stringPhoneNumber;
                stringUserName = edit1.getText().toString();
                stringPassword = edit2.getText().toString();

                //检验数据合法
                if (stringUserName.isEmpty()) {
                    userName.setErrorEnabled(true);
                    userName.setError("账号不能为空");
                } else if(!checkUsername(stringUserName)) {
                    //检验是否是字母开头,上面括号里调用了后面的正则表达式判断函数
                    userName.setErrorEnabled(true);
                    userName.setError("账号必须是由字母和数字组成或电话号码。");
                }else {
                    userName.setErrorEnabled(false);
                    if (stringPassword.isEmpty()) {
                        password.setErrorEnabled(true);
                        password.setError("密码不能为空");
                    } else {
                        password.setErrorEnabled(false);
                        resultcode = 1;
                    }
                }

                //此处完成了检测，暂时使用上一个传来的数据。实际应传数据到数据库并判断是否登录成功。
                if (resultcode == 1) {


                    //login(stringUserName, stringPassword);

                    /*final ProgressDialog dialog = ProgressDialog.show(SignInActivity.this, null, "正在加载中。。。");
                    //停1秒
                    new Thread(){
                        public void run(){
                            SystemClock.sleep(5000);
                            dialog.dismiss();
                        }
                    }.start();*/


                    //Log.i("ADD", ""+success);

                    /*if(login(stringUserName, stringPassword)) {
                        resultcode = 2;
                    }
                    /*Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putString("usrName", stringUserName);
                    intent.putExtras(bundle);
                    //此处需要数据库传回来的值处理
                    //bundle.putString("phoneNumber", stringPhoneNumber);
                    //bundle.putString("imagePath", imagePath);
                    SignInActivity.this.setResult(resultcode, intent);
                    SignInActivity.this.finish();*/

                    String phone = "";
                    String account = "";
                    if (isPhone(stringUserName)) {
                        phone = stringUserName;
                    } else {
                        account = stringUserName;
                    }

                    DramaticService service = ServiceFactory.createService(DramaticService.class);
                    Map<String, Object> temp = new HashMap<>();
                    temp.put("account", account);
                    temp.put("password", stringPassword);
                    temp.put("phone", phone);
                    service.login(temp)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<User>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (e instanceof HttpException) {             //HTTP错误
                                        HttpException httpException = (HttpException) e;
                                        if(httpException.code() == 404 || httpException.code() == 401 || httpException.code() == 406) {
                                            Toast.makeText(SignInActivity.this, "您的账号或密码不正确", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    //success = false;
                                }

                                @Override
                                public void onNext(User user) {

                                    //success = true;
                                    Intent intent = getIntent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("usrName", user.getAccount());
                                    bundle.putString("imagePath", user.getPicUrl());
                                    bundle.putString("phoneNumber", user.getPhone());
                                    bundle.putString("nickName", user.getUserName());
                                    Log.i("ADD",user.getAccount()+  user.getPicUrl()+ user.getPhone()+user.getUserName());
                                    intent.putExtras(bundle);
                                    //此处需要数据库传回来的值处理
                                    SignInActivity.this.setResult(2, intent);
                                    SignInActivity.this.finish();
                                }
                            });
                }
            }
        });
    }

    public  boolean login(String message, String password) {
        DramaticService service = ServiceFactory.createService(DramaticService.class);
        String phone = "";
        String account = "";
        if (isPhone(message)) {
            phone = message;
        } else {
            account = message;
        }

        Map<String, Object> temp = new HashMap<>();
        temp.put("account", "a12345 ");
        temp.put("password", "asdf1234");
        temp.put("phone", phone);
        service.login(temp)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {             //HTTP错误
                            HttpException httpException = (HttpException) e;
                            Toast.makeText(SignInActivity.this, ""+httpException.code(), Toast.LENGTH_SHORT).show();
                        }
                        success = false;
                    }

                    @Override
                    public void onNext(User user) {

                        success = true;
                    }
                });

        return success;
    }
    //判断账号是否格式正确
    public static boolean checkUsername(String username){
        String regex = "([a-zA-Z0-9]{1,15})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public static boolean isPhone(String phoneOrAccount){
        String regex = "([0-9]{1,15})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phoneOrAccount);
        return m.matches();
    }
}
