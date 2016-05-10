package com.wang.view;

import com.example.wang.gametwo.R;
import com.wang.utils.GameData;
import com.wang.utils.ViewService;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCBezierBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CCBezierConfig;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

/**
 * Created by WANG on 2016/4/7.
*/
public class LoginScene extends ModelLayer{
    //添加精灵的tag（id号）
    private final int tagBg = 1;
    private final int tagDayBg = 2;
    private final int tagDayLabel = 3;
    private final int tagFish = 4;
    private final int tagVersionLabel = 5;

    public LoginScene(){
        //一般都先需要游戏的初始化(在里面实现连接网络、加载图片、初始化数据库)
        //GameData.init();
        init();
        schedule("over",4f);
    }

    public void over(float f){
        unschedule("over");
        viewHelp.ChangeView(new HomeScene());
    }

    private void init() {
        //得到屏幕大小
        float wW = windowSize.getWidth();
        float hW = windowSize.getHeight();
        //添加背景图片,根据屏幕大小，缩放背景图片
        CCSprite bg =viewHelp.AddBack("background.jpg",wW, hW);
        bg.setAnchorPoint(0, 0);
        bg.setPosition(0, 0);
        this.addChild(bg,tagBg, tagBg);

        //添加桌面上跳动的鱼
        CCSprite fish = CCSprite.sprite("fish.png");
        fish.setPosition(fish.getContentSize().width/2,hW/3);
        this.addChild(fish,tagFish,tagFish);
        //设置抛物线动作
        CCBezierConfig c = new CCBezierConfig();
        c.controlPoint_1 = CGPoint.ccp(0,0);
        c.controlPoint_2 = CGPoint.ccp(wW/2,hW-fish.getContentSize().height);
        c.endPosition = CGPoint.ccp(wW-fish.getContentSize().width,hW/4);
        CCBezierBy by = CCBezierBy.action(2,c);
        //连续重复反向执行该动作
        CCSequence seq = CCSequence.actions(by,by.reverse());
        CCRepeatForever rf = CCRepeatForever.action(seq);
        fish.runAction(rf);

        //添加连续登陆天数
        CCSprite dayBg = CCSprite.sprite("dayBg.png");
        dayBg.setPosition(wW / 2, dayBg.getContentSize().height / 2 + 7);
        this.addChild(dayBg, tagDayBg, tagDayBg);

        //使用CCLabel来记录登陆的天数
        CCLabel dayLabel = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.dayBefore) + ViewService.GetDay() +
                CCDirector.theApp.getResources().getString(R.string.dayAfter), font, 40);
        dayLabel.setPosition(wW / 2, dayLabel.getContentSize().height * 3 / 2 + 4);
        this.addChild(dayLabel,tagDayLabel,tagDayLabel);

        //设置版本号
        CCLabel versionLabel = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.version)+ GameData.version,font,40);
        versionLabel.setColor(ccColor3B.ccBLUE);
        versionLabel.setPosition(wW-versionLabel.getContentSize().width, hW-versionLabel.getContentSize().height);
        this.addChild(versionLabel,tagVersionLabel,tagVersionLabel);

        //添加APP的名字
        CCLabel nameLabel = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.app_name),font,80);
       nameLabel.setColor(ccColor3B.ccBLUE);
        nameLabel.setPosition(nameLabel.getContentSize().width/2+35, hW-nameLabel.getContentSize().height-20);
        this.addChild(nameLabel,tagVersionLabel,tagVersionLabel);
    }


}
