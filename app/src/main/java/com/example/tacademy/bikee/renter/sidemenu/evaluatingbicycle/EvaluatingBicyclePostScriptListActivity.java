package com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EvaluatingBicyclePostScriptListActivity extends AppCompatActivity {
    private ListView lv;
    private EvaluatingBicyclePostScriptAdapter adapter;

    private static final String TAG = "EVALUATING_B_P_S_L_ACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluating_bicycle_post_script_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_evaluating_bicycle_post_script_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.post_script_backable_tool_bar, null);
        cView.findViewById(R.id.post_script_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.post_script_backable_tool_bar_back_button_layout:
                        finish();
                        break;
                }
            }
        });
        getSupportActionBar().setCustomView(cView);

        lv = (ListView) findViewById(R.id.activity_evaluating_bicycle_post_script_list_view);
        adapter = new EvaluatingBicyclePostScriptAdapter();
        lv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        // 내평가보기
        NetworkManager.getInstance().selectUserComment(
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        Log.i("result", "onResponse Success");
                        List<Result> results = receiveObject.getResult();
                        for (Result result : results)
                            for (Comment comment : result.getComments()) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
                                Log.i("result", "onResponse Bike Image : " + result.getBike().getImage().getCdnUri() + "/mini_" + result.getBike().getImage().getFiles().get(0)
                                                + ", Bike Name : " + result.getBike().getTitle()
                                                + ", CreateAt : " + simpleDateFormat.format(comment.getCreatedAt())
                                                + ", Body : " + comment.getBody()
                                                + ", Point : " + comment.getPoint()
                                );
                                int point = (null != comment.getPoint()) ? comment.getPoint() : 0;
                                adapter.add(result.getBike().getImage().getCdnUri() + "/mini_" + result.getBike().getImage().getFiles().get(0),
                                        result.getBike().getTitle(),
                                        simpleDateFormat.format(comment.getCreatedAt()),
                                        comment.getBody(),
                                        point
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
