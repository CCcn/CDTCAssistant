package com.cdtc.student.cdtcassistant.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.activity.FeedbackActivity;
import com.cdtc.student.cdtcassistant.util.T;


/**
 *
 * 首页
 *
 * Created by pcc on 2018/4/19.
 *
 * @author pcc
 */
public class MineFragment extends Fragment implements View.OnClickListener{

    /**
     * 六个模块
     *  跳蚤
     *  个人信息
     *  失物招领
     *  表白墙
     *  消息
     *  反馈
     */
    private LinearLayout buy;
    private LinearLayout info;
    private LinearLayout find;
    private LinearLayout love;
    private LinearLayout message;
    private LinearLayout feedback;

    private Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_mine_fragment, container, false);

        initVariable();
        initView(view);

        return view;
    }

    private void initVariable() {
        activity = getActivity();
    }

    /**
     * 初始化View
     * @param view Container
     */
    private void initView(View view) {

        buy = view.findViewById(R.id.mine_layout_buy);
        info = view.findViewById(R.id.mine_layout_info);
        find = view.findViewById(R.id.mine_layout_find);
        love = view.findViewById(R.id.mine_layout_love);
        message = view.findViewById(R.id.mine_layout_message);
        feedback = view.findViewById(R.id.mine_layout_feedback);

        buy.setOnClickListener(this);
        info.setOnClickListener(this);
        find.setOnClickListener(this);
        love.setOnClickListener(this);
        message.setOnClickListener(this);
        feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_layout_feedback:
                FeedbackActivity.startAction(activity);
                break;
            default:
                T.showShort(activity,"暂未开通");
                break;
        }
    }
}
