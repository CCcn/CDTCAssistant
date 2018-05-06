package com.cdtc.student.cdtcassistant.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cdtc.student.cdtcassistant.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by pcc on 2018/5/5.
 *
 * @author pcc
 */
public class LoadDialogUtils {
    private static Dialog mLoadingDialog;

    /**
     * 显示加载动画
     *
     * @param context 上下文
     */
    public static void showDialogForLoading(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null);
        AVLoadingIndicatorView avLoadingIndicatorView = view.findViewById(R.id.loading_indicator);
        mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        avLoadingIndicatorView.smoothToShow();
        mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mLoadingDialog.dismiss();
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 显示加载动画
     *
     * @param context 上下文
     */
    public static void showDialogForLogin(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_login_dialog, null);
        AVLoadingIndicatorView avLoadingIndicatorView = view.findViewById(R.id.loading_indicator);
        mLoadingDialog = new Dialog(context, R.style.login_dialog_style);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        avLoadingIndicatorView.smoothToShow();
        mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mLoadingDialog.dismiss();
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 关闭加载动画
     *
     * @param context
     */
    public static void hide(Context context) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
