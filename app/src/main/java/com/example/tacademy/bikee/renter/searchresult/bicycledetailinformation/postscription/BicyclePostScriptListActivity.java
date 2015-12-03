package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptListActivity extends AppCompatActivity {
    private Intent intent;
    @Bind(R.id.activity_bicycle_post_script_list_list_view) ListView lv;
    private BicyclePostScriptAdapter adapter;
    private String bicycleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicycle_post_script_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_bicycle_post_script_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        ButterKnife.bind(this);
        
        adapter = new BicyclePostScriptAdapter();
        lv.setAdapter(adapter);

        intent = getIntent();
        bicycleId = intent.getStringExtra("ID");

        initData();
    }

    private void initData() {
        NetworkManager.getInstance().selectBicycleComment(bicycleId, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
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
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
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
