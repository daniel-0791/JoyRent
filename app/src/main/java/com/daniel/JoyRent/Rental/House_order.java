package com.daniel.JoyRent.Rental;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.Rentrequest;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.login.OldLogin;
import com.daniel.JoyRent.main.widget.MainActivity;
import com.daniel.JoyRent.utils.HttpUtils;

import java.io.IOException;

public class House_order extends AppCompatActivity {

    private TextView houseID;
    private TextView member_ID;
    private TextView mateCharacter;
    private TextView budget;

    private String url2 = Urls.Commons+"/request/FindOrder";
    public  String id="0";

    /**
     * 用handle来传递
     * @param savedInstanceState
     */

    @SuppressLint("HandlerLeak")
private Handler handler1=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                    Rentrequest rentrequest= (Rentrequest) msg.obj;
                String houseID1=String.valueOf(rentrequest.getHouseID());

                String member_ID1=String.valueOf(rentrequest.getMember_ID());

                String budget1=String.valueOf(rentrequest.getBudget());



                String mateCharacter1=rentrequest.getMateCharacter();


                mateCharacter.setText(" "+mateCharacter1);
                houseID.setText(" "+houseID1);

                member_ID.setText(" "+member_ID1);
                budget.setText(" "+budget1);


                super.handleMessage(msg);
            }
        }


    } ;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mateCharacter.setText("暂无订单");
                houseID.setText("暂无订单");

                member_ID.setText("暂无订单");
                budget.setText("暂无订单");


            }
        }


    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_order);
        initView();
/**
 * 获取订单信息
 */
        if(OldLogin.userId!=0)
        {
            id  =String.valueOf(OldLogin.userId);

            new Thread( new Runnable() {
                @Override
                public void run()
                {
                    HttpUtils httpUtils=new HttpUtils();
                    final Rentrequest rentrequest;
                    try {
                        rentrequest = httpUtils.FindOrder( url2, id );


                     if(rentrequest!=null)
                     {

                         Message msg = new Message();
                         msg.what = 1;
                         msg.obj=rentrequest;
                         handler1.sendMessage(msg);
                     }else
                     {
                         Message msg = new Message();
                         msg.what = 1;
                         handler.sendMessage(msg);
                     }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }

    private void initView() {

        houseID= (TextView) findViewById(R.id.initiator);

        member_ID= (TextView) findViewById(R.id.proposer);
        budget= (TextView) findViewById(R.id.budget);


        mateCharacter=(TextView) findViewById(R.id.mateCharacter);


    }
    /**
     * 返回个人中心
     * @param view
     */
    public void fanhui_personaldata1(View view) {

        Intent intent = new Intent(House_order.this, MainActivity.class);
        intent.putExtra("id",1);
        startActivity(intent);

    }
}
