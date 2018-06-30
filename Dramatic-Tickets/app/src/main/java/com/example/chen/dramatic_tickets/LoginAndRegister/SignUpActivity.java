package com.example.chen.dramatic_tickets.LoginAndRegister;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {
    ImageView mImage;
    private boolean photoAdded = false;
    Bitmap bitmap1 = null;
    String imagePath;

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

                TextInputLayout userName, password, confirmPassword, PhoneNumber, nickName;
                userName = (TextInputLayout) findViewById(R.id.userName);
                password = (TextInputLayout) findViewById(R.id.password);
                confirmPassword = (TextInputLayout) findViewById(R.id.confirmPassword);
                PhoneNumber = (TextInputLayout) findViewById(R.id.PhoneNumber);;
                nickName = (TextInputLayout) findViewById(R.id.nickname);
                EditText edit1, edit2, edit3, edit4, edit5;
                edit1 = userName.getEditText();
                edit2 = password.getEditText();
                edit3 = confirmPassword.getEditText();
                edit4 = PhoneNumber.getEditText();
                edit5 = nickName.getEditText();

                String stringUserName, stringPassword, stringConfirmPassword, stringPhoneNumber, stringNickName;
                stringUserName = edit1.getText().toString();
                stringPassword = edit2.getText().toString();
                stringConfirmPassword = edit3.getText().toString();
                stringPhoneNumber = edit4.getText().toString();
                stringNickName = edit5.getText().toString();


                //检验数据合法
                if(stringNickName.isEmpty()) {
                    nickName.setErrorEnabled(true);
                    nickName.setError("请输入昵称");
                }else {
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
                }

                //此处完成了检测，应该与数据库联系。暂时将数据传回上一个部分进行处理。实际应传数据到数据库并判断是否注册成功。

                DramaticService service = ServiceFactory.createService(DramaticService.class);
                final Map<String, Object> temp = new HashMap<>();
                temp.put("account", stringUserName);
                temp.put("password", stringPassword);
                temp.put("phone", stringPhoneNumber);
                temp.put("picUrl", imagePath);
                temp.put("userName", stringNickName);

                service.register(temp)
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
                                    if(httpException.code() == 405) {
                                        Toast.makeText(SignUpActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
                                    } else if(httpException.code() == 403) {
                                        Toast.makeText(SignUpActivity.this, "该手机号已注册", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                //success = false;
                            }

                            @Override
                            public void onNext(User user) {
                                //success = true;
                                Intent intent = getIntent();
                                Bundle bundle = new Bundle();

                                bundle.putString("usrName", temp.get("account").toString());
                                bundle.putString("imagePath", temp.get("picUrl").toString());
                                bundle.putString("phoneNumber", temp.get("phone").toString());
                                bundle.putString("nickName", temp.get("userName").toString());
                                Log.i("register",temp.get("phone").toString());
                                intent.putExtras(bundle);
                                //此处需要数据库传回来的值处理
                                SignUpActivity.this.setResult(1, intent);
                                SignUpActivity.this.finish();
                            }
                        });

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
                Intent albumIntent = new Intent(Intent.ACTION_PICK);
                albumIntent.setType("image/*");
                startActivityForResult(albumIntent, 5);
            }
        });
    }

    //点击头像后进行的操作
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK) {
            imagePath = "";
            if(data != null && data.getData() != null){
                //有数据返回直接使用返回的图片地址
                imagePath = getFilePathByFileUri(this, data.getData());
            }else{//无数据使用指定的图片路径
            }


            ImageView imageView = (ImageView) findViewById(R.id.pic);
            /*try {
                Toast.makeText(this, "111"+imagePath, Toast.LENGTH_SHORT).show();
                bitmap1 = getLocalImg(imagePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
            bitmap1 = readBitmap(this, imagePath);
            bitmap1 = setImgSize(bitmap1,400,400);

            imageView.setImageBitmap(bitmap1);
            photoAdded = true;

            /*ContentResolver resolver = getContentResolver();
            Uri uri = intent.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver,uri);//获得图片
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView imageView = (ImageView) findViewById(R.id.pic);
            imageView.setImageBitmap(bitmap);
            photoAdded = true;*/
        }
    }

   /* private Bitmap  getLocalImg(String path) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(path);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }*/

        public static String getFilePathByFileUri(Context context, Uri uri) {
        String filePath = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cursor.close();
        return filePath;
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

    /**
     * dp转换成px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据图片路径和格式把图片转化为Base64编码格式
     * @param imgPath
     * @param imgFormat 图片格式
     * @param quality 压缩的程度
     * @return
     */
    public static String imgToBase64(Context context, String imgPath, String imgFormat, int quality) {
        Bitmap bitmap = null;
        if (imgPath !=null && imgPath.length() > 0) {
            bitmap = readBitmap(context,imgPath);
        }
        if(bitmap == null){
            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            long length = imgBytes.length;
            //LogUtil.LogShitou("字节长度", length+"");
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
