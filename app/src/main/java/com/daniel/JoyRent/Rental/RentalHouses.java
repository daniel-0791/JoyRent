package com.daniel.JoyRent.Rental;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.daniel.JoyRent.Image_UpLoad.TakePictureManager;
import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.HousesBean;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.utils.OkHttp3Util;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RentalHouses extends AppCompatActivity implements View.OnClickListener {
    private Button btn_enter;

    private Button btn_enter2;

    private EditText price;
    private  EditText houseDetail;
    private  EditText Adress;
    private  String Url=Urls.Commons+"/Imagefiles/putHouse";
    private  String Url1=Urls.Commons+"/Imagefiles/uploadfile";

    private    String jsonData;
    private Spinner spinner;
        private   String spinnerselect;
    private TakePictureManager takePictureManager;
    private  File imageFile;
    private ImageView ivShow;
    private Spinner spinnerHouseType;
    private   String spinnerselectHouseType;
    private  EditText houseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_main);

        initView();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                spinnerselect = (String) spinner.getSelectedItem();
                //把该值传给 TextView

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                spinnerselect="有";
            }
        });
        spinnerHouseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                spinnerselectHouseType = (String) spinnerHouseType.getSelectedItem();
                //把该值传给 TextView

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                spinnerselectHouseType="两室两厅";
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitHouse:

                sendWithOkhttp();
                break;
            case R.id.choosephoto:
                Toast.makeText(RentalHouses.this, "选择照片", Toast.LENGTH_SHORT).show();
                takePictureManager = new TakePictureManager(this);
                takePictureManager.setTailor(1, 1, 350, 350);
                takePictureManager.startTakeWayByAlbum();
                takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                    @Override
                    public void successful(boolean isTailor, File outFile, Uri filePath) {
                        imageFile=outFile;
                       // tvShow.setText(filePath.getPath());
                     //   fileImage=filePath.getPath();
                        Picasso.with(RentalHouses.this).load(outFile).error(R.mipmap.ic_launcher).into(ivShow);
                    }

                    @Override
                    public void failed(int errorCode, List<String> deniedPermissions) {

                    }

                });

                break;



        }
    }



    private void sendWithOkhttp(){


        final String   price1 = price.getText().toString().trim();
        final String  Adress1 = Adress.getText().toString().trim();
        final String  houseDetail1 = houseDetail.getText().toString().trim();
        final String  housename = houseName.getText().toString().trim();




/**
 * 获取当前时间
 */

        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    putTime    =    formatter.format(curDate);
        Log.d("asdsa",putTime);


        HousesBean housesBean=new HousesBean();
        housesBean.setCheckInDate(putTime);
        housesBean.setHouseName(housename);
        housesBean.setRentPrice(price1);
        housesBean.setArea(Adress1);
        housesBean.setElevator(spinnerselect);
        housesBean.setDescription(houseDetail1);
        housesBean.setHouseType(spinnerselectHouseType);
        jsonData=housesBean.bolwingJson1(); //这里跳出来

        String filename1="Jerry";
        OkHttp3Util.uploadFile1(Url1,imageFile,filename1, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();

            }
        });





                try {

                    OkHttp3Util.doPostJson(Url,jsonData, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("aaaa","成功 l ");
                            Toast.makeText(RentalHouses.this, "上传成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                    /**
                     *
                     */



                }catch (Exception e)
                {

                }



    }
    /**
     * 初始化
     */
    private void initView() {
        price= (EditText) findViewById(R.id.money);
        Adress= (EditText) findViewById(R.id.adress);
        spinner= (Spinner) findViewById(R.id.spinner);
        ivShow= (ImageView) findViewById(R.id.ivShow);
      //  tvShow= (TextView) findViewById(R.id.tvShow);
        houseDetail= (EditText) findViewById(R.id.houseDetail);
        btn_enter = (Button) findViewById(R.id.submitHouse);
        btn_enter.setOnClickListener(this);
        btn_enter2 = (Button) findViewById(R.id.choosephoto);
        btn_enter2.setOnClickListener(this);
        spinnerHouseType= (Spinner) findViewById(R.id.spinner3);
        houseName=(EditText) findViewById(R.id.houseName);
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
