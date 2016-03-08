package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.RegisterBicycleActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OwningBicycleListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // TODO : need modify UI
    private Toolbar toolbar;
    private Intent intent;
    private ListView lv;
    private OwningBicycleAdapter adapter;
    final private static int REGISTER_BICYCLE_ACTIVITY = 1;
    final public static String ID_TAG = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owning_bicycle_list);
        toolbar = (Toolbar) findViewById(R.id.activity_owning_bicycle_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_backable_addable_tool_bar);

        lv = (ListView) findViewById(R.id.activity_owning_bicycle_list_list_view);
        adapter = new OwningBicycleAdapter();
        lv.setAdapter(adapter);
        initData();
        lv.setOnItemClickListener(OwningBicycleListActivity.this);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.lister_backable_addable_tool_bar_back_button_layout)
    void back() {
        super.onBackPressed();
    }

    @OnClick(R.id.lister_backable_addable_tool_bar_register_bicycle_button_layout)
    void register() {
        Intent intent = new Intent(OwningBicycleListActivity.this, RegisterBicycleActivity.class);
        startActivityForResult(intent, REGISTER_BICYCLE_ACTIVITY);
    }

    private void initData() {
        // 보유자전거조회
        NetworkManager.getInstance().selectBicycle(new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Code : " + receiveObject.getCode()
                                + ", Success : " + receiveObject.isSuccess()
                );
                List<Result> results = receiveObject.getResult();
                for (Result result : results) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
                    String imageURL;
                    if ((null == result.getImage().getCdnUri())
                            || (null == result.getImage().getCdnUri())
                            || (null == result.getImage().getFiles().get(0))) {
                        imageURL = "";
                    } else {
                        imageURL = result.getImage().getCdnUri() + "/detail_" + result.getImage().getFiles().get(0);
                    }
                    Log.i("result", "onResponse Id : " + result.get_id()
                                    + ", ImageURL : " + imageURL
                                    + ", Title : " + result.getTitle()
                                    + ", CreateAt : " + simpleDateFormat.format(result.getCreatedAt())
                    );
                    adapter.add(result.get_id(),
                            imageURL,
                            result.getTitle(),
                            simpleDateFormat.format(result.getCreatedAt())
                    );
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OwningBicycleItem item = (OwningBicycleItem) lv.getItemAtPosition(position);
        intent = new Intent(OwningBicycleListActivity.this, OwningBicycleDetailInformationActivity.class);
        intent.putExtra(ID_TAG, item.getId());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REGISTER_BICYCLE_ACTIVITY) {
            adapter.clear();
            initData();
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