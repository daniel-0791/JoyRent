package com.daniel.JoyRent.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.main.widget.MainActivity;
import com.daniel.JoyRent.utils.HttpUtils;
import com.lauren.simplenews.R;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;

public class OldLogin extends AppCompatActivity  implements View.OnClickListener{
    private static final String TAG ="Login" ;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private CustomVideoView videoview;
    private Button btn_enter;
    private FloatingActionButton fab;
    private  Button bt_go;
    private EditText username;
    private EditText password1;

    public static int userId=0;  //全局
    public static String IdCard="1111";  //全局
    // LoginCheck jsonBean=null;
    private String url = Urls.Commons+"/member/getOneMember";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

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
        // mTvResult = (TextView) findViewById(R.id.login_tv_result);
/*        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);*/


        videoview = (CustomVideoView) findViewById(R.id.videoview);
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sport));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });
    }

    // @OnClick({R.id.bt_go, R.id.fab,R.id.btn_enter})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enter:

                sendrequestwwithokhttp();
                break;

            case R.id.bt_go:


                // Intent i2 = new Intent(this,LoginSuccessActivity.class);
                //  startActivity(i2, oc2.toBundle());
                break;
        }
    }


    private void sendrequestwwithokhttp(){

        final String   username1 = username.getText().toString().trim();
        final String  password = password1.getText().toString().trim();
        if(TextUtils.isEmpty(username1) || TextUtils.isEmpty(password)){
            Toast.makeText(OldLogin.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
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
                                    personInfo.save();
                                }



                                //   mTvResult.setText("登录成功");
                                //  actionStart(Login.this,result);
                                //   Log.d(TAG, "user:" + result.getMember_name());
                                // String name=result.getMember_name();
                                //  UserInfo.actionStart1(Login.this,name);


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

/*    public static  void  actionStart(Context context, PersonInfo string)
    {
        Intent intent=new Intent(context,UserInfo.class);

        intent.putExtra("info", string);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent );
    }*/

}
