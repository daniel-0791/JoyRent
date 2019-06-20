package com.daniel.JoyRent.House.model;

import android.util.Log;

import com.daniel.JoyRent.House.widget.HousesDetailActivity;
import com.daniel.JoyRent.beans.HousesBean;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.utils.GsonUtil;
import com.daniel.JoyRent.utils.OkHttpUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

public class HousesModelImpl implements HousesModel {

    /**
     * 加载房源列表
     * @param url
     * @param listener
     */
    //HousesPresenterImpl 调用  Presenter负责逻辑的处理，Model提供数据，View负责显示
    @Override
    public void loadHouses(String url,final int type,final OnLoadHousesListListener listener) {//HousesPresenterImpl 调用 impl接口继承层
        Log.d("HousesModelImpl",String.valueOf(type));


        OkHttpUtils.ResultCallback<String> loadHousesCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
//HousesPresenterImpl 调用


                List<HousesBean> housesBeanList = null;
                /**
                 * 访问不到网络的时候下一部是到达不了的，loadhouses里是callback
                 */
                try {
                   housesBeanList =GsonUtil.jsonToList(response, HousesBean.class);//   加.class   获得列表数据

                } catch (Exception e) {
                    e.printStackTrace();


                }

                /**
                 * 存储列表数据
                 */
                if (housesBeanList==null)
                {
                    List<HousesBean> NoOnline=DataSupport.select("*")

                            .find(HousesBean.class);
                    housesBeanList=NoOnline;

                }

                for (int i = 0; i < housesBeanList.size(); i++) {
                    int n=housesBeanList.get(i).getHouseID();
                    /**
                     * 在数据库查找有没有房源，若无，添加。若有，更新容量，这里若不连数据库，容量每次都会刷新到初始
                     */
                    List<HousesBean> findRepetition=DataSupport.select("*")
                            .where("houseID = ?",String.valueOf(n))
                            .find(HousesBean.class);
                    if (findRepetition.size()==0)
                    {
                        HousesBean housesBean=housesBeanList.get(i);

                        housesBean.save();
                    }
                    else
                    {
                        HousesBean housesBean=new HousesBean();
                        housesBean.setCapacity(housesBeanList.get(i).getCapacity());
                        housesBean.update(n);


                    }


                }

                //DataSupport.saveAll(housesBeanList);
                List<HousesBean>   housesBeanList1 = null;
                if(type==1)
                {
                    housesBeanList1=getType(type);
                }
                else  if(type==2)
                {

                    housesBeanList1=getType(type);
                }
                else  if(type==3)
                {

                    housesBeanList1=getType(type);
                }
                else  if(type==4)
                {

                    housesBeanList1=getType(type);
                }
                else  if(type==5)
                {

                    housesBeanList1=getType(type);
                }
                else
                {
                    housesBeanList1=housesBeanList;
                }
                Log.d("HousesModelImpl",String.valueOf(housesBeanList1.size()));
                //

                listener.onSuccess(housesBeanList1);//这行被我误删了
            }
;
            @Override
            public void onFailure(Exception e) {
                List<HousesBean> housesBeanList = null;
                List<HousesBean> NoOnline=DataSupport.findAll(HousesBean.class);
                housesBeanList=NoOnline;
                listener.onSuccess(housesBeanList);//这行被我误删了
              //  listener.onFailure("load house list failure.");
            }
        };
        OkHttpUtils.get(url, loadHousesCallback);
    }



    /**
     * 加载房源详情
     * @param docid
     * @param listener
     */
    @Override
    public void loadHousesDetail(final String docid,final OnLoadHouseDetailListener listener) {
       // String url = getDetailUrl(docid);
        String url=Urls.HOUSEStest+51651;
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
        //
               // HousesBean housesBean
          //      listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                String msg=HousesDetailActivity.mHouses.getDescription();
                listener.onFailure(msg);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }

            public  List<HousesBean> getType(int type)
            {
                if (type==2)
                {
                    List<HousesBean> housesBeans=DataSupport.select("*")
                            .where("rentPrice < ?","2000")
                            .find(HousesBean.class);
                    //.findBySQL("select * from HousesBean where rentPrice between ? and ?","500","2000");

                    return housesBeans;
                }
                else if(type==1)
                {
                    List<HousesBean> housesBeans=DataSupport.select("*")
                            .where("way = ?","室友合租")
                            .find(HousesBean.class);
                    //.findBySQL("select * from HousesBean where rentPrice between ? and ?","500","2000");
                    Log.d("return",String.valueOf(housesBeans.size()));
                    return housesBeans;
                }
                else if(type==3)
                {
                    List<HousesBean> housesBeans=DataSupport.select("*")
                            .find(HousesBean.class);
                    //.findBySQL("select * from HousesBean where rentPrice between ? and ?","500","2000");
                    Log.d("return",String.valueOf(housesBeans.size()));
                    return housesBeans;
                }
                else if(type==4)
                {
                    List<HousesBean> housesBeans=DataSupport.select("*")
                            .where("sex = ?","女")
                            .find(HousesBean.class);

                    Log.d("return",String.valueOf(housesBeans.size()));
                    return housesBeans;
                }
                else if(type==5)
                {
                    List<HousesBean> housesBeans=DataSupport.select("*")
                            .where("area = ?","南昌县")
                            .find(HousesBean.class);

                    Log.d("return",String.valueOf(housesBeans.size()));
                    return housesBeans;
                }
                else

                {
                    return  null;
                }

            }

}
