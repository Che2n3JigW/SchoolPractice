package net.hycollege.cjw.copybilibli.controller.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.hycollege.cjw.copybilibli.R;
import net.hycollege.cjw.copybilibli.controller.fragment.IndexFragment;
import net.hycollege.cjw.copybilibli.controller.fragment.MessageFragment;
import net.hycollege.cjw.copybilibli.controller.fragment.PartitionFragment;
import net.hycollege.cjw.copybilibli.model.Model;
import net.hycollege.cjw.copybilibli.model.bean.UserInfo;
import net.hycollege.cjw.copybilibli.model.util.Constants;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 主界面
 */
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private String user_account = "";

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.ll_rbtn_contain)
    RadioGroup ll_rbtn_contain;

    @BindView(R.id.rb_index)
    RadioButton rb_index;

    @BindView(R.id.rb_partition)
    RadioButton rb_partition;

    @BindView(R.id.rb_message)
    RadioButton rb_message;

    @BindView(R.id.fl_content)
    FrameLayout fl_content;

    private FragmentTransaction beginTransaction;
    private FragmentManager fragmentManager;


    private IndexFragment indexFragment;
    private PartitionFragment partitionFragment;
    private MessageFragment messageFragment;

    private ImageView head_iv;
    private TextView tv_user_nick;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化事件
        ButterKnife.bind(this);
        Log.e("===cjw", "进入MainActivity");

        initData();

        setListener();

        setData();

    }

    private void setData() {
        fragmentManager = getFragmentManager();
        indexFragment = new IndexFragment();
        partitionFragment = new PartitionFragment();
        messageFragment = new MessageFragment();
        rb_index.setChecked(true);
    }

    //设置事件监听
    private void setListener() {
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_cache:
                        Toast.makeText(MainActivity.this, "nav_cache", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_collection:
                        Toast.makeText(MainActivity.this, "nav_collection", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_History:
                        Toast.makeText(MainActivity.this, "nav_History", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.nav_index:
                        Toast.makeText(MainActivity.this, "nav_index", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.nav_Live:
                        Toast.makeText(MainActivity.this, "nav_Live", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.nav_service:
                        Toast.makeText(MainActivity.this, "nav_service", Toast.LENGTH_SHORT).show();

                        break;
                }
                return false;
            }
        });

        head_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "head iv", Toast.LENGTH_SHORT).show();
            }
        });


        ll_rbtn_contain.setOnCheckedChangeListener(this);


    }

    //初始化数据
    private void initData() {
        //初始化SharedPreferences  获取key为"isFirstUse"
        SharedPreferences preferences = getSharedPreferences("user_account", MODE_PRIVATE);

        //获取用户账号
        user_account = preferences.getString("user_account", "");

        nav_view.setItemTextColor(null);
        nav_view.setItemIconTintList(null);
        nav_view.setBackgroundColor(Color.BLACK);


        View headView = nav_view.inflateHeaderView(R.layout.nav_header_main);

        head_iv = (ImageView) headView.findViewById(R.id.iv_user_head);
        tv_user_nick = (TextView) headView.findViewById(R.id.tv_user_nick);

        //这里有网络请求将账号发送给服务器
        //服务器返回用户的信息
        //根据信息初始化抽屉菜单

        getDataFromNet();
//        Glide.with(MainActivity.this).load(Constants.BASE_URL + "user_head/qqq.jpg").into(head_iv);


    }

    private void getDataFromNet() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        //创建Form表单对象，可以add多个键值队
                        FormBody formBody = new FormBody.Builder()
                                .add("user_account", user_account)
                                .build();
                        //创建一个Request
                        Request request = new Request.Builder()
                                .url(Constants.BASE_URL + "getUserInfo.php")
                                .post(formBody)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThreadToast("服务器异常");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                //暂存字符串
                                String s = new String(response.body().string());
                                Log.e("===cjw", "LoginActivity --- s : " + s);

                                //解析数据
                                parseJson(s);
                            }
                        });
                    }
                });
            }
        });
    }

    //解析json
    private void parseJson(String s) {
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(s, UserInfo.class);
        String user_account = userInfo.getUser().get(0).getUser_account();
        String user_password = userInfo.getUser().get(0).getUser_password();
        final String user_nick = userInfo.getUser().get(0).getUser_nick();
        final String user_head = userInfo.getUser().get(0).getUser_head();

        Log.e("===cjw" , user_account + user_password + user_nick + user_head );

        //更新布局
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(MainActivity.this).load(Constants.BASE_URL + user_head).into(head_iv);
                tv_user_nick.setText(user_nick);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    //在ui线程中执行Toast
    private void runOnUiThreadToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.rb_index:
                //新开一个事务
                beginTransaction = fragmentManager.beginTransaction();
                beginTransaction.replace(R.id.fl_content, indexFragment);
                beginTransaction.commit();
                break;
            case R.id.rb_partition:
                beginTransaction = fragmentManager.beginTransaction();
                beginTransaction.replace(R.id.fl_content, partitionFragment);
                beginTransaction.commit();
                break;
            case R.id.rb_message:
                beginTransaction = fragmentManager.beginTransaction();
                beginTransaction.replace(R.id.fl_content, messageFragment);
                beginTransaction.commit();
                break;
            default:
                break;
        }
    }
}
