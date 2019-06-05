package com.daniel.JoyRent.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.daniel.JoyRent.Image_UpLoad.TakePhoto;
import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.main.widget.MainActivity;
import com.daniel.JoyRent.utils.HttpUtils;

import java.io.IOException;

public class PersonalInfo extends AppCompatActivity implements View.OnClickListener {

    private TextView detail_name;
    private TextView detailSex;

    private TextView detail_phone;
    private String url2 = Urls.Commons+"/member/FindID";
    public  String id="0";


    @SuppressLint("HandlerLeak")
    private Handler handler1=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                PersonInfo personInfo= (PersonInfo) msg.obj;
                String name=personInfo.getMember_name();
                String sex=personInfo.getMember_sex();

                String phone1=personInfo.getMember_phone();



                detail_name.setText(name);
                detailSex.setText(sex);

                detail_phone.setText(phone1);
            }
        }


    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();

        if(OldLogin.userId!=0)
        {
            id  =String.valueOf(OldLogin.userId);

            new Thread( new Runnable() {
                @Override
                public void run()
                {
                    HttpUtils httpUtils=new HttpUtils();
                    final PersonInfo result;
                    try {
                        result = httpUtils.FindID( url2, id );

                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj=result;
                        handler1.sendMessage(msg);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }

    }





    private void initView() {
        //detailPhone= (TextView) findViewById(R.id.et_phone);

        detail_name= (TextView) findViewById(R.id.nickname);

        detailSex= (TextView) findViewById(R.id.sex);
        detail_phone=(TextView) findViewById(R.id.peisonal_phone);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }

    public void fanhui_personaldata1(View view) {

        Intent intent = new Intent();
        intent.setClass(PersonalInfo.this,MainActivity.class);
        startActivity(intent);

    }
    public void ChangeDetail(View view) {

        Intent intent = new Intent();
        intent.setClass(PersonalInfo.this,modifiedData.class);
        startActivity(intent);

    }
    public void alterPhoto(View view) {

        Intent intent = new Intent();
        intent.setClass(PersonalInfo.this,TakePhoto.class);
        startActivity(intent);

    }

}
