package com.wang.view;

import com.wang.utils.ViewHelp;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

/**
 * Created by WANG on 2016/4/16.
 */
public class ModelSprite extends CCSprite {
    //存放唯一的属性，如屏幕大小、字体的格式等
    protected CGSize windowSize = CCDirector.sharedDirector().winSize();
    protected  String font = "hkbd.ttf";
    protected ViewHelp viewHelp = ViewHelp.Instance();
    public ModelSprite (String name){
        super(CCSprite.sprite(name).displayedFrame());
    }
}
