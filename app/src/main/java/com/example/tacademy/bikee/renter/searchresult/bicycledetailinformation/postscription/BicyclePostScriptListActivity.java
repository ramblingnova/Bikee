package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptListActivity extends AppCompatActivity {
    private Intent intent;
    @Bind(R.id.activity_bicycle_post_script_list_list_view)
    ListView lv;
    private BicyclePostScriptAdapter adapter;
    private String bicycleId;

    private static final String TAG = "BICYCLE_P_S_L_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicycle_post_script_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_bicycle_post_script_list_toolbar);
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

        ButterKnife.bind(this);

        adapter = new BicyclePostScriptAdapter();
        lv.setAdapter(adapter);

        intent = getIntent();
        bicycleId = intent.getStringExtra("ID");

        initData();
    }

    private void initData() {
        NetworkManager.getInstance().selectBicycleComment(
                bicycleId,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        Log.i("result", "onResponse Success");
                        List<Result> results = receiveObject.getResult();
                        for (Result result : results) {
                            if (null != result) {
                                for (Comment comment : result.getComments()) {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
                                    String imageURL;
                                    if ((null == comment.getWriter().getImage())
                                            || (null == comment.getWriter().getImage().getCdnUri())
                                            || (null == comment.getWriter().getImage().getFiles())
                                            || (null == comment.getWriter().getImage().getFiles().get(0))) {
                                        imageURL = "";
                                    } else {
                                        imageURL = comment.getWriter().getImage().getCdnUri() + "/mini_" + comment.getWriter().getImage().getFiles().get(0);
                                    }
                                    Log.i("result", "onResponse ImageURL : " + imageURL
                                                    + ", WriterName : " + comment.getWriter().getName()
                                                    + ", Point : " + comment.getPoint()
                                                    + ", PostScript : " + comment.getBody()
                                                    + ", CreateAt : " + simpleDateFormat.format(comment.getCreatedAt())
                                    );
                                    adapter.add(imageURL, comment.getWriter().getName(), comment.getPoint(), comment.getBody(), simpleDateFormat.format(comment.getCreatedAt()));
                                }
                            }
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
