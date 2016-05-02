package com.example.tacademy.bikee.lister.sidemenu.bicycle.register;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.dao.Bike;
import com.example.tacademy.bikee.etc.dao.Loc;
import com.example.tacademy.bikee.etc.dao.Price;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                List<MultipartBody.Part> multi = new ArrayList<>();
                for (File file : tempItem.getFiles()) {
                    RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                    multi.add(MultipartBody.Part.createFormData("files", file.getName(), fileReqBody));
                }

                Price price = new Price();
                price.setHour(tempItem.getHour());
                price.setDay(tempItem.getDay());
                price.setMonth(tempItem.getMonth());
                bike.setPrice(price);

                NetworkManager.getInstance().insertBicycle(
                        multi,
                        bike,
                        null,
                        new Callback<ReceiveObject>() {
                            @Override
                            public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                Log.d(TAG, "insertBicycle onResponse");
                            }

                            @Override
                            public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                Log.d(TAG, "insertBicycle onFailure", t);
                            }
                        });
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
