package net.hycollege.cjw.copybilibli.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.hycollege.cjw.copybilibli.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;


public class GuideActivity extends Activity {

    //背景
    @BindView(R.id.banner_guide_background)
    BGABanner banner_guide_background;
    //前景
    @BindView(R.id.banner_guide_foreground)
    BGABanner banner_guide_foreground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //初始化事件
        ButterKnife.bind(this);

        //设置banner的监听
        setListener();

        //初始化数据
        initData();

        //设置图片数据源
        setData();

    }

    //初始化数据
    private void initData() {
        banner_guide_foreground.setTransitionEffect(TransitionEffect.Cube);
    }

    //设置图片数据源
    private void setListener() {
        banner_guide_foreground.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    //设置数据
    private void setData() {
        // 设置数据源
        banner_guide_background.setData(R.drawable.ic_guide_1, R.drawable.ic_guide_2, R.drawable.ic_guide_3);
        banner_guide_foreground.setData(R.drawable.ic_guide_1, R.drawable.ic_guide_2, R.drawable.ic_guide_3);
    }

    //当数据恢复的时候执行
    @Override
    protected void onResume() {
        super.onResume();
        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        banner_guide_background.setBackgroundResource(android.R.color.white);
    }
}