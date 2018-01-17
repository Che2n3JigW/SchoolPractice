package net.hycollege.cjw.copybilibli.controller.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/11.
 */

public class RegisterActivity extends Activity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.et_user_account)
    EditText et_user_account;

    @BindView(R.id.et_user_password)
    EditText et_user_password;

    @BindView(R.id.et_user_password2)
    EditText et_user_password2;

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐去状态栏部分(电池等图标和一切修饰部分)，必须在激活View实例前完成
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        //初始化
        ButterKnife.bind(this);

        initData();
    }


    private void initData() {
        tv_title.setText("注册");
    }

    //注册的点击事件
    @OnClick(R.id.btn_register)
    void toRegister(View view){
        //获取文本内容
        final String account = et_user_account.getText().toString();
        String password = et_user_password.getText().toString();
        final String password2 = et_user_password2.getText().toString();

        if (TextUtils.isEmpty(account)){
            Toast.makeText(this, "请输入注册账号", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }else if (!password.equals(password2)){
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
        }else {//将数据发给服务器
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //加载对话框
                            final ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, null, "正在请求中...");
                            OkHttpClient client = new OkHttpClient();
                            //创建Form表单对象，可以add多个键值队
                            FormBody formBody = new FormBody.Builder()
                                    .add("user_account", account)
                                    .add("user_password", password2)
                                    .build();
                            //创建一个Request
                            Request request = new Request.Builder()
                                    .url(Constants.BASE_URL + "register.php")
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
                                    if (s.equals("1")) {//登录成功
                                        runOnUiThreadToast("注册成功");
                                        dialog.dismiss();
                                        finish();
                                    } else {
                                        runOnUiThreadToast("注册失败");
                                        dialog.dismiss();
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }
    //在ui线程中执行Toast
    private void runOnUiThreadToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
