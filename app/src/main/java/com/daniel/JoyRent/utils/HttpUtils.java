package com.daniel.JoyRent.utils;

import android.util.Log;

import com.daniel.JoyRent.beans.PersonInfo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG ="httputils" ;
    public PersonInfo login(String url, String username, String password) throws IOException {



        String regMsg = null;
        RequestBody requestBody=new FormBody.Builder()
                .add("member_password",password)
                .add("member_name", username)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();



       List<PersonInfo> jsonBeanlist =GsonUtil.jsonToList(result, PersonInfo.class);//   加.class


     if (jsonBeanlist.size()!=0)
     {
         PersonInfo jsonBean=jsonBeanlist.get(0);
         PersonInfo  login=jsonBean;
         Log.d(TAG, "user:" + jsonBean);
         return jsonBean;


     }
     else {
         PersonInfo  jsonBean1 = null;

         return  jsonBean1;
     }



    }

    public PersonInfo FindID(String url, String ID) throws IOException {





        RequestBody requestBody=new FormBody.Builder()
                .add("member_ID", ID)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();



        List<PersonInfo> jsonBeanlist =GsonUtil.jsonToList(result, PersonInfo.class);//   加.class


        if (jsonBeanlist.size()!=0)
        {
            PersonInfo jsonBean=jsonBeanlist.get(0);
            PersonInfo  login=jsonBean;
            Log.d(TAG, "user:" + jsonBean);
            return jsonBean;


        }
        else {
            PersonInfo  jsonBean1 = null;

            return  jsonBean1;
        }




    }
    public PersonInfo register(String url, String username, String password) throws IOException {


        RequestBody requestBody=new FormBody.Builder()
                .add("member_name", username)
                .add("member_password",password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();

        Gson gson = new Gson();

        PersonInfo jsonBean = gson.fromJson(result, PersonInfo.class);
        PersonInfo  register=jsonBean;

        return register;


    }

    public PersonInfo PhoneVerify(String url, String phone) throws IOException {


        RequestBody requestBody=new FormBody.Builder()
                .add("member_phone", phone)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();



        List<PersonInfo> jsonBeanlist =GsonUtil.jsonToList(result, PersonInfo.class);//   加.class


        if (jsonBeanlist.size()!=0)
        {
            PersonInfo jsonBean=jsonBeanlist.get(0);
            PersonInfo  login=jsonBean;
            Log.d(TAG, "user:" + jsonBean);
            return jsonBean;


        }
        else {
            PersonInfo  jsonBean1 = null;

            return  jsonBean1;
        }



    }

}