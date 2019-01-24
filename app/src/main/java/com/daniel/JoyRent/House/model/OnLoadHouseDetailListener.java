package com.daniel.JoyRent.House.model;

import com.daniel.JoyRent.beans.HousesBean;


public interface OnLoadHouseDetailListener {

    void onSuccess(HousesBean ousesDetailBean);
  //  void onSuccessForBody(HousesBean housesBean);

    void onFailure(String msg);

}
