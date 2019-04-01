package com.daniel.JoyRent.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.utils.OkHttp3Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class modifiedData extends AppCompatActivity  implements View.OnClickListener {
    private EditText sex;
    private EditText nickname;
    private  String Url=Urls.Commons+"/member/modifiedData";
    private EditText telnumber;
    private EditText tv_email;
    private Button btn_submit;
    private    String jsonData;
    public static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_data);
        initView();
    }

    public void fanhui_data(View view) {

        Intent intent = new Intent();
        intent.setClass(modifiedData.this,PersonalInfo.class);
        startActivity(intent);

    }
    private void initView() {

        nickname= (EditText) findViewById(R.id.nickname);
        telnumber= (EditText) findViewById(R.id.telnumber);
        sex= (EditText) findViewById(R.id.sex);
        tv_email= (EditText) findViewById(R.id.tv_email);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);  //一定要初始化，哪怕onclick里不是btn_enter
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:

                sendWithOkhttp();
                break;
        }
    }


    private void sendWithOkhttp(){


        final String   nickname1 = nickname.getText().toString().trim();
        final String  telnumber1 = telnumber.getText().toString().trim();
        final String  sex1 = sex.getText().toString().trim();
        final String  tv_email1 = tv_email.getText().toString().trim();
        final int  ID = OldLogin.userId;





        PersonInfo personInfo=new PersonInfo();
        personInfo.setMember_email(tv_email1);
        personInfo.setMember_sex(sex1);
        personInfo.setMember_phone(telnumber1);
        personInfo.setMember_name(nickname1);
        personInfo.setMember_ID(ID);



        jsonData=personInfo.toString();

        String filename1="Jerry";






                try {

                    OkHttp3Util.doPostJson(Url,jsonData, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("aaaa","chengggong l ");
                            /**
                             * 成功后跳转
                             */

                            new Thread() {
                                public void run() {
                                    //这儿是耗时操作，完成之后更新UI；
                                    runOnUiThread(new Runnable(){

                                        @Override
                                        public void run() {
                                            //更新UI
                                            Toast.makeText(modifiedData.this, "chenggong", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent();
                                            intent.setClass(modifiedData.this,PersonalInfo.class);
                                            startActivity(intent);
                                        }

                                    });
                                }
                            }.start();


                        }

                    });

                    /**
                     *
                     */
                }catch (Exception e)
                {

                }


    }
}
