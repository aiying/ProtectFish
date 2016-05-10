package com.wang.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wang.gametwo.R;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import java.util.ArrayList;

/**
 * Created by WANG on 2016/4/9.
 */
public class ViewHelp {
    //单例的方式创建，保证这个app中只有一个
    private static ViewHelp viewHelp;

    private ViewHelp(){

    }
    public static ViewHelp Instance(){
        if(viewHelp==null){
            viewHelp = new ViewHelp();
        }
        return viewHelp;
    }

    //替换场景
    public void ChangeView(CCLayer layer){
        CCScene scene = CCScene.node();
        scene.addChild(layer);
        CCDirector.sharedDirector().replaceScene(scene);
    }

    //添加背景
    public CCSprite AddBack(String path,float wW,float hW){
        //添加背景图片,根据屏幕大小，缩放背景图片
        CCSprite bg = CCSprite.sprite(path);
        float scaleX = wW/bg.getContentSize().width;
        float scaleY = hW/bg.getContentSize().height;
        bg.setScaleX(scaleX);
        bg.setScaleY(scaleY);
        return bg;
    }

    //添加对话框
    public Dialog AddDialog(Activity activity,int idWord,float wW,float hW){
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.helplayer_xml, null);
        final TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(idWord);
        final Dialog dialog = new Dialog(activity, R.style.dialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = 0; // 新位置X坐标
        lp.y = (int) hW / 4; // 新位置Y坐标
        lp.width = (int) wW; // 宽度
        lp.height = (int) hW / 2; // 高度
        dialogWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    //创建序列帧，就是动物变化的动画
    public ArrayList<CCSpriteFrame> getSpriteArray(int type){
        ArrayList<CCSpriteFrame> array= new ArrayList<CCSpriteFrame>();
        switch (type) {
            case 1:
                for (int i = 1; i <= 3; i++) {
                    CCSprite sp = CCSprite.sprite(String.format("ani1%d.png", i));
                    array.add(sp.displayedFrame());
                }
                break;
            case 2:
                for (int i = 1; i <= 3; i++) {
                    CCSprite sp = CCSprite.sprite(String.format("ani2%d.png", i));
                    array.add(sp.displayedFrame());
                }
                break;
            case 3:
                for (int i = 1; i <= 3; i++) {
                    CCSprite sp = CCSprite.sprite(String.format("ani3%d.png", i));
                    array.add(sp.displayedFrame());
                }
                break;
            case 4:
                for (int i = 1; i <= 3; i++) {
                    CCSprite sp = CCSprite.sprite(String.format("ani4%d.png", i));
                    array.add(sp.displayedFrame());
                }
                break;
            case 5:
                for (int i = 1; i <= 3; i++) {
                    CCSprite sp = CCSprite.sprite(String.format("ani5%d.png", i));
                    array.add(sp.displayedFrame());
                }
                break;
            case 6:
                for (int i = 1; i <= 3; i++) {
                    CCSprite sp = CCSprite.sprite(String.format("ani6%d.png", i));
                    array.add(sp.displayedFrame());
                }
                break;
            case 7:
                for (int i = 1; i <= 3; i++) {
                    CCSprite sp = CCSprite.sprite(String.format("ani7%d.png", i));
                    array.add(sp.displayedFrame());
                }
                break;
            case 8:
                for (int i = 1; i <= 3; i++) {
                    CCSprite sp = CCSprite.sprite(String.format("ani8%d.png", i));
                    array.add(sp.displayedFrame());
                }
                break;

        }
        return array;
    }

}
