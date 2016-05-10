package com.wang.utils;

import android.content.SharedPreferences;
import org.cocos2d.nodes.CCDirector;

/**
 * Created by WANG on 2016/4/9.
 */
public class LocalData {
    //保存数据到SharedPreference
    public static boolean saveInfoToPre(String key,String value){
        SharedPreferences sp = CCDirector.theApp.getSharedPreferences("config", 0);
        sp.edit().putString(key,value).commit();
        return true;
    }

    //从保存数据到SharedPreference获取数据
    public static String getInfoFromPre(String key){
        SharedPreferences sp = CCDirector.theApp.getSharedPreferences("config", 0);
        return sp.getString(key,"");
    }
}
