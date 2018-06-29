package com.example.chen.dramatic_tickets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Admin on 2018/6/28.
 */

public class PersonInfoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_details);

        ImageView ToLeft = (ImageView) findViewById(R.id.ToLeft1);
        ToLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonInfoDetailActivity.this, PersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        ConstraintLayout headPortrait = (ConstraintLayout) findViewById(R.id.headPortraitLayout);
        headPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final ConstraintLayout nickname = (ConstraintLayout) findViewById(R.id.nicknameLayout);
        final ConstraintLayout sex = (ConstraintLayout) findViewById(R.id.sexLayout);
        final ConstraintLayout birth = (ConstraintLayout) findViewById(R.id.birthLayout);
        final ConstraintLayout city = (ConstraintLayout) findViewById(R.id.cityLayout);
        final ConstraintLayout life = (ConstraintLayout) findViewById(R.id.lifeState);
        final ConstraintLayout job = (ConstraintLayout) findViewById(R.id.jobState);
        final ConstraintLayout hobby = (ConstraintLayout) findViewById(R.id.hobbyState);
        final ConstraintLayout signature = (ConstraintLayout) findViewById(R.id.signatureState);
        final ConstraintLayout address = (ConstraintLayout) findViewById(R.id.addressState);

        final TextView nicknameText = (TextView) findViewById(R.id.nicknameContent);
        final TextView sexText = (TextView) findViewById(R.id.sexContent);
        final TextView birthText = (TextView) findViewById(R.id.birthContent);
        final TextView cityText = (TextView) findViewById(R.id.cityContent);
        final TextView lifeText = (TextView) findViewById(R.id.lifeStateContent);
        final TextView jobText = (TextView) findViewById(R.id.jobContent);
        final TextView hobbyText = (TextView) findViewById(R.id.hobbyContent);
        final TextView signatureText = (TextView) findViewById(R.id.signatureContent);
        final TextView addressText = (TextView) findViewById(R.id.addressContent);

        modifyInfomation(nickname, nicknameText);
        modifyInfomation(sex, sexText);
        modifyInfomation(birth, birthText);
        modifyInfomation(city, cityText);
        modifyInfomation(life, lifeText);
        modifyInfomation(job, jobText);
        modifyInfomation(hobby, hobbyText);
        modifyInfomation(signature, signatureText);
        modifyInfomation(address, addressText);

    }

    private void modifyInfomation(ConstraintLayout constrain, final TextView textView) {
        constrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PersonInfoDetailActivity.this);
                dialog.setTitle("修改昵称");
                dialog.setMessage("请输入昵称");
                final EditText et=new EditText(PersonInfoDetailActivity.this);
                et.setSingleLine();
                et.setHint("请输入文本");
                dialog.setView(et);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name=et.getText().toString().trim();
                        if ("".equals(name)) {
                            Toast.makeText(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT).show();
                            return ;
                        }else{
                            textView.setText(name);
                            //修改昵称，未保存到数据库
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();
            }
        });
    }
}
