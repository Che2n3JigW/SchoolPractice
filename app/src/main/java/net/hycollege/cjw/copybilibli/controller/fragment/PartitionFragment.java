package net.hycollege.cjw.copybilibli.controller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.hycollege.cjw.copybilibli.R;

/**
 * Created by Administrator on 2018/1/11.
 */

public class PartitionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.e("===cjw","PartitionFragment 初始化");
        return inflater.inflate(R.layout.fl_partition, container, false);
    }
}
