package com.wang.utils;

import android.graphics.Bitmap;
import org.cocos2d.nodes.CCSprite;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WANG on 2016/4/10.
 */
public class GameData {
    //游戏的版本号
    public static int version = 1;

    //选中战斗的动物
    public static Map<Integer, CCSprite> aniSelectMap = new HashMap<Integer, CCSprite>();

    //动物的种类
    public static int WangWang =1;
    public static int MiaoMiao =2;
    public static int Bird =3;

    //动物的生命值
    public static int WangLife = 5;
    public static int MiaoLife =3;
    public static int BirdLife =3;

    //保存食物的记录值
    public static Map<String, Integer> foodMap = new HashMap<String, Integer>();

    //保存自选图片
    public static Bitmap bitmap = null;

    //第一关、第二关游戏的界限时间
    public static int FirstGameTime = 70;
    public static long SecondGameTime =70;


}
