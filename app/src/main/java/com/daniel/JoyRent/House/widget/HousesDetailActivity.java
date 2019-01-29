package com.daniel.JoyRent.House.widget;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.daniel.JoyRent.House.presenter.HousesDetailPresenter;
import com.daniel.JoyRent.House.presenter.HousesDetailPresenterImpl;
import com.daniel.JoyRent.House.view.HousesDetailView;
import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.HousesBean;
import com.daniel.JoyRent.utils.ImageLoaderUtils;


import org.sufficientlysecure.htmltextview.HtmlTextView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;




public class HousesDetailActivity  extends AppCompatActivity implements HousesDetailView {

    public  static  HousesBean mHouses;
    private HtmlTextView mTVHousesContent;
    private HousesDetailPresenter mHousesDetailPresenter;
    private ProgressBar mProgressBar;
    private SwipeBackLayout mSwipeBackLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
   //     mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mTVHousesContent = (HtmlTextView) findViewById(R.id.htHousesContent);

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
       mTVHousesContent.setHtmlFromString(mHouses.getDescription(), new HtmlTextView.LocalImageGetter());
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
