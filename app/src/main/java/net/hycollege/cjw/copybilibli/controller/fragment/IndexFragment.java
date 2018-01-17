package net.hycollege.cjw.copybilibli.controller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import net.hycollege.cjw.copybilibli.R;

/**
 * Created by Administrator on 2018/1/11.
 */

public class IndexFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.e("===cjw","IndexFragment 初始化");

        View view = inflater.inflate(R.layout.fl_index, container, false);
        WebView webView = view.findViewById(R.id.webView);
        //加载网页
        webView.loadUrl("file:///android_asset/MUIDemo/index.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置支持javascript
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        return view;
    }


}
