package com.daniel.JoyRent.beans;

import com.daniel.JoyRent.commons.Urls;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class HousesBean  extends DataSupport implements Serializable {

    private int houseID;
    private String houseName;
    private int memberID;
    private String rentPrice;
    private String checkInDate;
    private String houseType;
    private String area;
    private String elevator;
    private String image;
    private  String description;
    private  String way;
    private  String rentNum;
    private  String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRentNum() {
        return rentNum;
    }

    public void setRentNum(String rentNum) {
        this.rentNum = rentNum;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 非数据库字段
     * 一个房源对应多个请求
     */


    public String getImage() {
        return Urls.Commons+"/"+image;
    }

    public void setImage(String image) {
        this.image =Urls.Commons+image;

    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }



    public String getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public  String bolwingJson1() {
        this.rentPrice = rentPrice;
        this.area = area;
        this.elevator=elevator;
        this.description=description;

        String arrayStr="[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}]";
        //转化为list

        return /* {"name":"jifeng","company":"taobao"}; */
                " {\"rentPrice\":\"" + rentPrice + "\","   + "\"area\":\"" + area+ "\","  +"\"checkInDate\":\"" + checkInDate+ "\","  + "\"houseName\":\"" + houseName+ "\"," + "\"houseType\":\"" + houseType+ "\","  +"\"elevator\":\"" + elevator+"\"," +"\"description\":\"" + description  +"\"" + "  }";
        //     "{'username':" + username + ","+"'password':"+password+"}";
            // +   +"\"area\":\"" + area+ "\","


    }

 }

