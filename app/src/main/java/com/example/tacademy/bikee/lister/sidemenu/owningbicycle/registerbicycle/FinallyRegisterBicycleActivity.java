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
import com.example.tacademy.bikee.etc.dao.Price;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class FinallyRegisterBicycleActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private Intent intent;
    private RegisterBicycleItem tempItem;
    public static final String ITEM_TAG = "ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_register_bicycle);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_finally_register_bicycle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_back_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_ok_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_small_map_button);
        btn.setOnClickListener(this);

        intent = getIntent();
        tempItem = (RegisterBicycleItem)intent.getSerializableExtra(RegisterBicycleActivity.ITEM_TAG);
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

                 Map<String,TypedFile> map = new HashMap<String,TypedFile>();
                 for(int i =0;i<4;i++){
                     map.put("image"+i,new TypedFile("image/png", tempItem.getFile1()));
                 }

                Bike bike = new Bike();
                bike.setType(tempItem.getType());
                bike.setHeight(tempItem.getHeight());
                bike.setTitle(tempItem.getName());
                Price price = new Price();
                price.setHour(tempItem.getHour());
                price.setDay(tempItem.getDay());
                price.setMonth(tempItem.getMonth());
                bike.setPrice(price);
                MultipartTypedOutput m = new MultipartTypedOutput();
                for(int i = 0 ; i < 5;i++){
                    m.addPart("image", new TypedFile("image/png", tempItem.getFile1()));
                }
                int size = 4;

                NetworkManager.getInstance().insertBicycle(map, bike, size, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Code : " + receiveObject.getCode() + ", Success : " + receiveObject.isSuccess() + ", Msg : " + receiveObject.getMsg() + ", Error : ");
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
