package com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EvaluatedBicyclePostScriptListActivity extends AppCompatActivity {
    private ListView lv;
    private EvaluatedBicyclePostScriptAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluated_bicycle_post_script_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_evaluated_bicycle_post_script_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        lv = (ListView) findViewById(R.id.activity_evaluated_bicycle_post_script_list_list_view);
        adapter = new EvaluatedBicyclePostScriptAdapter();
        lv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        NetworkManager.getInstance().selectMyBicycleComment(new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Success");
                List<Result> results = receiveObject.getResult();
                for (Result result : results) {
                    for (Comment comment : result.getComments()) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
                        String imageURL;
                        if ((null == comment.getWriter().getImage())
                                || (null == comment.getWriter().getImage().getCdnUri())
                                || (null == comment.getWriter().getImage().getFiles())
                                || (null == comment.getWriter().getImage().getFiles().get(0))) {
                            imageURL = "";
                        } else {
                            imageURL = comment.getWriter().getImage().getCdnUri() + "/detail_" + comment.getWriter().getImage().getFiles().get(0);
                        }
                        Log.i("result", "onResponse Writer Image : " + imageURL
                                        + ", Writer Name : " + comment.getWriter().getName()
                                        + ", Bicycle Name : " + result.getBike().getTitle()
                                        + ", CreateAt : " + simpleDateFormat.format(comment.getCreatedAt())
                                        + ", Body : " + comment.getBody()
                                        + ", Point : " + comment.getPoint()
                        );
                        adapter.add(imageURL,
                                comment.getWriter().getName(),
                                result.getBike().getTitle(),
                                simpleDateFormat.format(comment.getCreatedAt()),
                                comment.getBody(),
                                comment.getPoint()
                        );
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
