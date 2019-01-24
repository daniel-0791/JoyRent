package com.daniel.JoyRent.House.view;



import com.daniel.JoyRent.beans.HousesBean;

import java.util.List;


public interface HousesView {

    void showProgress();

    void addHouses(List<HousesBean> HousesList);

    void hideProgress();

    void showLoadFailMsg();
}
