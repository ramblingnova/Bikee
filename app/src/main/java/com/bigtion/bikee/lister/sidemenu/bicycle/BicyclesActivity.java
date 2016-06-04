package com.bigtion.bikee.lister.sidemenu.bicycle;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigtion.bikee.common.content.ContentActivity;
import com.bigtion.bikee.etc.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.etc.manager.NetworkManager;
import com.bigtion.bikee.lister.sidemenu.bicycle.register.RegisterBicycleActivity;
import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.dao.ReceiveObject;
import com.bigtion.bikee.etc.dao.Result;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BicyclesActivity extends AppCompatActivity implements OnAdapterClickListener {
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_back_icon_image_view)
    ImageView toolbarLeftBackIconImageView;
    @Bind(R.id.toolbar_center_text_view)
    TextView toolbarCenterTextView;
    @Bind(R.id.toolbar_right_mode_icon_image_view)
    ImageView toolbarRightModeIconImageView;
    @Bind(R.id.activity_bicycles_recycler_view)
    RecyclerView recyclerView;

    private Intent intent;
    private LinearLayoutManager layoutManager;
    private BicycleAdapter adapter;

    public static final int from = 7;
    private static final int REGISTER_BICYCLE_ACTIVITY = 1;
    private static final String TAG = "BICYCLES_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owning_bicycle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_bicycles_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        ButterKnife.bind(this);

        /* 툴바 배경 */
        if (Build.VERSION.SDK_INT < 23)
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue));
        else
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue, getTheme()));

        /* 툴바 왼쪽 */
        toolbarLeftBackIconImageView.setVisibility(View.VISIBLE);

        /* 툴바 가운데 */
        toolbarCenterTextView.setText("내 자전거 보기");
        if (Build.VERSION.SDK_INT < 23)
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
        else
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
        toolbarCenterTextView.setVisibility(View.VISIBLE);

        /* 툴바 오른쪽 */
        toolbarRightModeIconImageView.setImageResource(R.drawable.add_icon);
        toolbarRightModeIconImageView.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        adapter = new BicycleAdapter();
        adapter.setOnAdapterClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new BicycleDecoration());

        init();
    }

    @OnClick({R.id.toolbar_left_layout,
            R.id.toolbar_right_mode_icon_image_view})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_layout:
                onBackPressed();
                break;
            case R.id.toolbar_right_mode_icon_image_view:
                Intent intent = new Intent(BicyclesActivity.this, RegisterBicycleActivity.class);
                startActivityForResult(intent, REGISTER_BICYCLE_ACTIVITY);
                break;
        }
    }

    @Override
    public void onAdapterClick(View view, Object item) {
        intent = new Intent(BicyclesActivity.this, ContentActivity.class);
        intent.putExtra("FROM", from);
        intent.putExtra("BICYCLE_ID", ((BicycleItem) item).getId());
        intent.putExtra("BICYCLE_LATITUDE", (double) 126.9203671);
        intent.putExtra("BICYCLE_LONGITUDE", (double) 37.4666033);

        startActivity(intent);
    }

    private void init() {
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