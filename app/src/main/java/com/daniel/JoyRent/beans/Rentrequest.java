package com.daniel.JoyRent.beans;

import org.litepal.crud.DataSupport;

public class Rentrequest extends DataSupport {
    private int reqId;
    private int member_ID;
    private int houseID;
    private String reqTime;
    private float budget;
    private String city;
    private String mateSex;

    public int getMember_ID() {
        return member_ID;
    }

    public void setMember_ID(int member_ID) {
        this.member_ID = member_ID;
    }

    private String nickname;
    private String job;
    private String image;
    private String mateCharacter;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMateCharacter() {
        return mateCharacter;
    }

    public void setMateCharacter(String mateCharacter) {
        this.mateCharacter = mateCharacter;
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }



    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMateSex() {
        return mateSex;
    }

    public void setMateSex(String mateSex) {
        this.mateSex = mateSex;
    }

    @Override
    public String toString() {
        return "Rentrequest{" +
                "reqId=" + reqId +
                ", member_ID=" + member_ID +
                ", houseID=" + houseID +
                ", reqTime='" + reqTime + '\'' +
                ", budget=" + budget +
                ", city='" + city + '\'' +
                ", mateSex='" + mateSex + '\'' +
                ", nickname='" + nickname + '\'' +
                ", job='" + job + '\'' +
                ", image='" + image + '\'' +
                ", mateCharacter='" + mateCharacter + '\'' +
                '}';
    }
}
