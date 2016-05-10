package com.wang.view;

import android.content.Intent;
import android.view.MotionEvent;
import com.example.wang.gametwo.R;
import com.wang.activity.FreeActivity;
import com.wang.activity.MainActivity;
import com.wang.utils.GameData;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.ease.CCEaseInOut;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor3B;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANG on 2016/4/10.
 */
public class SettingLayer extends ModelLayer{
    private int tagBack = 1;
    private int tagSelectBt = 2;
    private int tagFish = 2;
    private int tagFish2 = 2;
    private final int tagItem1 = 5;
    private final int tagItem2 = 6;
    private final int tagItem3 = 7;
    private final int tagBackItem = 8;
    private int tagMenu = 10;
    private int tagMusic = 5;

    public SettingLayer(){
        init();
        menu();
        AddButton();
    }
    List<CCSprite> musicList;
    List<CCSprite> thisList;
    List<CCSprite> freeList;
    private void AddButton() {

        musicList = AddButtonItem("btMusicBefore.png","select.png","noselect.png",tagMusic,
                    CGPoint.ccp(windowSize.width/3,3*windowSize.height/4+15),CGPoint.ccp(windowSize.width/3+230,3*windowSize.height/4+15),
                isMusic);
        thisList = AddButtonItem("btThisBefore.png","select.png","noselect.png",tagMusic,
                CGPoint.ccp(windowSize.width/2,windowSize.height/2-30),CGPoint.ccp(windowSize.width/2+230,windowSize.height/2-30),
                isThis);
        freeList = AddButtonItem("btFreeBefore.png","noselect.png","select.png",tagMusic,
                CGPoint.ccp( windowSize.width / 2, windowSize.height / 3-60),CGPoint.ccp(windowSize.width /2+230, windowSize.height / 3-60),
                isThis);
    }

    private List<CCSprite> AddButtonItem(String logo,String bt1Name,String bt2Name,int tag,CGPoint pos1,CGPoint pos2,boolean isPlay) {
        List<CCSprite> list = new ArrayList<>();
        CCSprite music = CCSprite.sprite(logo);
        music.setPosition(pos1);
        this.addChild(music, tag, tag);
        CCSprite bt1 = CCSprite.sprite(bt1Name);
        bt1.setPosition(pos2);
        CCSprite bt2 = CCSprite.sprite(bt2Name);
        bt2.setPosition(pos2);
        if(isPlay){
            bt2.setVisible(false);
        }else{
            bt1.setVisible(false);
        }
        this.addChild(bt1, tag, tag);
        this.addChild(bt2, tag, tag);
        list.add(bt1);
        list.add(bt2);
        return list;
    }

    boolean isMusic = MainActivity.isPlayMusic;
    boolean isThis = MainActivity.isThis;
    public boolean ccTouchesBegan(MotionEvent event) {
        soundPool.play(musicId.get(1),1,1,0,0,1);
        CGPoint point = this.convertTouchToNodeSpace(event);
        if(CGRect.containsPoint(musicList.get(0).getBoundingBox(), point) ||
                CGRect.containsPoint(musicList.get(1).getBoundingBox(), point)){
            if(isMusic){
                MainActivity.player.pause();
                musicList.get(0).setVisible(false);
                musicList.get(1).setVisible(true);
            }else{
                MainActivity.player.start();
                musicList.get(0).setVisible(true);
                musicList.get(1).setVisible(false);
            }
            isMusic = !isMusic;
            MainActivity.isPlayMusic = !MainActivity.isPlayMusic;
        }

        if(CGRect.containsPoint(thisList.get(0).getBoundingBox(), point) ||
                CGRect.containsPoint(thisList.get(1).getBoundingBox(), point)||
                CGRect.containsPoint(freeList.get(0).getBoundingBox(), point) ||
                CGRect.containsPoint(freeList.get(1).getBoundingBox(), point)){
            isThis = !isThis;
            MainActivity.isThis = !MainActivity.isThis;
            if(isThis){
                GameData.bitmap = null;
                thisList.get(0).setVisible(true);
                thisList.get(1).setVisible(false);
                freeList.get(0).setVisible(true);
                freeList.get(1).setVisible(false);
            }else{
                thisList.get(0).setVisible(false);
                thisList.get(1).setVisible(true);
                freeList.get(0).setVisible(false);
                freeList.get(1).setVisible(true);
                if(isMusic){
                    MainActivity.player.pause();
                }
                CCDirector.sharedDirector().getActivity().startActivity(
                        new Intent(CCDirector.sharedDirector().getActivity(), FreeActivity.class));
            }
        }
        return true;
    }

    private void menu() {
        //返回的菜单
        CCLabel backLabel = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.SURE), font, 50);
        backLabel.setColor(ccColor3B.ccc3(0xFF, 45, 00));
        CCMenuItemLabel itemBack = CCMenuItemLabel.item(backLabel, this, "CallMenu");
        itemBack.setPosition(windowSize.width-itemBack.getContentSize().width/2-50,windowSize.height/8-60);
        itemBack.setTag(tagBackItem);

        CCMenu menu = CCMenu.menu(itemBack);
        menu.setPosition(0, 0);
        this.addChild(menu, tagMenu, tagMenu);
    }

    public void CallMenu(Object obj){
        CCMenuItem item = (CCMenuItem) obj;
        int tag = item.getTag();
        soundPool.play(musicId.get(1),1,1,0,0,1);
        switch (tag){
            case tagBackItem:
                viewHelp.ChangeView(new HomeScene());
                break;

        }
    }

    public void init(){
        //添加背景
        CCSprite back = viewHelp.AddBack("homeback.jpg", windowSize.width, windowSize.height);
        back.setPosition(windowSize.width/2,windowSize.height/2);
        this.addChild(back,tagBack,tagBack);

        //添加开始菜单背景图片
        CCSprite selectBt = CCSprite.sprite("selectBt.png");
        selectBt.setScale(0.7f);
        selectBt.setAnchorPoint(0.5f, 0.5f);
        selectBt.setPosition(windowSize.width - selectBt.getContentSize().width/2,  selectBt.getContentSize().height / 2);
        this.addChild(selectBt, tagSelectBt, tagSelectBt);
        //添加旋转动画
        CCRotateBy cbS = CCRotateBy.action(5f, 360);
        CCRepeatForever cfS = CCRepeatForever.action(cbS);
        selectBt.runAction(cfS);

        //添加游动的小鱼
        FishFlow(0f);
        this.schedule("FishFlow", 3f);

    }

    public void FishFlow(float f){
        //添加右移游动的小鱼
        CCSprite fish = CCSprite.sprite("homeFish.png");
        fish.setPosition(-fish.getContentSize().width/2, 4*windowSize.height / 5);
        this.addChild(fish,tagFish,tagFish);
        //添加游动的动画
        CCMoveBy by = CCMoveBy.action(5, CGPoint.ccp(windowSize.width + fish.getContentSize().width, 35));
        fish.runAction(by);

        //添加左移游动的小鱼
        CCSprite fish2 = CCSprite.sprite("homeFish.png");
        fish2.setFlipX(true);
        fish2.setPosition(windowSize.width+fish.getContentSize().width/2, 4*windowSize.height / 5);
        this.addChild(fish2,tagFish2,tagFish2);
        //添加游动的动画
        CCMoveBy by2 = CCMoveBy.action(5, CGPoint.ccp( -windowSize.width-fish2.getContentSize().width,-3*windowSize.height / 5));
        CCEaseInOut inOut = CCEaseInOut.action(by2,2);
        CCSpawn sp = CCSpawn.actions(by2,inOut);
        fish2.runAction(sp);
    }


}
