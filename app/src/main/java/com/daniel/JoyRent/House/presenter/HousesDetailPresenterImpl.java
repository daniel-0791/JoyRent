package com.daniel.JoyRent.House.presenter;

import android.content.Context;

import com.daniel.JoyRent.House.model.HousesModel;
import com.daniel.JoyRent.House.model.HousesModelImpl;
import com.daniel.JoyRent.House.model.OnLoadHouseDetailListener;
import com.daniel.JoyRent.House.view.HousesDetailView;
import com.daniel.JoyRent.beans.HousesBean;



public class HousesDetailPresenterImpl implements HousesDetailPresenter, OnLoadHouseDetailListener {

    private Context mContent;
    private HousesDetailView mHousesDetailView;
    private HousesModel mHousesModel;
/*

HousesDetailActivity里
 */
    public HousesDetailPresenterImpl(Context mContent, HousesDetailView mHousesDetailView) {
        this.mContent = mContent;
        this.mHousesDetailView = mHousesDetailView;
        mHousesModel = new HousesModelImpl();
    }

    @Override
    public void loadHousesDetail(final String docId) {
       // mHousesDetailView.showProgress();
        mHousesModel.loadHousesDetail(docId, this);
    }


    @Override
    public void onSuccess(HousesBean HousesDetailBean) {


        if (HousesDetailBean != null) {
         //
        }
        mHousesDetailView.hideProgress();
    }

    @Override
    public void onFailure(String msg) {

        mHousesDetailView.showHousesDetialContent(msg);
        mHousesDetailView.hideProgress();


    }
}
