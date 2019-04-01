package com.daniel.JoyRent.login;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.main.widget.MainActivity;
import com.daniel.JoyRent.utils.HttpUtils;


import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.MediaType;

public class OldLogin extends AppCompatActivity  implements View.OnClickListener{
    private static final String TAG ="OldLogin" ;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private CustomVideoView videoview;
    private Button btn_enter;
    private FloatingActionButton fab;
    private  Button bt_go;
    private EditText username;
    private EditText password1;
    private Context context;
    private TextView thirdLogin;
    public static int userId=0;  //全局
    public static String IdCard="1111";  //全局
    // LoginCheck jsonBean=null;
    private String url = Urls.Commons+"/member/getOneMember";
private String url2 = Urls.Commons+"/member/getOneMemberByPhone";
    public static String user;
    public static String pas;
    public String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        context = this;
        username=(EditText)findViewById(R.id.et_phone);
        password1=(EditText)findViewById(R.id.et_pwd);//密码


        //  ButterKnife.inject(this);
        initView();

    }


    /**
     * 初始化
     */
    private void initView() {
        btn_enter=(Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);

        thirdLogin= (TextView) findViewById( R.id.thirdLogin);
        thirdLogin.setOnClickListener(this);
        // mTvResult = (TextView) findViewById(R.id.login_tv_result);
/*        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);*/


     /*   videoview = (CustomVideoView) findViewById(R.id.videoview);
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sport));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });*/
    }

    // @OnClick({R.id.bt_go, R.id.fab,R.id.btn_enter})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enter:

                    sendByPassword();

                break;
            case R.id.thirdLogin:

                opeRegisterPager();
                break;
            case R.id.bt_go:


                // Intent i2 = new Intent(this,LoginSuccessActivity.class);
                //  startActivity(i2, oc2.toBundle());
                break;
        }
    }
    private void sendByPassword() {

        final String   username1 = username.getText().toString().trim();
        final String  password = password1.getText().toString().trim();

        user=username1; pas=password;
        if(TextUtils.isEmpty(username1) || TextUtils.isEmpty(password)){
            Toast.makeText(OldLogin.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread( new Runnable() {
            @Override
            public void run() {

                HttpUtils httpUtils = new HttpUtils();

                try {
                    final PersonInfo result = httpUtils.login(url, username1,password);
                    Log.d(TAG, "结果:" + result);
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(result!=null){
                                userId=result.getMember_ID();
                                IdCard=result.getMember_idcard();
                                Toast.makeText(OldLogin.this, "登录成功", Toast.LENGTH_SHORT).show();
                                List<PersonInfo> personInfo1= DataSupport.select("*")
                                        .where("member_ID = ?",String.valueOf(userId))
                                        .find(PersonInfo.class);
                                if(personInfo1.size()==0)
                                {
                                    PersonInfo personInfo=new PersonInfo();
                                    personInfo.setMember_ID(result.getMember_ID());
                                    personInfo.setMember_name(result.getMember_name());
                                    personInfo.setMember_password(result.getMember_password());
                                    personInfo.setMember_sex(result.getMember_sex());
                                    personInfo.setMember_phone(result.getMember_phone());
                                    personInfo.setMember_image(result.getMember_image());
                                    personInfo.save();
                                }
                                else
                                {
                                    PersonInfo personInfo=new PersonInfo();
                                    personInfo.setMember_ID(result.getMember_ID());
                                    personInfo.setMember_name(result.getMember_name());
                                    personInfo.setMember_password(result.getMember_password());
                                    personInfo.setMember_sex(result.getMember_sex());
                                    personInfo.setMember_phone(result.getMember_phone());
                                    personInfo.setMember_image(result.getMember_image());
                                    personInfo.setMember_idcard(result.getMember_idcard());
                                    personInfo.setMember_email(result.getMember_email());
                                    personInfo.update(result.getMember_ID());

                                }

                                startActivity(new Intent(OldLogin.this,MainActivity.class));

                            }else{
                                //     mTvResult.setText("登录失败");
                                Toast.makeText(OldLogin.this, "登录失败,请重新登录", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
        }


    public void RegisterInfo(View view) {
        startActivity(new Intent(OldLogin.this,Register.class));
    }


private void opeRegisterPager() {
        //打开注册页面,这个页面就是我们在AndroidManifest.xml中添加的节点com.mob.tools.MobUIShell
        RegisterPage registerPage = new RegisterPage();
    registerPage.setRegisterCallback(new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // 解析注册结果
            if (result == SMSSDK.RESULT_COMPLETE) {  //成功后的操作
                @SuppressWarnings("unchecked") HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                String country = (String) phoneMap.get("country");
                phone = (String) phoneMap.get("phone");
                Log.d(TAG, "opeRegisterPager()--country=" + country + "--phone" + phone);



                new Thread( new Runnable() {
                    @Override
                    public void run() {

                        HttpUtils httpUtils = new HttpUtils();

                        try {
                            final PersonInfo result = httpUtils.PhoneVerify( url2,  phone);
                            Log.d(TAG, "结果:" + result);
                            //更新UI,在UI线程中
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(result!=null){
                                        userId=result.getMember_ID();
                                        IdCard=result.getMember_idcard();
                                        Toast.makeText(OldLogin.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        List<PersonInfo> personInfo1= DataSupport.select("*")
                                                .where("member_ID = ?",String.valueOf(userId))
                                                .find(PersonInfo.class);
                                        if(personInfo1.size()==0)
                                        {
                                            PersonInfo personInfo=new PersonInfo();
                                            personInfo.setMember_ID(result.getMember_ID());
                                            personInfo.setMember_name(result.getMember_name());
                                            personInfo.setMember_password(result.getMember_password());
                                            personInfo.setMember_sex(result.getMember_sex());
                                            personInfo.setMember_phone(result.getMember_phone());
                                            personInfo.setMember_image(result.getMember_image());
                                            personInfo.save();
                                        }
                                        else
                                        {
                                            PersonInfo personInfo=new PersonInfo();
                                            personInfo.setMember_ID(result.getMember_ID());
                                            personInfo.setMember_name(result.getMember_name());
                                            personInfo.setMember_password(result.getMember_password());
                                            personInfo.setMember_sex(result.getMember_sex());
                                            personInfo.setMember_phone(result.getMember_phone());
                                            personInfo.setMember_image(result.getMember_image());
                                            personInfo.setMember_idcard(result.getMember_idcard());
                                            personInfo.setMember_email(result.getMember_email());
                                            personInfo.update(result.getMember_ID());

                                        }



                                        startActivity(new Intent(OldLogin.this,MainActivity.class));

                                    }else{
                                        //     mTvResult.setText("登录失败");
                                        Toast.makeText(OldLogin.this, "登录失败,请重新登录", Toast.LENGTH_SHORT).show();
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
    });
    registerPage.show(context);

  //  SMSSDK.registerEventHandler(onUnregister());
/*    ShareSDK.initSDK(this);

    Platform qq = ShareSDK.getPlatform(QQ.NAME);

    qq.SSOSetting(true);

    authorize(qq);*/

/*
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // title标题，微信、QQ和QQ空间等平台使用
    oks.setTitle("罗文杰");
    // titleUrl QQ和QQ空间跳转链接
    oks.setTitleUrl("http://sharesdk.cn");
    // text是分享文本，所有平台都需要这个字段
    oks.setText("我是分享文本");
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
    // url在微信、微博，Facebook等平台中使用
    oks.setUrl("http://sharesdk.cn");
    // comment是我对这条分享的评论，仅在人人网使用
    oks.setComment("我是测试评论文本");
    // 启动分享GUI
    oks.show(this);*/

}

}
