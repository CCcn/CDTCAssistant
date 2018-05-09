package com.cdtc.student.cdtcassistant.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.AddFindBean;
import com.cdtc.student.cdtcassistant.network.bean.ContactBean;
import com.cdtc.student.cdtcassistant.network.request.AddFindRequest;
import com.cdtc.student.cdtcassistant.network.response.AddFindResponse;
import com.cdtc.student.cdtcassistant.network.response.FileUploadResponse;
import com.cdtc.student.cdtcassistant.util.BitMapUtils;
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

    private ImageView addPicture;

    private Button submit;

    private Activity activity;

    private final String TAG = "AddFindActivity";



    private static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_CODE = 0;

    private final Integer HAS_IMAGE = 1;

    /**
     * 存上传的图片
     */
    private List<String> imgs = new ArrayList<>();

    /**
     * 压缩后的图片路径
     */
    private List<String> paths = new ArrayList<>();


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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

        addPicture = getView(R.id.find_add_img);

        submit = getView(R.id.add_find_submit);


        /**
         * 获取相册图片
         */
        addPicture.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
            startActivityForResult(intent, IMAGE_CODE);
        });

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

            AddFindRequest addFindRequest = new AddFindRequest();
            AddFindBean addFindBean = new AddFindBean();

            Integer userId = Singleton.getInstance(activity).getUser().getId();

            addFindBean.setUserId(userId);
            addFindBean.setName(inputName);
            addFindBean.setDate(inputDate);
            addFindBean.setTitle(inputTitle);
            addFindBean.setPlace(inputPlace);
            addFindBean.setDescription(inputDescription);
            addFindBean.setContactPerson(inputContactPerson);

            if (!imgs.isEmpty()) {
                addFindBean.setHasImg(HAS_IMAGE);
                addFindBean.setImg(imgs.get(0));
                addFindRequest.setImgs(imgs);
            }

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

            addFindRequest.setFind(addFindBean);
            addFindRequest.setContacts(contacts);

            //未选择图片
            if (!paths.isEmpty()) {
                T.showShort(activity,"正在上传图片，请耐心等待！");
                submit.setEnabled(true);
               return;
            }

            submitData(addFindRequest);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bm = null;

        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();

        if (requestCode == IMAGE_CODE) {

            try {
                // 获得图片的uri
                Uri originalUri = data.getData();

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                //使用系统的一个工具类，参数列表为 Bitmap Width,Height  这里使用压缩后显示，否则在华为手机上ImageView 没有显示
                // imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 100, 100));
                // 显得到bitmap图片
                // imageView.setImageBitmap(bm);
                String[] proj = { MediaStore.Images.Media.DATA };
                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
                Log.i(TAG, "onActivityResult: path" + path);
                String compressImage = BitMapUtils.compressImage(path, activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath(), 50);
                int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            activity,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                }
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    paths.add(compressImage);
                    uploadPicture(compressImage);
                }else {
                    T.showShort(activity,"请开启文件读写权限");
                }

            } catch (IOException e) {
                Log.e("TAG-->Error", e.toString());

            }
            finally {
                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 提交数据
     * @param addFindRequest
     */
    private void submitData(AddFindRequest addFindRequest) {
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
    }

    /**
     * 上传图片
     * @param compressImage 压缩后的图片路径
     */
    private void uploadPicture(String compressImage) {
        OkHttpUtil.upLoadFile(Api.FILE_UPLOAD, compressImage, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    T.showShort(activity,e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                runOnUiThread(() -> {
                    try {
                        FileUploadResponse response1 = new Gson().fromJson(responseString, FileUploadResponse.class);
                        if (response1.code == HttpConstant.OK) {
                            imgs.addAll(response1.getData().getUrls());
                            paths.remove(compressImage);
                            Log.i(TAG, "onResponse: 图片上传成功" + response1.getData().getUrls());
                        }
                    } catch (Exception e) {
                        T.showShort(activity,"图片上传失败" + e.getMessage());
                    }
                });

            }
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AddFindActivity.class);
        context.startActivity(intent);
    }
}
