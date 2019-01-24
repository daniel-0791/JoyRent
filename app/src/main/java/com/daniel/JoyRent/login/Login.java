package com.daniel.JoyRent.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.main.widget.MainActivity;
import com.daniel.JoyRent.utils.HttpUtils;
import com.lauren.simplenews.R;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import butterknife.InjectView;
import okhttp3.MediaType;

public class Login extends AppCompatActivity  implements View.OnClickListener{
    private static final String TAG ="Login" ;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private CustomVideoView videoview;
    private Button btn_enter;
    private  FloatingActionButton fab;
    private  Button bt_go;
    private EditText username;
    private EditText password1;
    private TextView switcher;
    public static String user;
    public static String pas;


    public static int userId=0;  //全局
    public static String IdCard="1111";  //全局
   // LoginCheck jsonBean=null;
    private String url = Urls.Commons+"/member/getOneMember";

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.bt_go)
    Button btGo;
    @InjectView(R.id.cv)
    CardView cv;
  /*  @InjectView(R.id.fab)
    FloatingActionButton fab;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.newloginlayout);

        username=(EditText)findViewById(R.id.et_username);
        password1=(EditText)findViewById(R.id.et_password);//密码
      //  ButterKnife.inject(this);
        initView();

    }


    /**
     * 初始化
     */
    private void initView() {


        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        bt_go=(Button) findViewById( R.id.bt_go);
        bt_go.setOnClickListener(this);
        switcher= (TextView) findViewById(  R.id.switcher);
        switcher.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switcher:

                startActivity(new Intent(Login.this,OldLogin.class));
                break;
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, Register.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, Register.class));
                }
                break;
            case R.id.bt_go:
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                sendrequestwwithokhttp();
               // Intent i2 = new Intent(this,LoginSuccessActivity.class);
              //  startActivity(i2, oc2.toBundle());
                break;
        }
    }


    private void sendrequestwwithokhttp(){

        final String   username1 = username.getText().toString().trim();
        final String  password = password1.getText().toString().trim();

        user=username1; pas=password;
        if(TextUtils.isEmpty(username1) || TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
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



                               startActivity(new Intent(Login.this,MainActivity.class));

                            }else{
                           //     mTvResult.setText("登录失败");
                                Toast.makeText(Login.this, "登录失败,请重新登录", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(Login.this,Register.class));
    }


/*    public static  void  actionStart(Context context, PersonInfo string)
    {
        Intent intent=new Intent(context,UserInfo.class);

        intent.putExtra("info", string);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent );
    }*/

}
