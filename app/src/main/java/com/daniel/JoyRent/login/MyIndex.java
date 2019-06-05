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
import android.widget.Toast;

import com.daniel.JoyRent.R;
import com.daniel.JoyRent.Rental.House_order;
import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.main.widget.MainActivity;
import com.daniel.JoyRent.utils.HttpUtils;
import com.daniel.JoyRent.utils.ImageLoaderUtils;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyIndex extends Fragment implements View.OnClickListener {



    private LinearLayout lay_info;
    private LinearLayout lay_order;
    private LinearLayout lay_exit;
    private TextView name;
    private ImageView imageView;

    public  String id="0";
    private String url2 = Urls.Commons+"/member/FindID";
    private PersonInfo result;
    public MyIndex() {

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
        lay_exit=(LinearLayout)getActivity().findViewById(R.id.lay_exit);  //进入个人订单
        lay_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldLogin.userId=0;
                Toast.makeText(getActivity(),"退出成功",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), MainActivity.class));

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



            showPersonInfo(result);
        }


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




        String name1=personInfo.getMember_name();

        /**
         * 图像路径修改
         */
        if(personInfo.getMember_image()!=null)
        {
            String image1=Urls.Commons+"/"+personInfo.getMember_image();
            ImageLoaderUtils.display(getActivity(), imageView, image1);
        }

        name.setText("你好 "+name1);


    }
    private void initView() {
        //detailPhone= (TextView) findViewById(R.id.et_phone);



        name= (TextView) getActivity().findViewById(R.id.tv_name);



        imageView= (ImageView) getActivity().findViewById(R.id.img_avatar); //换照片


    }


}
