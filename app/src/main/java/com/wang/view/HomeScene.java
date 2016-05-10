package com.wang.view;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.wang.gametwo.R;
import com.wang.utils.GameData;
import org.cocos2d.actions.ease.CCEaseInOut;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WANG on 2016/4/9.
 */
public class HomeScene extends ModelLayer {
    private final int tagBg = 1;
    private final int tagFish = 2;
    private final int tagFish2 = 3;

    private final int tagStart = 5;
    private final int tagSetting = 6;
    private final int tagHelp = 7;
    private final int tagBack = 8;
    private final int tagMenu = 9;
    public HomeScene(){
        init();
        //创建菜单menu();
        menu();
    }

    private void menu() {
        //s设置菜单条目
        CCMenuItemImage start = CCMenuItemImage.item("btStartBefore.png","btStartAfter.png",this,"MenuCall");
        start.setPosition(windowSize.width / 2+53, 27*windowSize.height/ 32);
        start.setTag(tagStart);
        CCMenuItemImage setting = CCMenuItemImage.item("btSettingBefore.png","btSettingAfter.png",this,"MenuCall");
        setting.setPosition(windowSize.width / 2 + 53, 21 * windowSize.height / 32);
        setting.setTag(tagSetting);
        CCMenuItemImage help = CCMenuItemImage.item("btHelpBefore.png","btHelpAfter.png",this,"MenuCall");
        help.setPosition(windowSize.width / 2+7, 13*windowSize.height/ 32);
        help.setTag(tagHelp);
        CCMenuItemImage back = CCMenuItemImage.item("btBackBefore.png", "btBackAfter.png", this, "MenuCall");
        back.setPosition(windowSize.width / 2+7, 7 * windowSize.height / 32);
        back.setTag(tagBack);

        CCMenu menu = CCMenu.menu(start,setting,help,back);
        menu.setPosition(0, 0);
        this.addChild(menu,tagMenu,tagMenu);

    }

    //存放动物说明的List
    ArrayList<HashMap<String, Object>> list ;
    SimpleAdapter adapter;
    public void MenuCall(Object obj){
        soundPool.play(musicId.get(1),1,1,0,0,1);
        CCMenuItem item = (CCMenuItem) obj;
        int tag = item.getTag();
        switch (tag){
            case tagStart:
                //选择防御动物页面
                viewHelp.ChangeView(new SelectLayer());
                break;
            case tagSetting:
                viewHelp.ChangeView(new SettingLayer());
                break;
            case tagHelp:
                //点击帮助按钮，弹出一个对话框，说明游戏,因为是改变UI线程，所以需要异步操作
                CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //弹出一个对话框，解释各种动物的说明
                        LayoutInflater inflater = LayoutInflater.from(CCDirector.sharedDirector().getActivity());
                        View view = inflater.inflate(R.layout.ani_help_layout, null);
                        final Dialog dialog = new Dialog(CCDirector.sharedDirector().getActivity(), R.style.dialogStyle);
                        dialog.setContentView(view);

                        //放置里面具体动物的说明
                        list = new ArrayList<>();
                        adapter = new SimpleAdapter(CCDirector.sharedDirector().getActivity(),
                                getData(),R.layout.ani_help_item,new String[]{"image","name","des"},new int[]{R.id.image,R.id.textName,R.id.textDes});
                        ListView listView = (ListView) view.findViewById(R.id.listView);
                        listView.setAdapter(adapter);

                        dialog.show();

                        Button button = (Button) view.findViewById(R.id.button);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                soundPool.play(musicId.get(1),1,1,0,0,1);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                break;
            case tagBack:
                //退出游戏
                CCDirector.sharedDirector().getActivity().finish();
                break;
        }
    }

    //添加适配器里面的内容
    private List<? extends Map<String,?>> getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("image",R.drawable.ani1);
        //map.put("word", R.string.ani1);输出的是这个字符串的id号
        map.put("name","大汪");
        map.put("des","食量：300；生命值：5；攻击力：循环扔骨头");
        list.add(map);

        map = new HashMap<>();
        map.put("image",R.drawable.ani2);
        map.put("name", "生气鸟");
        map.put("des","食量：200；生命值：3；攻击力：丢青菜");
        list.add(map);

        map = new HashMap<>();
        map.put("image",R.drawable.ani3);
        map.put("name", "柔柔猫");
        map.put("des","食量：100；生命值：3；攻击力：哈哈，看");
        list.add(map);

        map = new HashMap<>();
        map.put("image",R.drawable.ani4);
        map.put("name", "萌兔");
        map.put("des","食量：200；生命值：3；攻击力：丢青菜");
        list.add(map);

        map = new HashMap<>();
        map.put("image",R.drawable.ani5);
        map.put("name", "大嘴汪");
        map.put("des","食量：300；生命值：5；攻击力：循环扔骨头");
        list.add(map);

        map = new HashMap<>();
        map.put("image",R.drawable.ani6);
        map.put("name", "长毛犬");
        map.put("des","食量：300；生命值：5；攻击力：循环扔骨头");
        list.add(map);

        map = new HashMap<>();
        map.put("image",R.drawable.ani7);
        map.put("name", "瘦皮狗");
        map.put("des","食量：300；生命值：5；攻击力：循环扔骨头");
        list.add(map);

        map = new HashMap<>();
        map.put("image",R.drawable.ani8);
        map.put("name", "大肥猫");
        map.put("des","食量：100；生命值：3；攻击力：哈哈，看");
        list.add(map);

        return list;
    }

    private void init() {
        //第二个场景，主菜单
        //添加背景
        CCSprite bg =viewHelp.AddBack("homeback.jpg", windowSize.width, windowSize.height);
        bg.setPosition(windowSize.width / 2, windowSize.height / 2);
        this.addChild(bg, tagBg, tagBg);
        //添加游动的小鱼
        FishFlow(0f);
        this.schedule("FishFlow", 3f);

        //清楚原有的数据
        GameData.aniSelectMap.clear();
        System.out.print(GameData.aniSelectMap.isEmpty());

    }

    public void FishFlow(float f){
        //添加右移游动的小鱼
        CCSprite fish = CCSprite.sprite("homeFish.png");
        fish.setPosition(-fish.getContentSize().width/2, windowSize.height / 5);
        this.addChild(fish,tagFish,tagFish);
        //添加游动的动画
        CCMoveBy by = CCMoveBy.action(5, CGPoint.ccp( windowSize.width+fish.getContentSize().width,35));
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
