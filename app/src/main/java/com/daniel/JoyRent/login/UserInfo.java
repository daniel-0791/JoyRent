package com.daniel.JoyRent.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniel.JoyRent.beans.PersonInfo;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.utils.ImageLoaderUtils;
import com.lauren.simplenews.R;
import com.roughike.bottombar.BottomBar;

import org.litepal.crud.DataSupport;

import java.util.List;

public class UserInfo extends AppCompatActivity {
    public static String UserInfoname=null;
   private TextView tvName;
    private PersonInfo personInfo;
    private static final String TAG ="UserInfo" ;
    private TextView detailName;
    private TextView detail_name;
    private TextView detailSex;
    private TextView ShowUserName;
    private TextView detailEmail;

    private TextView detail_phone;
    private TextView numberCard;
    private ImageView imageView;
    private TextView detailPhone;

    private Context mContext;
    private BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
      //  tvName=(TextView)findViewById(R.id.tv_name);

      //  String name=getIntent().getStringExtra("info");
      //  personInfo =(PersonInfo) getIntent().getSerializableExtra("info");


     //   String name=personInfo.getMember_name();
      //  Log.d(TAG, "user:" + name);
      //  tvName.setText(name);

            String id=String.valueOf(Login.userId);
            List<PersonInfo> personInfo1= DataSupport.select("*")
                    .where("member_ID = ?",id)
                    .find(PersonInfo.class);

                    int number=personInfo1.size();


                  //  Log.d(TAG,String.valueOf(ii));

                    if(number==0)
                    {
                        detailName.setText("请登录");
                        detail_name.setText("请登录");
                        detailSex.setText("请登录");
                    }
                    else{
                        PersonInfo personInfoOne=personInfo1.get(0);

                        //显示出来
                        showPersonInfo(personInfoOne);
                    }




      /*  bottomBar = (BottomBar) findViewById(R.id.bottomBar);


        bottomBar.setTabSelectionInterceptor(new TabSelectionInterceptor() {
            @Override
            public boolean shouldInterceptTabSelection(@IdRes int oldTabId, @IdRes int newTabId) {
                // 点击无效
                if (newTabId == R.id.tab_mine ) {
                    // 返回 true 。代表：这里我处理了，你不用管了。



                    return true;
                }
                if (newTabId == R.id.tab_HomePage ) {
                    // 返回 true 。代表：这里我处理了，你不用管了。
                    startActivity(new Intent(UserInfo.this,MainActivity.class));


                    return true;
                }
                if (newTabId == R.id.tab_nearby ) {
                    // 返回 true 。代表：这里我处理了，你不用管了。



                    return true;
                }

                return false;
            }
        });
*/

    }

    private void showPersonInfo(PersonInfo personInfo) {



        String name=personInfo.getMember_name();
        String sex=personInfo.getMember_sex();
        String email=personInfo.getMember_email();
        String phone1=personInfo.getMember_phone();
        String idcard=personInfo.getMember_idcard();
        String image1=Urls.Commons+"/"+personInfo.getMember_image();
        detailName.setText(name);
        detail_name.setText(name);
        detailSex.setText(sex);
        detailEmail.setText(email);
        detail_phone.setText(phone1);
        numberCard.setText(idcard);
        String sa="http://192.168.1.101:8080/img/house/06.jpg";
        ImageLoaderUtils.display(getApplicationContext(), imageView, image1);



    }

    private void initView() {
       //detailPhone= (TextView) findViewById(R.id.et_phone);
        detailName= (TextView) findViewById(R.id.tv_name);
        detail_name= (TextView) findViewById(R.id.detail_name);
        detailEmail= (TextView) findViewById(R.id.tv_email);
        detailSex= (TextView) findViewById(R.id.detail_sex);
        detail_phone=(TextView)findViewById(R.id.detail_phone);
        numberCard=(TextView) findViewById(R.id.numberCard);
        imageView= (ImageView) findViewById(R.id.img_avatar); //换照片
    }

  /*  public static  void  actionStart1(Context context, String string)
    {
        Intent intent=new Intent(context,UserInfo.class);
        intent.putExtra("info1",string);

        context.startActivity(intent );
    }*/
 }
