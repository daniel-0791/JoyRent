package com.daniel.JoyRent.beans;


import org.litepal.crud.DataSupport;

public class PersonInfo extends DataSupport {
    public String regFlag = null;
    public String regMsg = null;
    private int member_ID;
    private String member_name = null;
    private String member_password;
    private String member_image;
    private String member_idcard;
    private String member_phone;
    private String member_email;
    private String member_sex;

    public String getMember_image() {
        return member_image;
    }

    public void setMember_image(String member_image) {
        this.member_image = member_image;
    }


    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }


    public int getMember_ID() {
        return member_ID;
    }

    public void setMember_ID(int member_ID) {
        this.member_ID = member_ID;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_password() {
        return member_password;
    }

    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }

    public String getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(String member_sex) {
        this.member_sex = member_sex;
    }

    public String getMember_idcard() {
        return member_idcard;
    }

    public void setMember_idcard(String member_idcard) {
        this.member_idcard = member_idcard;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }


    public String getRegFlag() {
        return regFlag;
    }

    public void setRegFlag(String regFlag) {
        this.regFlag = regFlag;
    }

    public String getRegMsg() {
        return regMsg;
    }

    public void setRegMsg(String regMsg) {
        this.regMsg = regMsg;
    }

    @Override
    public String toString() {
        return " {\"member_sex\":\"" + member_sex + "\"," + "\"member_name\":\""
                + member_name + "\"," + "\"member_ID\":\""
                + member_ID + "\","+ "\"member_phone\":\"" + member_phone + "\","
                + "\"member_email\":\"" + member_email + "\"" + "  }";
    }
}