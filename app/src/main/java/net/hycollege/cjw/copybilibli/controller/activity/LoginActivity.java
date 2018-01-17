package net.hycollege.cjw.copybilibli.controller.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.hycollege.cjw.copybilibli.R;
import net.hycollege.cjw.copybilibli.model.Model;
import net.hycollege.cjw.copybilibli.model.util.Constants;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 登录界面
 */
public class LoginActivity extends Activity {

    @BindView(R.id.et_user_account)
    EditText et_user_account;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.btn_register)
    Button btn_register;

    @BindView(R.id.tv_loginBySMS)
    TextView tv_loginBySMS;

    @BindView(R.id.tv_forgetPassword)
    TextView tv_forgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐去状态栏部分(电池等图标和一切修饰部分)，必须在激活View实例前完成
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        //初始化事件
        ButterKnife.bind(this);
    }

    //登录按钮点击事件
    @OnClick(R.id.btn_login)
    void login(View view) {
        //开启线程
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //加载对话框
                        final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, null, "正在请求中...");
                        OkHttpClient client = new OkHttpClient();
                        //创建Form表单对象，可以add多个键值队
                        FormBody formBody = new FormBody.Builder()
                                .add("user_account", et_user_account.getText().toString())
                                .add("user_password", et_password.getText().toString())
                                .build();
                        //创建一个Request
                        Request request = new Request.Builder()
                                .url(Constants.BASE_URL + "login.php")
                                .post(formBody)
                                .build();

                        //发起异步请求，并加入回调
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThreadToast("服务器异常");
                                dialog.dismiss();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                //暂存字符串
                                String s = new String(response.body().string());
                                Log.e("===cjw", "LoginActivity --- s : " + s);

                                if (s.equals("success")) {//登录成功
                                    runOnUiThreadToast("登录成功");
                                    dialog.dismiss();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                    //将用户账号存入SharedPreferences
                                    saveUserAccount();
                                    finish();
                                } else {
                                    runOnUiThreadToast("登录失败");
                                    dialog.dismiss();
                                }

                            }
                        });

                    }
                });


            }
        });
    }

    //将用户账号存入SharedPreferences
    private void saveUserAccount() {
        //初始化SharedPreferences  获取key为"isFirstUse"
        SharedPreferences preferences = getSharedPreferences("user_account", MODE_PRIVATE);
        //获取编辑对象
        SharedPreferences.Editor editor = preferences.edit();
        //把isFirstUse设置为false
        editor.putString("user_account", et_user_account.getText().toString());
        //提交改变
        editor.commit();
    }

    //在ui线程中执行Toast
    private void runOnUiThreadToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //注册按钮点击事件
    @OnClick(R.id.btn_register)
    void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    //短信登录点击事件
    @OnClick(R.id.tv_loginBySMS)
    void loginBySMS(View view) {
        Toast.makeText(this, "短信登录点击事件", Toast.LENGTH_SHORT).show();
    }

    //忘记密码点击事件
    @OnClick(R.id.tv_forgetPassword)
    void forgetPassword(View view) {
        Toast.makeText(this, "忘记密码点击事件", Toast.LENGTH_SHORT).show();
    }
}
