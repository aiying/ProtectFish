package com.wang.ani;

import com.wang.utils.GameData;
import com.wang.view.ModelSprite;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by WANG on 2016/4/12.
 */
public class Animal extends ModelSprite {
    private CCSprite sprite;
    private CCSprite shoot;
    private int life;
    private int type;
    private int typeShoot;
    private int tagMiao = 1000;
    private int tagWang = 1000;
    private CGPoint point ;
    private List<CCSprite> shootList = new CopyOnWriteArrayList<>();


    public Animal(String name,int life,int type,int typeShoot)
    {
        super(name);
        sprite = CCSprite.sprite(name);
        point = sprite.getPosition();
        this.life = life;
        this.type = type;
        this.typeShoot = typeShoot;
        //序列帧动画,就动了一次
        CCAnimation anim = CCAnimation.animation("",0.5f,viewHelp.getSpriteArray(typeShoot));
        CCAnimate animate = CCAnimate.action(anim);
        this.runAction(CCRepeatForever.action(animate));

    }

    public void setLife(int life){
        this.life = life;
    }

    public int getLife(){
        return life;
    }

    public void run(){
        if(type == GameData.WangWang){
            CreateWang();
        }else if(type == GameData.MiaoMiao){
            CreateMiao();
        }else {
            CreateOther();
        }
    }

    //产生狗类动物的攻击物品，绕着狗转圈
    public void CreateWang(){
        shoot = CCSprite.sprite("wang.png");
        shoot.setAnchorPoint(3,3);
        shoot.setPosition(point.x + sprite.getContentSize().width / 2, point.y + sprite.getContentSize().height / 2);
        this.addChild(shoot, tagWang, tagWang);
        shootList.add(shoot);
        //添加旋转动画
        CCRotateBy cbS = CCRotateBy.action(4f, 360);
        CCCallFuncN fun = CCCallFuncN.action(this,"ShootOver");
        CCSequence seq = CCSequence.actions(cbS,fun);
        shoot.runAction(seq);

    }

    public void createA(String name,int[][] pos){
        shoot = CCSprite.sprite(name);
        shoot.setPosition(point.x+sprite.getContentSize().width/2,point.y+sprite.getContentSize().height/2);
        this.addChild(shoot, tagMiao, tagMiao);
        shootList.add(shoot);
        if(count>3){
            count=0;
        }
        //添加移动的动画
        CCMoveTo to = CCMoveTo.action(2f,CGPoint.ccp(point.x+pos[count][0],point.y+pos[count][1]));
        CCCallFuncN fun = CCCallFuncN.action(this,"ShootOver");
        CCSequence seq = CCSequence.actions(to, fun);
        shoot.runAction(seq);
        count++;
    }
    //子弹消失动画
    public void ShootOver(Object obj){
        CCSprite spr = (CCSprite) obj;
        spr.removeSelf();
        shootList.remove(spr);
    }

    //猫的攻击物品，在方圆1/8的屏幕中发射4个想法的物品
    int[][] posMiao = {{200,0},{0,-200},{-200,0},{0,200}};
    int count =0;
    public void CreateMiao(){
        createA("miao.png",posMiao);
    }

    //产生其他类动物的攻击物品,设计距离不限
    int[][] posOther = {{300,300},{300,-300},{-300,-300},{-300,300}};
    public void CreateOther(){
        createA("vagetable.png", posOther);
    }

    //摧毁这个动物
    public void IsDestroy(){

    }

    public int getType() {
        return this.type;
    }

    public List<CCSprite> getShootList(){
        return shootList;
    }
}
