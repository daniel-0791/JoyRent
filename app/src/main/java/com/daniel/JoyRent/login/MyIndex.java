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

import com.daniel.JoyRent.Image_UpLoad.TakePhoto;
import com.daniel.JoyRent.R;
import com.daniel.JoyRent.Rental.House_order;
import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.utils.ImageLoaderUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyIndex extends Fragment implements View.OnClickListener {
    private TextView detailName;
    private TextView detail_name;
    private TextView detailSex;
    private TextView detailEmail;
    private TextView detail_phone;
    private TextView tv_email1;
    private LinearLayout lay_info;
    private LinearLayout lay_order;
    private TextView numberCard;
    private ImageView imageView;
    private String url = Urls.Commons+"/member/getOneMember";
    public MyIndex() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView imageView=getActivity().findViewById(R.id.img_avatar); //进入上传图片
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TakePhoto.class));

            }
        });
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
            case R.id.img_avatar:
                break;

            case R.id.lay_info:
                break;
        }
    }
    public  void showPersonInfo(PersonInfo personInfo) {



        String name=personInfo.getMember_name();
        String sex=personInfo.getMember_sex();
        String email=personInfo.getMember_email();
        String phone1=personInfo.getMember_phone();
        String idcard=personInfo.getMember_idcard();
        if(personInfo.getMember_image()!=null)
        {
            String image1=Urls.Commons+"/"+personInfo.getMember_image();
            ImageLoaderUtils.display(getActivity(), imageView, image1);
        }

        detailName.setText(name);
        detail_name.setText(name);
        detailSex.setText(sex);
        detailEmail.setText("你好,"+name);
        detail_phone.setText(phone1);
        numberCard.setText(idcard);
        tv_email1.setText(email);

    }
    private void initView() {
        //detailPhone= (TextView) findViewById(R.id.et_phone);
        detailName= (TextView) getActivity().findViewById(R.id.tv_name);
        detail_name= (TextView) getActivity().findViewById(R.id.detail_name);
        detailEmail= (TextView) getActivity().findViewById(R.id.tv_email);
        detailSex= (TextView)getActivity(). findViewById(R.id.detail_sex);


        imageView= (ImageView) getActivity().findViewById(R.id.img_avatar); //换照片


    }
}
