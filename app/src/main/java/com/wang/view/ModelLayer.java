package com.wang.view;

import android.media.AudioManager;
import android.media.SoundPool;
import com.example.wang.gametwo.R;
import com.wang.utils.ViewHelp;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;
import java.util.HashMap;

/**
 * Created by WANG on 2016/4/9.
 */
public class ModelLayer extends CCLayer {
    //存放唯一的属性，如屏幕大小、字体的格式等
    protected  CGSize windowSize = CCDirector.sharedDirector().winSize();
    protected  String font = "hkbd.ttf";
    protected ViewHelp viewHelp = ViewHelp.Instance();

    //按键声
    public static SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM,5);
    public static HashMap<Integer, Integer> musicId = new HashMap<Integer, Integer>();

    public ModelLayer (){
        setIsTouchEnabled(true);
        //按钮的声音
        musicId .put(1, soundPool.load(CCDirector.sharedDirector().getActivity(), R.raw.button_sound, 1));
        //抓到星星和敌人的声音
        musicId .put(2, soundPool .load(CCDirector.sharedDirector().getActivity(), R.raw.love, 1));
        //动物被敌人攻击的声音
        musicId .put(3, soundPool .load( CCDirector.sharedDirector().getActivity() , R.raw.tixing, 1));
        //一轮游戏失败的声音，输
        musicId.put(4, soundPool.load(CCDirector.sharedDirector().getActivity(), R.raw.girl_sound, 1));
        //一轮游戏成功的声音，赢
        musicId.put(5,soundPool.load(CCDirector.sharedDirector().getActivity(),R.raw.girl_sound_l,1));
    }


}
