package com.daniel.JoyRent.House.presenter;


        import com.daniel.JoyRent.House.model.HousesModel;
        import com.daniel.JoyRent.House.model.HousesModelImpl;
        import com.daniel.JoyRent.House.model.OnLoadHousesListListener;
        import com.daniel.JoyRent.House.view.HousesView;
        import com.daniel.JoyRent.beans.HousesBean;
        import com.daniel.JoyRent.commons.Urls;
        import com.daniel.JoyRent.utils.LogUtils;

        import java.util.List;


public class HousesPresenterImpl implements HousesPresenter, OnLoadHousesListListener {

    private static final String TAG = "HousesPresenterImpl";

    private HousesView mHousesView;
    private HousesModel mHousesModel;

    public HousesPresenterImpl(HousesView HousesView) {
        this.mHousesView = HousesView;
        this.mHousesModel = new HousesModelImpl();
    }

    /*
       加载房源列表
     */

    @Override
    public void loadHouses(final int type, final int pageIndex) {

        //  String url = getUrl(type, pageIndex);//0，3
        String url =  Urls.HOUSEStest;
        LogUtils.d(TAG, url);
        //只有第一页的或者刷新的时候才显示刷新进度条
        if(pageIndex == 0) {
            mHousesView.showProgress();
        }
        mHousesModel.loadHouses(url, type, this);
    }




    @Override
    public void onSuccess(List<HousesBean> list) {
        mHousesView.hideProgress();
        mHousesView.addHouses(list);
    }

    @Override
    public void onFailure(String msg) {
        mHousesView.hideProgress();
        mHousesView.showLoadFailMsg();
    }
}
