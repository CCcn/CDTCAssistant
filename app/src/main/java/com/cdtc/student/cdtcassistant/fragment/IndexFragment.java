package com.cdtc.student.cdtcassistant.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdtc.student.cdtcassistant.R;


/**
 *
 * 首页
 *
 * Created by pcc on 2018/4/18.
 *
 * @author pcc
 */
public class IndexFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_index_fragment, container, false);

        initView(view);
        return view;
    }

    /**
     * 初始化View
     * @param view container
     */
    private void initView(View view) {

    }
}
