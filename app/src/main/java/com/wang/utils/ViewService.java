package com.wang.utils;

import android.text.TextUtils;
import java.util.Calendar;

/**
 * Created by WANG on 2016/4/9.
 */
public class ViewService{
    /**
     * 获取累计天数
     */
    private static String key = "days";
    private static String keyVersion = "version";
    public static String GetDay() {
        int day = 1;
        //获取今天在一年中位置
        Calendar calendar = Calendar.getInstance();
        int dayActual = calendar.get(Calendar.DAY_OF_YEAR);
        //如果sharedPreference里面已经有天数了，那就需要判断是不是今天，不是就将累计天数+1，是就不修改
        //在sharedPreference存放的格式是：第几天_这一天在一年中的位置
        String str = LocalData.getInfoFromPre(key);
        String[] dayBefore = str.split("_");
        if (!TextUtils.isEmpty(str)) {
            if (dayActual == Integer.valueOf(dayBefore[1])) {
                return dayBefore[0];
            } else {
                day = Integer.valueOf(dayBefore[0]) + 1;
            }
        }
        LocalData.saveInfoToPre(key, day + "_" + dayActual);
        return String.valueOf(day);
    }

    /**
     * 设置版本号
     */
    public static void SetVersion(int num){
        LocalData.saveInfoToPre(keyVersion,String.valueOf(num));
    }

    /**
     * 获取版本号
     */
    public static String GetVersion(){
        return LocalData.getInfoFromPre(keyVersion);
    }
}
