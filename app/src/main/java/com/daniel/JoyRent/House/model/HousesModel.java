package com.daniel.JoyRent.House.model;

public interface HousesModel {
//统一Houses
    void loadHouses(String url, int type, OnLoadHousesListListener listener);

    void loadHousesDetail(String docid, OnLoadHouseDetailListener listener);
}
