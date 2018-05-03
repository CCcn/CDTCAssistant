package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.cdtc.student.cdtcassistant.network.bean.AddFindBean;
import com.cdtc.student.cdtcassistant.network.bean.ContactBean;
import com.cdtc.student.cdtcassistant.network.request.AddFindRequest;
import com.cdtc.student.cdtcassistant.network.response.AddFindResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddFindActivity extends BaseTopActivity {

    private EditText title;
    private EditText name;
    private EditText place;
    private EditText date;
    private EditText contactPerson;
    private EditText qq;
    private EditText phone;
    private EditText description;

    private Button submit;

    private Activity activity;

    private final String TAG = "AddFindActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_find);

        initVariable();

        initView();

    }

    private void initVariable() {
        activity = this;
    }

    private void initView() {
        initTopBar("添加");

        qq = getView(R.id.add_find_qq);
        name = getView(R.id.add_find_name);
        date = getView(R.id.add_find_date);
        title = getView(R.id.add_find_title);
        place = getView(R.id.add_find_place);
        phone = getView(R.id.add_find_phone);
        description = getView(R.id.add_find_description);
        contactPerson = getView(R.id.add_find_contact_person);

        submit = getView(R.id.add_find_submit);

        submit.setOnClickListener(v -> {
            submit.setEnabled(false);

            String inputQQ = qq.getText().toString();
            String inputName = name.getText().toString();
            String inputDate = date.getText().toString();
            String inputTitle = title.getText().toString();
            String inputPlace = place.getText().toString();
            String inputPhone = phone.getText().toString();
            String inputDescription = description.getText().toString();
            String inputContactPerson = contactPerson.getText().toString();

            if (TextUtils.isEmpty(inputName) || TextUtils.isEmpty(inputDate) || TextUtils.isEmpty(inputTitle) ||
                    TextUtils.isEmpty(inputPlace) || TextUtils.isEmpty(inputDescription) || TextUtils.isEmpty(inputContactPerson)) {
                T.showShort(activity, "请输入完整信息");
                submit.setEnabled(true);
                return;
            }

            if (TextUtils.isEmpty(inputQQ) && TextUtils.isEmpty(inputPhone)) {
                T.showShort(activity, "至少输入一个联系方式");
                submit.setEnabled(true);
                return;
            }

            AddFindBean addFindBean = new AddFindBean();

            Integer userId = Singleton.getInstance(activity).getUser().getId();

            addFindBean.setUserId(userId);
            addFindBean.setName(inputName);
            addFindBean.setDate(inputDate);
            addFindBean.setTitle(inputTitle);
            addFindBean.setPlace(inputPlace);
            addFindBean.setDescription(inputDescription);
            addFindBean.setContactPerson(inputContactPerson);

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

            AddFindRequest addFindRequest = new AddFindRequest();

            addFindRequest.setFind(addFindBean);
            addFindRequest.setContacts(contacts);


            OkHttpUtil.doJsonPost(Api.CREATE_FIND, new Gson().toJson(addFindRequest), new Callback() {
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
                        AddFindResponse addFindResponse = null;

                        try {
                            addFindResponse = new Gson().fromJson(responseString, AddFindResponse.class);
                            if (addFindResponse.code == HttpConstant.OK) {
                                T.showShort(activity, "提交成功");
                                Log.i(TAG, "onResponse: " + addFindResponse.message);
                                finish();
                                return;
                            }
                            //出错了
                            T.showShort(activity, addFindResponse.message);
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
        Intent intent = new Intent(context, AddFindActivity.class);
        context.startActivity(intent);
    }
}
