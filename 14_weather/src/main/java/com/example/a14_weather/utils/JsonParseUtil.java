package com.example.a14_weather.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.a14_weather.beans.JsonCityBean;
import com.example.a14_weather.beans.JsonDistrictBean;
import com.example.a14_weather.db.City;
import com.example.a14_weather.db.Province;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 2017/8/22.
 *
 *  解析json数据的工具类
 */

public class JsonParseUtil {


    /**
     * 解析省份数据:
     * @param response
     * @return
     */
    public static  boolean handlerProvinceResponse(String response){

        if(TextUtils.isEmpty(response)) {
            return false;
        }


        try {
            JSONArray allProvinces = new JSONArray(response);
            if(allProvinces!=null&&allProvinces.length()>0) {
                for (int i=0; i < allProvinces.length() ; i++){
                    JSONObject pro = allProvinces.getJSONObject(i);

                    String name = pro.optString("name");
                    int id = pro.optInt("id");
                    Province province = new Province();
                    province.setProvinceName(name);
                    province.setProvinceCode(id);
                    //province.save();
                    Log.d("JsonParseUtil", province.toString());
                }
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  false;

    }

    /**
     * 解析城市数据
     * @param response
     * @return
     */
    public static  boolean handlerCityResponse(String response){

        if(TextUtils.isEmpty(response)) {
            return false;

        }
       /*
        //1. 遍历解析
        JsonArray jarr =  new JsonParser().parse(response).getAsJsonArray();
        List<JsonCityBean> citys = new ArrayList<>();

        for (JsonElement je:jarr){
            JsonCityBean cityBean = new Gson().fromJson(je, JsonCityBean.class);
            citys.add(cityBean);
            Log.d("JsonParseUtil", cityBean.toString());
        }

        */

        //2. 使用反射解析
        /**
         *
         */
        Type type = new TypeToken<List<JsonCityBean>>(){}.getType();
        List<JsonCityBean> citys = new Gson().fromJson(response,type);

        for (JsonCityBean c:citys){

            City city = new City();
            city.setCityCode(c.getId());
            city.setCityName(c.getName());
            //city.save();
        }
        return true;
    }



    public static  boolean handlerDistrictResponse(String response){
        if(TextUtils.isEmpty(response)) {
            return false;
        }

        JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();


        for (JsonElement ja: jsonArray){
            JsonDistrictBean db = new Gson().fromJson(ja,JsonDistrictBean.class);
            Log.d("JsonParseUtil", db.toString());
        }



        return true;
    }

}
