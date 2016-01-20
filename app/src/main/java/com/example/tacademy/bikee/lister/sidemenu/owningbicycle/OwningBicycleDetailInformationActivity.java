package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OwningBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Intent intent;
    private Button btn;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owning_bicycle_detail_information);
        toolbar = (Toolbar)findViewById(R.id.activity_owning_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_backable_tool_bar);

        intent = getIntent();
        int i = intent.getIntExtra("STATE", -1);
        if(i == 0) {
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_deactivate_button);               btn.setVisibility(View.VISIBLE);
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_back_button);                     btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_approval_button);                 btn.setVisibility(View.GONE);
        } else if(i == 1) {
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_deactivate_button);               btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_back_button);                     btn.setVisibility(View.VISIBLE);
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_approval_button);                 btn.setVisibility(View.VISIBLE);
        }
        btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_small_map_button);
        btn.setOnClickListener(OwningBicycleDetailInformationActivity.this);

        id = intent.getStringExtra(OwningBicycleListActivity.ID_TAG);
        initData(id);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.lister_backable_tool_bar_back_button_layout)
    void back() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_owning_bicycle_detail_information_deactivate_button:
                // TODO
                finish();
                break;
            case R.id.activity_owning_bicycle_detail_information_back_button:
                finish();
                break;
            case R.id.activity_owning_bicycle_detail_information_approval_button:
//                ChoiceDialogFragment dialog = new ChoiceDialogFragment();
//                dialog.setMessage("예약을 정말 취소하시겠습니까?", 1);
//                dialog.show(getSupportFragmentManager(), "custom");
                finish();
                break;
            case R.id.activity_owning_bicycle_detail_information_small_map_button:
                Intent intent = new Intent(OwningBicycleDetailInformationActivity.this, SmallMapActivity.class);
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

    private void initData(String bike_id) {
        // 자전거상세조회
        NetworkManager.getInstance().selectBicycleDetail(bike_id, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Code : " + receiveObject.getCode()
                        + ", Success : " + receiveObject.isSuccess()
                        + ", Msg : " + receiveObject.getMsg()
                        + ", Error : "
                );
                List<Result> results = receiveObject.getResult();
                for (Result result : results) {
                    Log.i("result", "onResponse Title : " + result.getTitle()
                                    + ", Price : " + result.getPrice().getDay()
                                    + ", Type : " + result.getType()
                                    + ", Height : " + result.getHeight()
                                    + ", Description : " + result.getIntro()
                                    + ", Latitude : " + result.getLoc().getCoordinates().get(1)
                                    + ", Longitude : " + result.getLoc().getCoordinates().get(0)
                    );
                    // TODO 데이터를 뿌려주어야 한다.
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
