package com.daniel.JoyRent.House.widget;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.daniel.JoyRent.House.presenter.HousesDetailPresenter;
import com.daniel.JoyRent.House.presenter.HousesDetailPresenterImpl;
import com.daniel.JoyRent.House.view.HousesDetailView;
import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.HousesBean;
import com.daniel.JoyRent.beans.Rentrequest;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.login.OldLogin;
import com.daniel.JoyRent.utils.ImageLoaderUtils;
import com.daniel.JoyRent.utils.OkHttp3Util;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.Call;
import okhttp3.Response;


public class HousesDetailActivity  extends AppCompatActivity implements HousesDetailView {

    public  static  HousesBean mHouses;
    private HtmlTextView mTVHousesContent;
    private HtmlTextView mTVHousesContentMoney;
    private HtmlTextView mTVHousesContentAdress;
    private HtmlTextView mTVHousesCapacity;
    private HousesDetailPresenter mHousesDetailPresenter;
    private ProgressBar mProgressBar;
    private SwipeBackLayout mSwipeBackLayout;
    private int Host_id;
    private String money;
    private String mateCharacter;
    private  String data;
    private  String url=Urls.Commons+"/request/rentRequest";

private    JSONObject jsonObject;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
   //     mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mTVHousesContent = (HtmlTextView) findViewById(R.id.htHousesContent);
        mTVHousesContentMoney= (HtmlTextView) findViewById(R.id.htHousesdetailmoney);
        mTVHousesContentAdress= (HtmlTextView) findViewById(R.id.htHousesdetailadress);
        mTVHousesCapacity= (HtmlTextView) findViewById(R.id.Capacity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

/*        mSwipeBackLayout = getSwipeBackLayout();

        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);*/

        mHouses = (HousesBean) getIntent().getSerializableExtra("Houses");
        //HousesAdapter.OnItemClickListener mOnItemClickListener = new HousesAdapter.OnItemClickListener()这里给的

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mHouses.getHouseName());

        ImageLoaderUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivHouseImage), mHouses.getImage());

        mHousesDetailPresenter = new HousesDetailPresenterImpl(getApplication(), this);
       // mHousesDetailPresenter.loadHousesDetail(mHouses.getHouseName());//   房源详细信息获取列表的id
       //showHousesDetialContent(mHouses.getDescription());//房源详情
        /**
         * 加载房源详情，租金等等
         */
       mTVHousesContent.setHtmlFromString(mHouses.getDescription(), new HtmlTextView.LocalImageGetter());
        mTVHousesContentMoney.setHtmlFromString(mHouses.getRentPrice(), new HtmlTextView.LocalImageGetter());
        mTVHousesContentAdress.setHtmlFromString(mHouses.getArea(), new HtmlTextView.LocalImageGetter());
        mTVHousesCapacity.setHtmlFromString(mHouses.getCapacity(), new HtmlTextView.LocalImageGetter());

        Host_id= mHouses.getHouseID();
        money= mHouses.getRentPrice();
        mateCharacter= mHouses.getDescription();
    }



    /**
     * 签约信息  signContract
     */

    public void signContract(View view) throws ParseException {

        /**
         * 获取当前时间
         */
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm:ss");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    Time    =    formatter.format(curDate);
        Log.d("signContract",Time);


        if(OldLogin.userId!=0)
        {


            Rentrequest rentrequest=new Rentrequest();
            rentrequest.setHouseID(Host_id);
            rentrequest.setMember_ID(OldLogin.userId);
          
            rentrequest.setReqTime(Time);
            rentrequest.setBudget(2000);
            rentrequest.setMateCharacter("要求室友安静");

            data= JSON.toJSONString(rentrequest);
            Log.d("signContract",data);

            OkHttp3Util.doPostJson(url,data, new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


           data = response.body().string();

                    Log.d("response",data);
                    Message message=new Message();
                    message.obj=data;


                  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    /*  handler.handleMessage( message);*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //更新UI

                            try {
                                if(jsonObject.getString("regMsg").equals("success"))
                                {
                                    Toast.makeText(HousesDetailActivity.this, "签约成功，请等待审核", Toast.LENGTH_SHORT).show();
                                }
                                else if(jsonObject.getString("regMsg").equals("failed"))
                                {
                                    Toast.makeText(HousesDetailActivity.this, "签约已满", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

        }
        else {
            Toast.makeText(HousesDetailActivity.this, "请先登录再进行签约", Toast.LENGTH_SHORT).show();

        }

    }

    public void CallHim(View view){


        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "15779481686"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    @Override
    public void showHousesDetialContent(String HousesDetailContent) {
       // mTVHousesContent.setHtmlFromString(HousesDetailContent, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
      // mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
