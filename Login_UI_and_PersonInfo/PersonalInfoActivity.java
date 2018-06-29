package com.example.chen.dramatic_tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Admin on 2018/6/28.
 */

public class PersonalInfoActivity extends AppCompatActivity{

    private ConstraintLayout personInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        //跳转到用户详细资料
        personInfo = (ConstraintLayout) findViewById(R.id.modifyPersonInfo);
        personInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonalInfoActivity.this, PersonInfoDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
