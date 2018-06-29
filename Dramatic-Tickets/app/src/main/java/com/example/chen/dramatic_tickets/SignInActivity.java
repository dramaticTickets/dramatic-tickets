package com.example.chen.dramatic_tickets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {
    ImageView mImage;
    String userNameLogin = "a", passwordLogin = "q";

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
                    if(stringUserName.equals(userNameLogin) && stringPassword.equals(passwordLogin)) {

                        resultcode = 2;
                    }
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putString("usrName", stringUserName);
                    intent.putExtras(bundle);
                    //此处需要数据库传回来的值处理
                    //bundle.putString("phoneNumber", stringPhoneNumber);
                    //bundle.putString("imagePath", imagePath);
                    SignInActivity.this.setResult(resultcode, intent);
                    SignInActivity.this.finish();
                }
            }
        });
    }

    //判断账号是否格式正确
    public static boolean checkUsername(String username){
        String regex = "([a-zA-Z0-9]{1,15})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }
}
