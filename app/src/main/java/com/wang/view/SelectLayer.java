package com.wang.view;

import android.view.MotionEvent;
import com.example.wang.gametwo.R;
import com.wang.utils.GameData;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by WANG on 2016/4/10.
 */
public class SelectLayer extends ModelLayer {
    private final int tagBg = 1;
    private final int tagBasket = 2;
    private final int tagAni = 3;
    private final int tagWord = 20;
    private int tagSelectBt = 21;
    private final int tagItem = 30;
    private final int tagMenu = 31;

    int food = 800;
    CCLabel lableScore;
    CCSprite score;
    Random random = new Random();

    //存放战斗的动物
    private HashMap<Integer, CCSprite> aniMap = new HashMap<Integer, CCSprite>();
    //存放被选择的动物
    private List<CCSprite> aniListSelect = new ArrayList<CCSprite>();
    private int tagScore = 100;
    private int tagLabelScore = 101;
    private final int tagBackItem = 102;


    public SelectLayer(){
        setIsTouchEnabled(true);
        init();
        menu();
    }

    private void menu() {
        //添加开始菜单选项
        CCLabel label = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.GO), font, 50);
        label.setColor(ccColor3B.ccc3(0xFF, 45, 00));
        CCMenuItemLabel item = CCMenuItemLabel.item(label, this, "MenuCall");
        item.setPosition(windowSize.width-2*item.getContentSize().width,windowSize.height/8-5);
        item.setTag(tagItem);

        CCLabel backLabel = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.BACK), font, 50);
        backLabel.setColor(ccColor3B.ccc3(0xFF, 45, 00));
        CCMenuItemLabel itemBack = CCMenuItemLabel.item(backLabel, this, "MenuCall");
        itemBack.setPosition(windowSize.width-itemBack.getContentSize().width/2-50,windowSize.height/8-60);
        itemBack.setTag(tagBackItem);

        CCMenu menu = CCMenu.menu(item,itemBack);
        menu.setPosition(0, 0);
        this.addChild(menu, tagMenu, tagMenu);

    }

    //回调函数必须为public
    public void MenuCall(Object obj){
        CCMenuItem item = (CCMenuItem) obj;
        int tag = item.getTag();
        soundPool.play(musicId.get(1),1,1,0,0,1);
        switch (tag){
            case tagItem:
                if(GameData.aniSelectMap.size()<1){
                    //没有选择动物弹出对话框，让其选择
                    CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewHelp.AddDialog(CCDirector.sharedDirector().getActivity(),
                                    R.string.selectNoAni,windowSize.width,windowSize.height);
                        }
                    });
                }else{
                    //切换到游戏场景
                    viewHelp.ChangeView(new GameLayer());
                }
                break;
            case tagBackItem:
                viewHelp.ChangeView(new HomeScene());
                break;
        }


    }

    private void init() {
        //添加背景
        CCSprite bg =viewHelp.AddBack("gameback.jpg", windowSize.width, windowSize.height);
        bg.setPosition(windowSize.width / 2, windowSize.height / 2);
        this.addChild(bg, tagBg, tagBg);

        //添加开始菜单背景图片
        CCSprite selectBt = CCSprite.sprite("selectBt.png");
        selectBt.setAnchorPoint(0.5f, 0.5f);
        selectBt.setPosition(windowSize.width - selectBt.getContentSize().width, 3 * selectBt.getContentSize().height / 4);
        this.addChild(selectBt, tagSelectBt, tagSelectBt);
        //添加旋转动画
        CCRotateBy cbS = CCRotateBy.action(5f, 360);
        CCRepeatForever  cfS = CCRepeatForever.action(cbS);
        selectBt.runAction(cfS);

        CCSprite selectBt2 = CCSprite.sprite("selectBt.png");
        selectBt2.setAnchorPoint(0.5f, 0.5f);
        selectBt2.setPosition(windowSize.width - selectBt2.getContentSize().width/2, selectBt.getContentSize().height / 2);
        this.addChild(selectBt2, tagSelectBt, tagSelectBt);
        //添加旋转动画
        CCRotateBy cbS2 = CCRotateBy.action(8f, 360);
        CCRepeatForever  cfS2 = CCRepeatForever.action(cbS2);
        selectBt2.runAction(cfS2);


        //添加说明文字
        CCLabel word = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.selectWord), font, 40);
        word.setPosition(word.getContentSize().width/2+20,windowSize.height-word.getContentSize().height-10);
        this.addChild(word,tagWord,tagWord);


        //添加分数
        score = CCSprite.sprite("score.png");
        score.setPosition(windowSize.width-score.getContentSize().width / 2-10, windowSize.height - score.getContentSize().height / 2 - 10);
        this.addChild(score, tagScore, tagScore);
        lableScore  = CCLabel.makeLabel(""+food,font,50);
        lableScore.setColor(ccColor3B.ccc3(0xFF, 45, 00));
        lableScore.setPosition(windowSize.width-score.getContentSize().width / 2-10, windowSize.height - score.getContentSize().height / 2 - 10);
        this.addChild(lableScore, tagLabelScore, tagLabelScore);
        GameData.foodMap.put("food",food);

        //添加装动物的篮子
        CCSprite basket = CCSprite.sprite("basket.png");
        basket.setPosition(basket.getContentSize().width / 2-40, basket.getContentSize().height / 3);
        this.addChild(basket, tagBasket, tagBasket);
        //添加旋转动画
        CCRotateBy cb = CCRotateBy.action(2f, 20);
        CCSequence seq = CCSequence.actions(cb, cb.reverse());
        CCRepeatForever  cf = CCRepeatForever.action(seq);
        basket.runAction(cf);


        //在篮子中放小动物
        for(int i=1;i<=8;i++){
            int offset = random.nextInt(15);
            CCSprite sprite = CCSprite.sprite(String.format("ani%d%d.png",i,1));
            sprite.setAnchorPoint(0, 0);
            sprite.setPosition(10 + windowSize.width / 5 + 125 * ((i - 1) % 4), 25 + windowSize.height / 3 - 140 * ((i - 1) / 4));
            this.addChild(sprite, tagAni + i, tagAni + i);
            aniMap.put(sprite.getTag(), sprite);
            //aniList.add(sprite);

            //偶数动物的跳动作
            CCJumpBy by2 = CCJumpBy.action(1f, CGPoint.ccp(offset,offset),offset,1);
            CCSequence seq2 = CCSequence.actions(by2,by2.reverse());
            CCRepeatForever  cf2 = CCRepeatForever.action(seq2);
            //奇数动物的移动动作
            CCMoveBy by3 = CCMoveBy.action(1f, CGPoint.ccp(offset, 0));
            CCSequence seq3 = CCSequence.actions(by3,by3.reverse());
            CCRepeatForever  cf3 = CCRepeatForever.action(seq3);
            if(i%2==0){
                sprite.runAction(cf2);
            }else{
                sprite.runAction(cf3);
            }
        }
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        //判断选择的动物
        CGPoint point = this.convertTouchToNodeSpace(event);
        for(int i=1;i<=aniMap.size() ;i++){
            CCSprite sp = aniMap.get(i+3);
            if(CGRect.containsPoint(sp.getBoundingBox(),point)&& GameData.aniSelectMap.get(sp.getTag())==null){
                soundPool.play(musicId.get(1),1,1,0,0,1);
                sp.setVisible(false);
                CCSprite sprite2 = CCSprite.sprite(String.format("ani%d%d.png",i,1));
                int offsetX = 0;
                if(i<=4){
                    offsetX = (i-1)*100;
                }else{
                    offsetX = (i-5)*100+200;
                }
                sprite2.setPosition(windowSize.width / 5 + offsetX-30, 3 * windowSize.height / 5 + ((i - 1) % 4) * 100);
                this.addChild(sprite2, tagAni + i, tagAni + i);
                CCMoveBy by = CCMoveBy.action(1f, CGPoint.ccp(50,50));
                sprite2.runAction(by);
                aniListSelect.add(sprite2);
                GameData.aniSelectMap.put(sprite2.getTag(),sprite2);
            }
        }

        //判断已经移除去战斗的动物再回圈子里
        for(int i=1;i<=aniListSelect.size() ;i++){
            CCSprite sp = aniListSelect.get(i-1);
            if(CGRect.containsPoint(sp.getBoundingBox(),point)){
                soundPool.play(musicId.get(1),1,1,0,0,1);
                sp.removeSelf();
               aniListSelect.remove(sp);
                aniMap.get(sp.getTag()).setVisible(true);
                GameData.aniSelectMap.remove(sp.getTag());
            }
        }
        return true;
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        return false;
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        return false;
    }
}
