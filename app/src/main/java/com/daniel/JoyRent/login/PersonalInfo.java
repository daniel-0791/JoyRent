package com.daniel.JoyRent.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.main.widget.MainActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

public class PersonalInfo extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private TextView detail_name;
    private TextView detailSex;
    private TextView detailEmail;
    private TextView detail_phone;
    private Context context;
    private ImageView fanhui_personaldata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();

        String id=String.valueOf(OldLogin.userId);
        List<PersonInfo> personInfo1= DataSupport.select("*")
                .where("member_ID = ?",id)
                .find(PersonInfo.class);

        int number=personInfo1.size();
        if(number==0)
        {

            detail_name.setText("请登录");
            detailSex.setText("请登录");
        }
        else{
            PersonInfo personInfoOne=personInfo1.get(0);
            showPersonInfo(personInfoOne);
        }
    }






    public  void showPersonInfo(PersonInfo personInfo) {



        String name=personInfo.getMember_name();
        String sex=personInfo.getMember_sex();

        String phone1=personInfo.getMember_phone();
/*

        if(personInfo.getMember_image()!=null)
        {
            String image1=Urls.Commons+"/"+personInfo.getMember_image();

            ImageLoaderUtils.display(getApplication(), imageView, image1);
        }
*/


        detail_name.setText(name);
        detailSex.setText(sex);

        detail_phone.setText(phone1);


    }
    private void initView() {
        //detailPhone= (TextView) findViewById(R.id.et_phone);

        detail_name= (TextView) findViewById(R.id.nickname);
        imageView= (ImageView)findViewById(R.id.img_avatar); //换照片
        detailSex= (TextView) findViewById(R.id.sex);
        detail_phone=(TextView) findViewById(R.id.peisonal_phone);
        fanhui_personaldata=(ImageView)findViewById(R.id.fanhui_personaldata);
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

}
