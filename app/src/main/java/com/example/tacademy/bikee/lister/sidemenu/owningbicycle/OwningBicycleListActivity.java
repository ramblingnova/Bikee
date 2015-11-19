package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

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
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.RegisterBicycleActivity;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OwningBicycleListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private Intent intent;
    private ListView lv;
    private OwningBicycleAdapter adapter;
    private Button btn;
    final private static int OWNING_BICYCLE_DETAIL_INFORMATION_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owning_bicycle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_owning_bicycle_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        lv = (ListView) findViewById(R.id.activity_owning_bicycle_list_list_view);
        adapter = new OwningBicycleAdapter();
        lv.setAdapter(adapter);
        initData();

        lv.setOnItemClickListener(OwningBicycleListActivity.this);

        btn = (Button) findViewById(R.id.activity_owning_bicycle_list_button);
        btn.setOnClickListener(OwningBicycleListActivity.this);
    }

    private void initData() {
        // 보유자전거조회
        NetworkManager.getInstance().selectBicycle(new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Code : " + receiveObject.getCode() + ", Success : " + receiveObject.isSuccess());
                List<Result> results = receiveObject.getResult();
                for (Result result : results) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
                    Log.i("result", "onResponse Id : " + result.get_id()
                                    + ", Title : " + result.getTitle()
                                    + ", CreateAt : " + simpleDateFormat.format(result.getCreatedAt())
                    );
                    adapter.add(result.get_id(),
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
        Toast.makeText(OwningBicycleListActivity.this, "position : " + position, Toast.LENGTH_SHORT).show();
        intent = new Intent(OwningBicycleListActivity.this, OwningBicycleDetailInformationActivity.class);
        intent.putExtra("id", item.getId());
        startActivityForResult(intent, OWNING_BICYCLE_DETAIL_INFORMATION_ACTIVITY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_owning_bicycle_list_button:
                Intent intent = new Intent(OwningBicycleListActivity.this, RegisterBicycleActivity.class);
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
}