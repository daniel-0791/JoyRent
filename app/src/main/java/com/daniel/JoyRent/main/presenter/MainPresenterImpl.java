package com.daniel.JoyRent.main.presenter;

import com.daniel.JoyRent.main.view.MainView;
import com.lauren.simplenews.R;

public class MainPresenterImpl implements MainPresenter {  //当行

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                mMainView.switch2News();
                break;
            case R.id.navigation_houses:
                mMainView.switch2Houses();
                break;
            case R.id.navigation_item_about:
                mMainView.switch2About();
                break;
            default:
                mMainView.switch2News();
                break;
        }
    }
}
