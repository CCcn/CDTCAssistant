package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.AddBuyBean;
import com.cdtc.student.cdtcassistant.network.bean.ContactBean;
import com.cdtc.student.cdtcassistant.network.request.AddBuyRequest;
import com.cdtc.student.cdtcassistant.network.response.AddBuyResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddBuyActivity extends BaseTopActivity {

    private EditText title;
    private EditText name;
    private EditText contactPerson;
    private EditText price;
    private EditText qq;
    private EditText phone;
    private EditText description;
    private Button submit;


    private Activity activity;

    private Integer userId;

    private final String TAG = "AddBuyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buy);

        initVariable();

        initView();
    }


    private void initVariable() {
        activity = this;
        userId = Singleton.getInstance(activity).getUser().getId();

    }

    private void initView() {
        initTopBar("添加");

        qq = getView(R.id.add_buy_qq);
        name = getView(R.id.add_buy_name);
        title = getView(R.id.add_buy_title);
        price = getView(R.id.add_buy_price);
        phone = getView(R.id.add_buy_phone);
        description = getView(R.id.add_buy_description);
        contactPerson = getView(R.id.add_buy_contact_person);

        submit = getView(R.id.add_buy_submit);

        submit.setOnClickListener( view -> {

            submit.setEnabled(false);

            String inputQQ = qq.getText().toString();
            String inputName = name.getText().toString();
            String inputTitle = title.getText().toString();
            String inputPhone = phone.getText().toString();
            String inputPrice = price.getText().toString();
            String inputDescription = description.getText().toString();
            String inputContactPerson = contactPerson.getText().toString();

            if (TextUtils.isEmpty(inputName) || TextUtils.isEmpty(inputTitle) ||
                    TextUtils.isEmpty(inputPrice) || TextUtils.isEmpty(inputDescription) || TextUtils.isEmpty(inputContactPerson)) {
                T.showShort(activity, "请输入完整信息");
                submit.setEnabled(true);
                return;
            }

            if (TextUtils.isEmpty(inputQQ) && TextUtils.isEmpty(inputPhone)) {
                T.showShort(activity, "至少输入一个联系方式");
                submit.setEnabled(true);
                return;
            }


            AddBuyBean addBuyBean = new AddBuyBean();
            addBuyBean.setUserId(userId);
            addBuyBean.setTitle(inputTitle);
            addBuyBean.setName(inputName);
            addBuyBean.setPrice(inputPrice);
            addBuyBean.setDescription(inputDescription);
            addBuyBean.setOwner(inputContactPerson);

            List<ContactBean> contacts = new ArrayList<>();

            if (!TextUtils.isEmpty(inputQQ)) {
                ContactBean contactBean = new ContactBean();
                contactBean.setNumber(inputQQ);
                contactBean.setContactType("qq");
                contacts.add(contactBean);
            }

            if (!TextUtils.isEmpty(inputPhone)) {
                ContactBean contactBean = new ContactBean();
                contactBean.setNumber(inputPhone);
                contactBean.setContactType("phone");
                contacts.add(contactBean);
            }

            AddBuyRequest request = new AddBuyRequest();
            request.setBuy(addBuyBean);
            request.setContacts(contacts);

            OkHttpUtil.doJsonPost(Api.CREATE_BUY, new Gson().toJson(request), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: 请求失败" + e.getMessage() );

                    runOnUiThread(() -> {
                        T.showError(activity);
                        submit.setEnabled(true);
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Log.i(TAG, "onResponse: 响应成功 " + response.code());
                    String responseString = response.body().string();

                    runOnUiThread(() -> {
                        AddBuyResponse addBuyResponse = null;
                        try {

                            addBuyResponse = new Gson().fromJson(responseString, AddBuyResponse.class);
                            if (addBuyResponse.code == HttpConstant.OK) {
                                T.showShort(activity, "提交成功");
                                Log.i(TAG, "onResponse: " + addBuyResponse.message);
                                finish();
                                return;
                            }
                            //出错了
                            T.showShort(activity, addBuyResponse.message);
                            submit.setEnabled(true);
                        } catch (Exception e) {
                            T.showDataError(activity);
                            submit.setEnabled(true);
                        }
                    });

                }
            });

        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AddBuyActivity.class);
        context.startActivity(intent);
    }
}
