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

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.dao.Bike;
import com.example.tacademy.bikee.etc.dao.Loc;
import com.example.tacademy.bikee.etc.dao.Price;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.util.ArrayList;
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
import retrofit.mime.TypedString;

public class FinallyRegisterBicycleActivity extends AppCompatActivity implements View.OnClickListener {
    // TODO : need new post url
    private Toolbar toolbar;
    private Button btn;
    private Intent intent;
    private RegisterBicycleItem tempItem;
    public static final String ITEM_TAG = "ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_register_bicycle);
        toolbar = (Toolbar) findViewById(R.id.activity_finally_register_bicycle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_backable_page_movable_tool_bar);

        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_back_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_ok_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_small_map_button);
        btn.setOnClickListener(this);

        intent = getIntent();
        tempItem = (RegisterBicycleItem) intent.getSerializableExtra(RegisterBicycleActivity.ITEM_TAG);

        ButterKnife.bind(this);
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
//                List<TypedFile> typedFile1 = new ArrayList<>();
//                typedFile1.add(new TypedFile("image/png", tempItem.getFile1()));
//                TypedFile typedFile2 = new TypedFile("image/png", tempItem.getFile2());
//                typedFile1.add(typedFile2);
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

                Map<String, TypedFile> map = new HashMap<String, TypedFile>();
                int i = 0;
                for (File file : tempItem.getFiles()) {
                    // temporary sleep, i have to wake this up
                    map.put("image" + (i++), new TypedFile("image/png", file));
                }
                int size = i;

                Price price = new Price();
                price.setHour(tempItem.getHour());
                price.setDay(tempItem.getDay());
                price.setMonth(tempItem.getMonth());
                bike.setPrice(price);

                NetworkManager.getInstance().insertBicycle(map, bike, size, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Code : " + receiveObject.getCode()
                                + ", Success : " + receiveObject.isSuccess()
                                + ", Msg : " + receiveObject.getMsg()
                                + ", response.getStatus() : " + response.getStatus()
                        );
                        intent = getIntent();
                        intent.putExtra(ITEM_TAG, tempItem);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
