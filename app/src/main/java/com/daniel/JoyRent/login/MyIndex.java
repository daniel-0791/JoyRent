package com.daniel.JoyRent.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daniel.JoyRent.R;
import com.daniel.JoyRent.Rental.House_order;
import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.utils.HttpUtils;
import com.daniel.JoyRent.utils.ImageLoaderUtils;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyIndex extends Fragment implements View.OnClickListener {



    private TextView detailSex;
    private TextView detailEmail;

    private TextView tv_email1;
    private LinearLayout lay_info;
    private LinearLayout lay_order;
    private TextView numberCard;
    private ImageView imageView;

    public  String id="0";
    private String url2 = Urls.Commons+"/member/FindID";
    private PersonInfo result;
    public MyIndex() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lay_info=(LinearLayout)getActivity().findViewById(R.id.lay_info);  //进入个人信息页面
        lay_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PersonalInfo.class));

            }
        });



        lay_order=(LinearLayout)getActivity().findViewById(R.id.lay_order);  //进入个人订单
        lay_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), House_order.class));

            }
        });
        initView();

        if(OldLogin.userId!=0)
        {
            id  =String.valueOf(OldLogin.userId);
/**
 *
 * 子线程顺序执行 join
 */
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpUtils httpUtils=new HttpUtils();
                    try {
                        result = httpUtils.FindID( url2, id );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

      /*      new Thread( new Runnable() {
                @Override
                public void run()
                {
                    HttpUtils httpUtils=new HttpUtils();

                    try {
                        result = httpUtils.FindID( url2, id );

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();*/

            showPersonInfo(result);
        }


/*
        String id=String.valueOf(OldLogin.userId);   //我的界面上的信息
        List<PersonInfo> personInfo1= DataSupport.select("*")
                .where("member_ID = ?",id)
                .find(PersonInfo.class);

        int number=personInfo1.size();
        if(number==0)
        {

            detailEmail.setText("你好,请先登录");
        }
        else{
            PersonInfo personInfoOne=personInfo1.get(0);
          showPersonInfo(personInfoOne);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_user_info, container, false);


    }


    //作废
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.lay_info:
                break;
        }
    }
    public  void showPersonInfo(PersonInfo personInfo) {




        String sex=personInfo.getMember_sex();
        String email=personInfo.getMember_email();
        String phone1=personInfo.getMember_phone();
        String idcard=personInfo.getMember_idcard();
        /**
         * 图像路径修改
         */
        if(personInfo.getMember_image()!=null)
        {
            String image1=Urls.Commons+"/"+personInfo.getMember_image();
            ImageLoaderUtils.display(getActivity(), imageView, image1);
        }




    }
    private void initView() {
        //detailPhone= (TextView) findViewById(R.id.et_phone);



        detailEmail= (TextView) getActivity().findViewById(R.id.tv_email);
        detailSex= (TextView)getActivity(). findViewById(R.id.detail_sex);


        imageView= (ImageView) getActivity().findViewById(R.id.img_avatar); //换照片


    }
}
