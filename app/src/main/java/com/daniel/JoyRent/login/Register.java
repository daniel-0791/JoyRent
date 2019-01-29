package com.daniel.JoyRent.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.utils.HttpUtils;


import java.io.IOException;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private Button btn_enter;
    private EditText username1;
    private EditText password1;
    private TextView mTvResult;
    private FloatingActionButton fab;
    private String url = Urls.Commons+ "/member/regist2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);


        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    username1=(EditText)findViewById(R.id.et_username);
    password1=(EditText)findViewById(R.id.et_password);

    initView();
}


    /**
     * 初始化
     */
    private void initView() {


        btn_enter = (Button) findViewById(R.id.bt_go);
        btn_enter.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_go:
                sendrequestwwithokhttp();

                break;
            case  R.id.fab:
                startActivity(new Intent(this, Login.class));
        }
    }


    private void sendrequestwwithokhttp(){

        final String   username = username1.getText().toString().trim();
        final String  password = password1.getText().toString().trim();
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(Register.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        new Thread( new Runnable() {
            @Override
            public void run() {

                HttpUtils httpUtils = new HttpUtils();
                //转换为JSON
                /*      String user = httpUtils.bolwingJson(username, password);*/




                //String user ="{'username':" + username + ","+"'password':"+password+"}";

                //  Log.d(TAG, "user:" + user);

                try {
                    final PersonInfo result = httpUtils.register(url, username,password);

                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if("success".equals(result.regFlag)){

                                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(   Register.this,Login.class));

                            }else{
                                Toast.makeText(Register.this, "用户名已存在，请重新注册", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        }).start();


    }


}
