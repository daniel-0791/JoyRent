package com.daniel.JoyRent.Image_UpLoad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.daniel.JoyRent.R;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.login.OldLogin;
import com.daniel.JoyRent.main.widget.MainActivity;
import com.daniel.JoyRent.utils.OkHttp3Util;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class TakePhoto extends AppCompatActivity implements View.OnClickListener {


    private ImageView ivShow;



    private TakePictureManager takePictureManager;

    private  String  fileImage;

    private  String  Url=Urls.Commons+"/Imagefiles/uploadperson";

    private  File imageFile;

    private boolean enable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        inintView();
       /* findViewById(R.id.btn_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPicker.from(TakePhoto.this)
                        .enableAnimation(enable)
                   //     .setAnimationStyle(anim)
                        .setLocatedCity(null)
                      //  .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                               // currentTV.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                                Toast.makeText(
                                        getApplicationContext(),
                                        String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CityPicker.from(TakePhoto.this).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();
            }
        });*/
    }

    private void inintView() {
        ivShow = (ImageView) findViewById(R.id.ivShow);
        Button btCamera = (Button) findViewById(R.id.btCamera);
        btCamera.setOnClickListener(this);
        Button btAlbum = (Button) findViewById(R.id.btAlbum);
        btAlbum.setOnClickListener(this);

        Button upImageId = (Button) findViewById(R.id.upImageId);
        upImageId.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btCamera:

                takePictureManager = new TakePictureManager(this);
                //开启裁剪 比例 1:3 宽高 350 350  (默认不裁剪)
                takePictureManager.setTailor(1, 3, 350, 350);
                //拍照方式
                takePictureManager.startTakeWayByCarema();
                //回调
                takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                    //成功拿到图片,isTailor 是否裁剪？ ,outFile 拿到的文件 ,filePath拿到的URl
                    @Override
                    public void successful(boolean isTailor, File outFile, Uri filePath) {
                        imageFile=outFile;

                        fileImage=filePath.getPath();
                        Picasso.with(TakePhoto.this).load(outFile).error(R.mipmap.ic_launcher).into(ivShow);
                    }

                    //失败回调
                    @Override
                    public void failed(int errorCode, List<String> deniedPermissions) {
                        Log.e("==w",deniedPermissions.toString());
                    }
                });
                break;
            case R.id.btAlbum:

                takePictureManager = new TakePictureManager(this);
                takePictureManager.setTailor(1, 1, 350, 350);
                takePictureManager.startTakeWayByAlbum();
                takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                    @Override
                    public void successful(boolean isTailor, File outFile, Uri filePath) {
                        imageFile=outFile;


                        fileImage=filePath.getPath();
                        Picasso.with(TakePhoto.this).load(outFile).error(R.mipmap.ic_launcher).into(ivShow);
                    }

                    @Override
                    public void failed(int errorCode, List<String> deniedPermissions) {

                    }

                });

                break;
            /**
             * 发送照片
             */
            case R.id.upImageId:
              String id=String.valueOf(OldLogin.userId);


                OkHttp3Util.uploadFile1(Url,imageFile,id, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String responseData=response.body().string();
                        runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                //更新UI
                                Toast.makeText(TakePhoto.this, "上传成功", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(TakePhoto.this, MainActivity.class);
                                intent.putExtra("id",1);
                                startActivity(intent);

                            }

                        });
                    }
                });



                break;
        }
    }


    //把本地的onActivityResult()方法回调绑定到对象
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

    //onRequestPermissionsResult()方法权限回调绑定到对象
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        takePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
