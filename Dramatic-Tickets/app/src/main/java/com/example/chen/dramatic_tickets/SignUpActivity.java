package com.example.chen.dramatic_tickets;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    ImageView mImage;
    private boolean photoAdded = false;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mImage= (ImageView) findViewById(R.id.pic);
        clickImg(mImage);
        Button register = (Button) findViewById(R.id.register);
        clickRegister(register);
    }

    //此函数用于检验注册是否合法，然后再传给数据库
    public void clickRegister(Button register) {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resultcode = 0;

                TextInputLayout userName, password, confirmPassword, PhoneNumber;
                userName = (TextInputLayout) findViewById(R.id.userName);
                password = (TextInputLayout) findViewById(R.id.password);
                confirmPassword = (TextInputLayout) findViewById(R.id.confirmPassword);
                PhoneNumber = (TextInputLayout) findViewById(R.id.PhoneNumber);
                EditText edit1, edit2, edit3, edit4;
                edit1 = userName.getEditText();
                edit2 = password.getEditText();
                edit3 = confirmPassword.getEditText();
                edit4 = PhoneNumber.getEditText();

                String stringUserName, stringPassword, stringConfirmPassword, stringPhoneNumber;
                stringUserName = edit1.getText().toString();
                stringPassword = edit2.getText().toString();
                stringConfirmPassword = edit3.getText().toString();
                stringPhoneNumber = edit4.getText().toString();

                //检验数据合法
                if (stringUserName.isEmpty()) {
                    userName.setErrorEnabled(true);
                    userName.setError("账号不能为空");
                } else if(!checkUsername(stringUserName)) {
                    //检验是否是字母开头,上面括号里调用了后面的正则表达式判断函数
                    userName.setErrorEnabled(true);
                    userName.setError("账号必须是字母开头，由字母和数字组成。");
                }else {
                    userName.setErrorEnabled(false);
                    if (stringPassword.isEmpty()) {
                        password.setErrorEnabled(true);
                        password.setError("密码不能为空");
                    } else {
                        password.setErrorEnabled(false);
                        if (stringConfirmPassword.isEmpty()) {
                            confirmPassword.setErrorEnabled(true);
                            confirmPassword.setError("确认密码不能为空");
                        } else if(!stringPassword.equals(stringConfirmPassword)) {
                            confirmPassword.setErrorEnabled(true);
                            confirmPassword.setError("两次输入的密码不一样");
                        } else {
                            confirmPassword.setErrorEnabled(false);
                            if (stringPhoneNumber.isEmpty()) {
                                PhoneNumber.setErrorEnabled(true);
                                PhoneNumber.setError("电话号码不能为空");
                            } else if(!checkPhoneNumber(stringPhoneNumber)) {
                                PhoneNumber.setErrorEnabled(true);
                                PhoneNumber.setError("请输入正确的电话号码");
                            } else {
                                //此处检查是否上传头像
                                if (photoAdded) {
                                    resultcode = 1;
                                } else {
                                    Toast.makeText(SignUpActivity.this, "请上传头像", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                //此处完成了检测，暂时将数据传回上一个部分进行处理。实际应传数据到数据库并判断是否注册成功。
                if (resultcode == 2) {
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putString("usrName", stringUserName);
                    bundle.putString("password", stringPassword);
                    bundle.putString("confirmPassword", stringConfirmPassword);
                    bundle.putString("phoneNumber", stringPhoneNumber);
                    //bundle.putParcelable("bitmap", bitmap);
                    intent.putExtras(bundle);
                    SignUpActivity.this.setResult(resultcode, intent);
                    SignUpActivity.this.finish();
                }
            }
        });
    }

    //判断账号是否字母开头
    public static boolean checkUsername(String username){
        String regex = "(^[a-zA-Z][a-zA-Z0-9]{0,14})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    //电话号码是否以1开头，共11位
    public static boolean checkPhoneNumber(String phoneNumber){
        Pattern pattern=Pattern.compile("^1[0-9]{10}$");
        Matcher matcher=pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    //点击头像后进行的操作，这里只是假的，需要修改
    public void clickImg(ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                albumIntent.setType("image/*");
                startActivityForResult(albumIntent, 5);
            }
        });
    }

    //点击头像后进行的操作，这里只是假的，需要修改
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 5) {
            ContentResolver resolver = getContentResolver();
            Uri uri = intent.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver,uri);//获得图片
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView imageView = (ImageView) findViewById(R.id.pic);
            imageView.setImageBitmap(bitmap);
            photoAdded = true;
        }
    }
}
