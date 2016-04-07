package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.dao.Bike;
import com.example.tacademy.bikee.etc.dao.Loc;
import com.example.tacademy.bikee.etc.dao.Price;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.ncloud.filestorage.FSRestClient;
import com.ncloud.filestorage.model.FSClientException;
import com.ncloud.filestorage.model.FSResourceID;
import com.ncloud.filestorage.model.FSServiceException;
import com.ncloud.filestorage.model.FSUploadFileResult;
import com.ncloud.filestorage.model.FSUploadSourceInfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

public class FinallyRegisterBicycleActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private Intent intent;
    private RegisterBicycleItem tempItem;
    public static final String ITEM_TAG = "ITEM";
    private final int MAX_BUF_BYTE = 1024000;
    private static final String TAG = "FINALLY_R_B_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_register_bicycle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_finally_register_bicycle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_backable_page_movable_tool_bar);

        ButterKnife.bind(this);

        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_back_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_ok_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_small_map_button);
        btn.setOnClickListener(this);

        intent = getIntent();
        tempItem = (RegisterBicycleItem) intent.getSerializableExtra(RegisterBicycleActivity.ITEM_TAG);

    }

    @OnClick(R.id.lister_backable_page_movable_tool_bar_back_button_layout)
    void back() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_finally_register_bicycle_back_button:
                intent = new Intent();
                intent.putExtra("close", false);
                finish();
                break;
            case R.id.activity_finally_register_bicycle_ok_button:
                Bike bike = new Bike();

                bike.setType(tempItem.getType());
                bike.setComponents(tempItem.getComponents());
                bike.setHeight(tempItem.getHeight());

                List<Double> coordinates = new ArrayList<>();
                coordinates.add(tempItem.getLongitude());
                coordinates.add(tempItem.getLatitude());
                Loc loc = new Loc();
                loc.setCoordinates(coordinates);
                bike.setLoc(loc);

                bike.setTitle(tempItem.getName());
                bike.setIntro(tempItem.getIntroduction());

//                Map<String, TypedFile> map = new HashMap<String, TypedFile>();
//                int i = 0;
//                for (File file : tempItem.getFiles()) {
//                    // temporary sleep, i have to wake this up
//                    map.put("image" + (i++), new TypedFile("image/png", file));
//                }
//                int size = i;
                List<TypedFile> list = new ArrayList<>();
                for (File file : tempItem.getFiles())
                    list.add(new TypedFile("image/png", file));

                Price price = new Price();
                price.setHour(tempItem.getHour());
                price.setDay(tempItem.getDay());
                price.setMonth(tempItem.getMonth());
                bike.setPrice(price);

//                NetworkManager.getInstance().insertBicycle(list, bike, new Callback<ReceiveObject>() {
//                    @Override
//                    public void success(ReceiveObject receiveObject, Response response) {
//                        Log.i("result", "onResponse Code : " + receiveObject.getCode()
//                                + ", Success : " + receiveObject.isSuccess()
//                                + ", Msg : " + receiveObject.getMsg()
//                                + ", response.getStatus() : " + response.getStatus()
//                        );
//                        intent = getIntent();
//                        intent.putExtra(ITEM_TAG, tempItem);
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.e("error", "onFailure Error : " + error.toString());
//                    }
//                });

                // TODO : send images to ncloud
//                FSRestClient.initialize();
//                FSRestClient client = new FSRestClient(
//                        "restapi.fs.ncloud.com",
//                        80,
//                        "EQGtmtLqgwPNONDwuODO",
//                        "YbJwoJw9q5fO7qj4EQMzT4LpCl97CQ4Bmp3vwWqA"
//                );
//                InputStream ins = null;
//                try {
//                    for (File file : tempItem.getFiles()) {
//                        String fileName = Long.toString(new Date().getTime())
//                                + "_" + file.getName();
//                        byte fileArray[] = fileWrite(file);
//                        String mimeType = "image/png";
//
//                        FSResourceID rid = new FSResourceID("bikee-image/" + fileName);
//                        ins = new ByteArrayInputStream(fileArray);
//                        FSUploadSourceInfo info = new FSUploadSourceInfo(
//                                ins,
//                                mimeType,
//                                fileArray.length,
//                                null
//                        );
//
//                        FSUploadFileResult result = client.uploadFile(rid, info);
//                        if (BuildConfig.DEBUG)
//                            Log.d(TAG, "fileUrl : " + "http://restapi.fs.ncloud.com/bikee-image/" + fileName
//                                            + "\nresult.getETag() : " + result.getETag()
//                                            + "\nresult.toString() : " + result.toString()
//                            );
//
//                        ins.close();
//                    }
//                } catch (FSClientException e) {
//                    if (BuildConfig.DEBUG)
//                        Log.d(TAG, "FSClientException...", e);
//                } catch (FSServiceException e) {
//                    if (BuildConfig.DEBUG)
//                        Log.d(TAG, "FSServiceException...", e);
//                } catch (Exception e) {
//                    if (BuildConfig.DEBUG)
//                        Log.d(TAG, "Exception...", e);
//                } finally {
//                    try {
//                        if (ins != null)
//                            ins.close();
//                    } catch (IOException e) {
//                        if (BuildConfig.DEBUG)
//                            Log.d(TAG, "IOException...", e);
//                    } finally {
//                        FSRestClient.destroy();
//                    }
//                }
                break;
            case R.id.activity_finally_register_bicycle_small_map_button:
                intent = new Intent(FinallyRegisterBicycleActivity.this, SmallMapActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public byte[] fileWrite(File file) {
        InputStream in = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream arrayBuff = new ByteArrayOutputStream();
        try {

            byte[] buffer = new byte[MAX_BUF_BYTE];

            in = new FileInputStream(file);
            bis = new BufferedInputStream(in);
            int len = 0;
            while ((len = bis.read(buffer)) >= 0) {
                arrayBuff.write(buffer, 0, len);
            }

        } catch (Exception e) {

        } finally {

            try {

                in.close();
                bis.close();

            } catch (Exception e) {
            }
        }
        return arrayBuff.toByteArray();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
