package com.cd.ijkplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.cd.chenplayview.media.IjkPlayerView;

/**
 * 项目名称：IjKPlayer.
 * 创建人： CT.
 * 创建时间: 2017/4/25.
 * GitHub:https://github.com/CNHTT
 */
public class IjkFullscreenActivity extends AppCompatActivity {

    private static final String VIDEO_URL = "http://112.5.254.242/hd.yinyuetai.com/uploads/videos/common/3152015C7C38662FB9BBE92D4A466233.mp4?sc\\u003d9cb81249ac74e371\\u0026br\\u003d1098\\u0026vid\\u003d2881449\\u0026aid\\u003d277\\u0026area\\u003dHT\\u0026vst\\u003d0";

    private static final String IMAGE_URL = "http://img3.c.yinyuetai.com/video/mv/170606/2881327/-M-65b83109efa0417743c2ea3704c2eb30_240x135.jpg";
    IjkPlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerView = new IjkPlayerView(this);
        setContentView(mPlayerView);
        Glide.with(this).load(IMAGE_URL).fitCenter().into(mPlayerView.mPlayerThumb);
        mPlayerView.init()
                .alwaysFullScreen()
                .enableOrientation()
                .setVideoPath(VIDEO_URL)
//                .enableDanmaku()
//                .setDanmakuSource(getResources().openRawResource(R.raw.bili))
                .setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
                .start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}

