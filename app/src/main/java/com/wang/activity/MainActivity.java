package com.wang.activity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.example.wang.gametwo.R;
import com.wang.view.LoginScene;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends BaseActivity {
    private CCDirector director = CCDirector.sharedDirector();//装载导演
    public static MediaPlayer player ;
    public SharedPreferences sp ;
    private CCScene scene;
    public static boolean isPlayMusic = true;
    public static boolean isThis = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView view = new CCGLSurfaceView(this);
        setContentView(view);
        director.attachInView(view);

        //创建场景
        scene = CCScene.node();
        scene.addChild(new LoginScene());
        director.runWithScene(scene);

        //添加背景音乐
        player = MediaPlayer.create(this, R.raw.wel);
        player.setLooping(true);
        if(isPlayMusic) {
            player.start();
        }

        //显示帧率
        director.setDisplayFPS(true);
        director.setAnimationInterval(1f / 60);

    }

    @Override
    protected void onPause() {
        if(isPlayMusic) {
            player.pause();
            director.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        director.end();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if(isPlayMusic){
            player.start();
            director.onResume();
        }
        super.onResume();
    }


}
