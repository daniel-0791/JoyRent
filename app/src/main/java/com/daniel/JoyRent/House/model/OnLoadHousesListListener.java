package com.daniel.JoyRent.House.model;

import com.daniel.JoyRent.beans.HousesBean;

import java.util.List;

public interface OnLoadHousesListListener {


    void onSuccess(List<HousesBean> list);

    void onFailure(String msg);
}
