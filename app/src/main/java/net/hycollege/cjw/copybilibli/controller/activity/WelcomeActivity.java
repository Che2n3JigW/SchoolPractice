package net.hycollege.cjw.copybilibli.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import net.hycollege.cjw.copybilibli.R;
import net.hycollege.cjw.copybilibli.model.Model;

/**
 * 该类为打开app时候用户见到的第一个界面
 */
public class WelcomeActivity extends Activity {

    //判断是否第一次进入app标志
    private boolean isFirstUse;
    //当前Context
    private Context context = WelcomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐去状态栏部分(电池等图标和一切修饰部分)，必须在激活View实例前完成
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //布局
        setContentView(R.layout.activity_welcome);

        //开启线程池
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //延迟2秒
                    Thread.sleep(2000);

                    //初始化SharedPreferences  获取key为"isFirstUse"
                    SharedPreferences preferences = getSharedPreferences("isFirstUse", MODE_PRIVATE);

                    //默认为true(第一次打开app)
                    isFirstUse = preferences.getBoolean("isFirstUse", true);
                    Log.e("===cjw", "WelcomeActivity isFirstUse = " + isFirstUse);

                    /**
                     * 当isFirstUse为true的时候进入引导界面
                     * 当isFirstUse为false的时候进入
                     */
                    if (isFirstUse) {
                        //获取编辑对象
                        SharedPreferences.Editor editor = preferences.edit();
                        //把isFirstUse设置为false
                        editor.putBoolean("isFirstUse", false);
                        //提交改变
                        editor.commit();
                        //开启引导界面活动类
                        startActivity(new Intent(context, GuideActivity.class));
                        //关闭当前活动类
                        finish();
                    } else {
                        //开启登录界面活动类
                        startActivity(new Intent(context, LoginActivity.class));
                        //关闭当前活动类
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //关闭当前活动类
                    finish();
                }
            }
        });

    }

    //销毁的时候
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭当前活动类
        finish();
    }
}
