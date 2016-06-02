package com.bikee.www.lister.sidemenu.bicycle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bikee.www.common.content.ContentActivity;
import com.bikee.www.common.interfaces.OnAdapterClickListener;
import com.bikee.www.etc.manager.NetworkManager;
import com.bikee.www.lister.sidemenu.bicycle.register.RegisterBicycleActivity;
import com.bikee.www.BuildConfig;
import com.bikee.www.R;
import com.bikee.www.etc.dao.ReceiveObject;
import com.bikee.www.etc.dao.Result;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BicyclesActivity extends AppCompatActivity implements OnAdapterClickListener {
    private Intent intent;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BicycleAdapter adapter;

    public static final int from = 7;
    private static final int REGISTER_BICYCLE_ACTIVITY = 1;
    private static final String TAG = "BICYCLES_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owning_bicycle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_owning_bicycle_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_backable_addable_toolbar);

        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_owning_bicycle_list_list_view);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new BicycleAdapter();
        adapter.setOnAdapterClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new BicycleDecoration());

        init();
    }

    @OnClick(R.id.lister_backable_addable_toolbar_back_button_layout)
    void back() {
        super.onBackPressed();
    }

    @OnClick(R.id.lister_backable_addable_toolbar_register_bicycle_button_layout)
    void register() {
        Intent intent = new Intent(BicyclesActivity.this, RegisterBicycleActivity.class);
        startActivityForResult(intent, REGISTER_BICYCLE_ACTIVITY);
    }

    @Override
    public void onAdapterClick(View view, Object item) {
        intent = new Intent(BicyclesActivity.this, ContentActivity.class);
        intent.putExtra("FROM", from);
        intent.putExtra("BICYCLE_ID", ((BicycleItem) item).getId());
        intent.putExtra("BICYCLE_LATITUDE", (double)126.9203671);
        intent.putExtra("BICYCLE_LONGITUDE", (double)37.4666033);

        startActivity(intent);
    }

    private void init() {
        // 보유자전거조회
        NetworkManager.getInstance().selectBicycle(
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
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
                                imageURL = result.getImage().getCdnUri() + result.getImage().getFiles().get(0);
                            }
                            Log.i("result", "onResponse Id : " + result.get_id()
                                            + ", ImageURL : " + imageURL
                                            + ", Title : " + result.getTitle()
                                            + ", CreateAt : " + simpleDateFormat.format(result.getCreatedAt())
                            );
                            adapter.add(
                                    new BicycleItem(
                                            result.get_id(),
                                            imageURL,
                                            result.getTitle(),
                                            result.getCreatedAt()
                                    )
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REGISTER_BICYCLE_ACTIVITY) {
            adapter.clear();
            init();
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