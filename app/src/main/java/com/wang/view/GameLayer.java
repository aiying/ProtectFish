package com.wang.view;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.example.wang.gametwo.R;
import com.wang.activity.MainActivity;
import com.wang.ani.Animal;
import com.wang.utils.GameData;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCJumpTo;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor3B;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by WANG on 2016/4/10.
 */
public class GameLayer extends ModelLayer {
    //标签
    private final int tagBack = 0;
    private final int tagRiver = 1;
    private final int tagFish =2;
    private final int tagStar = 50;
    private final int tagSelectBt = 19;
    private final int tagGoItem =20;
    private final int tagBackItem = 21;
    private final int tagMenu =22;
    private int tagScore = 100;
    private int tagLabelScore = 101;
    private int tagRole = 3;
    private int tagDi = 200;

    //定义线程
    private Thread thread;
    boolean isRun = false;
    int time = 0;

    //构造函数初始化
    public GameLayer(){
        initView();
        menu();

    }
    //初始化界面
    private void initView() {
        //添加背景
        CCSprite back = viewHelp.AddBack("gameback3.png", windowSize.width, windowSize.height);
        back.setPosition(windowSize.width / 2, windowSize.height / 2);
        this.addChild(back, tagBack, tagBack);
        
        //添加流动的河和小鱼
        RiverFlow();
        schedule("RiverFlow", 20f);
        
        //装上选中的动物
        int hight = 20;
        for(Map.Entry<Integer,CCSprite> entry: GameData.aniSelectMap.entrySet()){
            CCSprite sprite = entry.getValue();
            hight += sprite.getContentSize().height/2+30;
            sprite.setScale(0.6f);
            int tag = entry.getKey();
            sprite.setPosition(sprite.getContentSize().width / 2 + 10, hight);
            this.addChild(sprite, tag, tag);
        }

        //添加开始按钮菜单背景图片
        CCSprite selectBt = CCSprite.sprite("star.png");
        selectBt.setScale(0.6f);
        selectBt.setAnchorPoint(0.5f, 1f);
        selectBt.setPosition(windowSize.width - selectBt.getContentSize().width/2+5,selectBt.getContentSize().height/2+53 );
        this.addChild(selectBt, tagSelectBt, tagSelectBt);
        //添加旋转动画
        CCRotateBy cb = CCRotateBy.action(2f, 8);
        CCRotateBy cb2 = CCRotateBy.action(2f, -8);
        CCSequence seq = CCSequence.actions(cb, cb.reverse(),cb2, cb2.reverse());
        CCRepeatForever  cf = CCRepeatForever.action(seq);
        selectBt.runAction(cf);

        //添加分数
        AddScore();

    }

    private void RiverFlow() {
        CCSprite river = CCSprite.sprite("river2.png");
        river.setPosition(windowSize.width/2,0);
        this.addChild(river, tagRiver, tagRiver);
        CCMoveBy by = CCMoveBy.action(20, CGPoint.ccp(0,windowSize.height));
        CCRepeatForever cf = CCRepeatForever.action(by);
        river.runAction(cf);

        //小鱼跳
        CCSprite fish = CCSprite.sprite("fish2.png");
        fish.setScale(0.6f);
        fish.setPosition(windowSize.width/4,windowSize.height/8);
        this.addChild(fish, tagFish, tagFish);
        CCMoveBy by2 = CCMoveBy.action(10f, CGPoint.ccp(windowSize.width / 2, 3 * windowSize.height/4));
        CCJumpBy jb = CCJumpBy.action(10f, CGPoint.ccp(windowSize.width / 2, 3 * windowSize.height / 4), windowSize.height/6,4);
        CCSpawn sp = CCSpawn.actions(by2, jb);
        CCRotateBy rb = CCRotateBy.action(0.5f,180);
        CCSequence seq = CCSequence.actions(sp, rb,sp.reverse(),rb);
        CCRepeatForever cf2 = CCRepeatForever.action(seq);
        fish.runAction(cf2);
    }

    public void RiverFlow(float f){
        CCSprite river = CCSprite.sprite("river2.png");
        river.setPosition(windowSize.width / 2, 0);
        this.addChild(river, tagRiver, tagRiver);
        CCMoveBy by = CCMoveBy.action(20, CGPoint.ccp(0,windowSize.height));
        CCRepeatForever cf = CCRepeatForever.action(by);
        river.runAction(cf);
    }

    //分数的标签
    CCSprite score;
    CCLabel lableScore;
    int food = GameData.foodMap.get("food");
    private void AddScore() {
        score = CCSprite.sprite("score.png");
        score.setPosition(windowSize.width - score.getContentSize().width / 2 - 10, windowSize.height - score.getContentSize().height / 2 - 10);
        this.addChild(score, tagScore, tagScore);
        lableScore  = CCLabel.makeLabel(""+food,font,50);
        lableScore.setColor(ccColor3B.ccc3(0xFF, 45, 00));
        lableScore.setPosition(windowSize.width-score.getContentSize().width / 2-10, windowSize.height - score.getContentSize().height / 2 - 10);
        this.addChild(lableScore, tagLabelScore, tagLabelScore);
    }

    public void ChangeFood(int food){
        //重绘分数显示
        if(lableScore !=null){
            lableScore.removeSelf();
        }
        lableScore = CCLabel.makeLabel(""+food,font,50);
        lableScore.setColor(ccColor3B.ccc3(0xFF, 45, 00));
        lableScore.setPosition(windowSize.width - score.getContentSize().width / 2 - 10, windowSize.height - score.getContentSize().height / 2 - 10);
        this.addChild(lableScore, tagLabelScore, tagLabelScore);
        GameData.foodMap.put("food",food);
    }

    CCLabel goLabel;
    CCLabel backLabel;
    boolean isGo = false;
    int menuCount = 0;
    //菜单按钮
    private void menu() {
        //添加开始菜单选项
        goLabel = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.ISPLAY),font,50);
        goLabel.setColor(ccColor3B.ccc3(0xFF, 45, 00));
        CCMenuItemLabel item = CCMenuItemLabel.item(goLabel,this, "MenuCall");
        item.setPosition(windowSize.width -  item.getContentSize().width/2 , 155);
        item.setTag(tagGoItem);

        //添加返回菜单
        backLabel = CCLabel.makeLabel(CCDirector.theApp.getResources().getString(R.string.BACK),font,50);
        backLabel.setColor(ccColor3B.ccc3(0xFF, 45, 00));
        CCMenuItemLabel itemBack = CCMenuItemLabel.item(backLabel,this, "MenuCall");
        itemBack.setPosition(windowSize.width -  item.getContentSize().width/2 , 60);
        itemBack.setTag(tagBackItem);

        CCMenu menu = CCMenu.menu(item,itemBack);
        menu.setPosition(0, 0);
        this.addChild(menu, tagMenu, tagMenu);
    }


    //菜单按钮的回调函数，必须为public
    public void MenuCall(Object obj){
        CCMenuItem item = (CCMenuItem) obj;
        int tag = item.getTag();
        soundPool.play(musicId.get(1),1,1,0,0,1);
        switch (tag){
            case tagGoItem:
                //添加星星敌人
                menuCount++;
                if(menuCount>0){
                    if(isGo){
                        isRun = false;
                        MainActivity.player.pause();
                        CCDirector.sharedDirector().pause();
                        isGo = false;
                    }else{
                        isRun = true;
                        //查看在设置中是否将music停止了，停止了就算再按开始，也不能播放，即跳出
                        if(MainActivity.isPlayMusic){
                            MainActivity.player.start();
                        }
                        //开始游戏，发射小星星，动物也开始抵御
                        CCDirector.sharedDirector().resume();
                        StartGame();
                        isGo = true;
                    }
                }
                break;
            //返回selectLayer
            case tagBackItem :
                isRun = false;
                viewHelp.ChangeView(new HomeScene());
                break;
        }
    }

    //开始游戏
    private void StartGame() {
        //添加主人翁
        AddRole();
        //发射星星
        schedule("AddStar", 1.6f);
        //动物发射子弹
        schedule("AddShoot",0.8f);
        //添加怪物
        schedule("AddMon",1f);
        //添加碰撞监听器
        schedule("Detection", 0.01f);
        //添加定时器，时间到了，说明你赢了，进入第二关
        thread =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRun){
                        Thread.sleep(1000);
                        time++;
                        if(time == GameData.FirstGameTime){
                            final Dialog[] dialog = new Dialog[1];
                            soundPool.play(musicId.get(5), 1, 1, 0, 0, 1);
                            UnSchedule();
                            CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog[0] = viewHelp.AddDialog(CCDirector.sharedDirector().getActivity(),
                                            R.string.NEXTGAME, windowSize.width, windowSize.height);
                                }
                            });
                            //进入第二关
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                        viewHelp.ChangeView(new GameSecondLayer());
                                        if(dialog[0]!=null){
                                            dialog[0].dismiss();
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //添加星星敌人
    private List<CCSprite> starList = new CopyOnWriteArrayList<CCSprite>();
    private Random rand = new Random();
    public void AddStar(float f) {
        int x = rand.nextInt((int) windowSize.width);
        int y = rand.nextInt((int) windowSize.height);
        int len = rand.nextInt(200);
        int num = rand.nextInt(8);
        CCSprite star = CCSprite.sprite("litterstar.png");
        star.setAnchorPoint(0, 0);
        star.setPosition(x, y);
        starList.add(star);
        this.addChild(star, tagStar, tagStar);
        CCJumpTo jt = CCJumpTo.action(4f,CGPoint.ccp(windowSize.width/2,windowSize.height-star.getContentSize().height),len,num);
        CCCallFuncN cf = CCCallFuncN.action(this, "StarOver");
        CCSequence seq = CCSequence.actions(jt, cf);
        star.runAction(seq);
    }

    //添加星星消失的方法
    public void StarOver(Object obj){
        CCSprite star = (CCSprite) obj;
        star.removeSelf();
        starList.remove(star);
    }

    //添加敌人
    List<CCSprite> listDi = new ArrayList<>();
    float x = windowSize.width;
    float y = windowSize.height;
    float[] pos = {3*y/4,2*y/5,5*y/6,3*y/5};
    int num = 0;
    public void AddMon(float f){
        if(num>4){
            num = 0;
        }
        if(listDi.size()>4){
            return;
        }
        num++;
       CCSprite di = CCSprite.sprite(String.format("di%d.png", num));
        if(num<3){
            di.setPosition(x-di.getContentSize().width/2-10,pos[num-1]);
        }else{
            di.setPosition(di.getContentSize().width/2+10,pos[num-1]);
        }
        this.addChild(di, tagDi, tagDi);
        listDi.add(di);

        CCMoveTo to1;
        if(num==1 || num==3){
            //添加动画
            to1 = CCMoveTo.action(10f, CGPoint.ccp(x / 2, y-di.getContentSize().height/2));
        }else{
            to1 = CCMoveTo.action(10f, CGPoint.ccp(x / 2, di.getContentSize().height/2));
        }
        CCMoveTo to2 = CCMoveTo.action(10f, CGPoint.ccp(x / 2, y / 2));
        CCSequence seq = CCSequence.actions(to1,to2);
        di.runAction(seq);
    }

    //存放的主角
    private CCSprite role;
    private Bitmap bitmep = GameData.bitmap;
    private void AddRole() {
        if(bitmep!=null){
            role = CCSprite.sprite(bitmep,null);
        }else {
            ArrayList<CCSpriteFrame> array = new ArrayList<CCSpriteFrame>();
            role = CCSprite.sprite("role.png");
            array.add(role.displayedFrame());
            role = CCSprite.sprite("role2.png");
            array.add(role.displayedFrame());
            //序列帧动画,就动了一次
            CCAnimation anim = CCAnimation.animation("", 0.5f, array);
            CCAnimate animate = CCAnimate.action(anim);
            role.runAction(CCRepeatForever.action(animate));
        }
        this.addChild(role, tagRole, tagRole);
        role.setPosition(windowSize.width / 2, windowSize.height / 2);
    }

    //添加动物发射的子弹
    public void AddShoot(float f){
        for(int i=0 ;i<touchAniList.size();i++){
            touchAniList.get(i).run();
        }
    }

    //监听器，查看碰撞事件
    public void Detection(float f) {
        //子弹打中星星，加20分，两种都消失
        for(int i=0;i<touchAniList.size();i++){
            List<CCSprite> aniShootList = touchAniList.get(i).getShootList();
            for(int j=0;j<aniShootList.size();j++){
                CCSprite shoot = aniShootList.get(j);
                CGRect shootRect = shoot.getBoundingBox();
                for(int k=0;k<starList.size();k++){
                    CCSprite star = starList.get(k);
                    CGRect starRect = star.getBoundingBox();
                    if(CGRect.intersects(shootRect,starRect)){
                        soundPool.play(musicId.get(2),1,1,0,0,1);
                        food += 20;
                        ChangeFood(food);
                        star.removeSelf();
                        starList.remove(star);
                        shoot.removeSelf();
                        aniShootList.remove(shoot);
                    }
                }
            }

        }
        //怪物到达中心，移走了主人翁，弹出对话框，输了
        for(int i=0;i<listDi.size();i++){
            CCSprite di = listDi.get(i);
            if(CGRect.intersects(di.getBoundingBox(),role.getBoundingBox())) {
                soundPool.play(musicId.get(4), 1, 1, 0, 0, 1);
                role.removeSelf();
                di.removeSelf();
                listDi.remove(di);
                UnSchedule();
                CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHelp.AddDialog(CCDirector.sharedDirector().getActivity(),
                                R.string.GAMEOVER, windowSize.width, windowSize.height);
                    }
                });
                viewHelp.ChangeView(new HomeScene());
            }
        }
        //子弹射中怪物，射中，怪物死了
        for(int i=0;i<touchAniList.size();i++){
            List<CCSprite> aniShootList = touchAniList.get(i).getShootList();
            for(int j=0;j<aniShootList.size();j++){
                CCSprite shoot = aniShootList.get(j);
                CGRect shootRect = shoot.getBoundingBox();
                for(int k=0;k<listDi.size();k++){
                    CCSprite di = listDi.get(k);
                    CGRect diRect = di.getBoundingBox();
                    if(CGRect.intersects(shootRect,diRect)){
                        food += 120;
                        ChangeFood(food);
                        di.removeSelf();
                        listDi.remove(di);
                        shoot.removeSelf();
                        aniShootList.remove(shoot);
                        soundPool.play(musicId.get(2), 1, 1, 0, 0, 1);
                    }
                }
            }
        }

        //怪物碰到动物，需要吃掉动物
        for(int i=0;i<touchAniList.size();i++){
            Animal ani = touchAniList.get(i);
            CGRect aniRect = ani.getBoundingBox();
            for(int k=0;k<listDi.size();k++){
                CCSprite di = listDi.get(k);
                CGRect diRect = di.getBoundingBox();
                if(CGRect.intersects(aniRect,diRect)){
                    soundPool.play(musicId.get(3), 1, 1, 0, 0, 1);
                    int temp = ani.getLife()-1;
                    if(temp<0){
                        ani.removeSelf();
                        touchAniList.remove(ani);
                    }
                    di.removeSelf();
                    listDi.remove(di);
                    ani.setLife(temp);
                }
            }
        }
    }

    //结束游戏
    private void UnSchedule() {
        //发射星星
        this.unschedule("AddStar");
        //动物发射子弹
        this.unschedule("AddShoot");
        //添加怪物
        this.unschedule("AddMon");
        //添加碰撞监听器
        this.unschedule("Detection");
        //清除存在的精灵
        listDi.clear();
        touchAniList.clear();
        //重置food
        ChangeFood(800);
        time = 0;
        isRun = false;
    }

    //存放战斗的动物
    private ArrayList< Animal> touchAniList = new ArrayList<>();
    //被创建的动物属性
    Animal putAni ;
    //CCSprite putSprite;
    String name = "";
    int type = 0;
    int typeShoot = 0;
    int lift = 0;
    boolean isTouch = false;
    
    public boolean ccTouchesBegan(MotionEvent event) {
        //点击屏幕上的动物，创建一个被点击的动物，屏幕中不能多于5个动物
        CGPoint point = this.convertTouchToNodeSpace(event);
        for(Map.Entry<Integer,CCSprite> entry: GameData.aniSelectMap.entrySet()){
            CCSprite touchSprite = entry.getValue();
            int tag = entry.getKey()-3;
            if(CGRect.containsPoint(touchSprite.getBoundingBox(),point)){
                soundPool.play(musicId.get(1),1,1,0,0,1);
                //屏幕中多于5个动物，就弹出一个对话框，提醒用户
                if(touchAniList.size()>4){
                    CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewHelp.AddDialog(CCDirector.sharedDirector().getActivity(),
                                    R.string.numWord,windowSize.width,windowSize.height);
                        }
                    });
                    return false;
                }
                isTouch = true;
                //根据选中的动物的tag来创建相应的动物，这些动物会发射子弹
                name = String.format("ani%d%d.png",tag,1);
                if(tag==1 || tag==5 ||tag==6 ||tag==8){
                    type = GameData.WangWang;
                    lift = GameData.WangLife;

                }else if(tag==3 || tag==7 ){
                    type = GameData.MiaoMiao;
                    lift = GameData.MiaoLife;
                }else{
                    type = GameData.Bird;
                    lift = GameData.BirdLife;
                }
                typeShoot = tag;
                putAni = new Animal(name,lift,type,typeShoot);
                //this.addChild(putAni, tagPutAni, tagPutAni);
                this.addChild(putAni,tag+2,tag+2);
                putAni.setPosition(touchSprite.getPosition());
            }
        }
        return true;
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        CGPoint point = this.convertTouchToNodeSpace(event);
        if(touchAniList.size()>4){
            return false;
        }
        if(isTouch ){
            //移动被点击的动物
            putAni.setPosition(point);
        }
        return true;
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        //放手之后就创建了这个攻击的动物
        CGPoint point = convertTouchToNodeSpace(event);
        if(touchAniList.size()>4){
            isTouch = false;
            return false;
        }

        //判断放置的位置，不在河的区域，就不能放置，即移除这个Sprite
        if(isTouch && point.x>=windowSize.width/4 && point.x<=3*windowSize.width/4 && putAni!=null){
            int tag = putAni.getTag()-2;
            if(tag==1 || tag==5 || tag==6 || tag==8){
                food -= 300;
            }else if(tag==2 || tag==4){
                food -= 200;
            }else{
                food -= 100;
            }

            //如果分数小于0，那么就填出一个对话框，表示不能再添加动物
            if(food<0 ){
                CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHelp.AddDialog(CCDirector.sharedDirector().getActivity(),
                                R.string.NoFood, windowSize.width, windowSize.height);
                    }
                });

                if(tag==1 || tag==5 || tag==6 || tag==8){
                    food += 300;
                }else if(tag==2 || tag==4){
                    food += 200;
                }else{
                    food += 100;
                }
                putAni.removeSelf();
                ChangeFood(food);
                isTouch = false;
                return false;
            }
            //分数改变之后调用一下刷新
            ChangeFood(food);
            putAni.setScale(0.8f);
            putAni.setPosition(point);
            touchAniList.add(putAni);
        }else if(isTouch && putAni!=null){
            putAni.removeSelf();
        }
        isTouch = false;
        return false;
    }
}
