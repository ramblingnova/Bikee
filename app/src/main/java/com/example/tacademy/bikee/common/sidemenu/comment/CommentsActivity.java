package com.example.tacademy.bikee.common.sidemenu.comment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.dao.UserCommentReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.lister.ListerMainActivity;
import com.example.tacademy.bikee.renter.RenterMainActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {
    private Intent intent;
    private RecyclerView recycler;
    private LinearLayoutManager layoutManager;
    private CommentAdapter adapter;
    private String from;

    private static final String TAG = "COMMENTS_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_evaluating_bicycle_post_script_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.post_script_backable_tool_bar);
        ButterKnife.bind(this);

        intent = getIntent();
        from = intent.getStringExtra("FROM");

        recycler = (RecyclerView) findViewById(R.id.activity_comments_recycler_view);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recycler.setLayoutManager(layoutManager);

        adapter = new CommentAdapter();

        recycler.setAdapter(adapter);

        init();
    }

    @OnClick(R.id.post_script_backable_tool_bar_back_button_layout)
    void back(View view) {
        super.onBackPressed();
    }

    private void init() {
        if (from.equals(RenterMainActivity.from)) {
            NetworkManager.getInstance().selectUserComment(
                    null,
                    new Callback<UserCommentReceiveObject>() {
                        @Override
                        public void onResponse(Call<UserCommentReceiveObject> call, Response<UserCommentReceiveObject> response) {
                            UserCommentReceiveObject receiveObject = response.body();
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectUserComment onResponse");
                            List<UserCommentReceiveObject.Result> results = receiveObject.getResult();
                            for (UserCommentReceiveObject.Result result : results)
                                for (UserCommentReceiveObject.Result.Comment comment : result.getComments()) {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm", java.util.Locale.getDefault());
                                    if (BuildConfig.DEBUG)
                                        Log.d(TAG, "Bike Image : " + result.getBike().getImage().getCdnUri() + result.getBike().getImage().getFiles().get(0)
                                                        + "\nBike Name : " + result.getBike().getTitle()
                                                        + "\nCreateAt : " + simpleDateFormat.format(comment.getCreatedAt())
                                                        + "\nBody : " + comment.getBody()
                                                        + "\nPoint : " + comment.getPoint()
                                        );
                                    int point = comment.getPoint();
                                    adapter.add(
                                            new CommentItem(
                                                    result.getBike().getImage().getCdnUri() + result.getBike().getImage().getFiles().get(0),
                                                    result.getBike().getTitle(),
                                                    comment.getCreatedAt(),
                                                    comment.getBody(),
                                                    point
                                            )
                                    );
                                }
                        }

                        @Override
                        public void onFailure(Call<UserCommentReceiveObject> call, Throwable t) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectUserComment onFailure", t);
                        }
                    });
        } else if (from.equals(ListerMainActivity.from)) {
            NetworkManager.getInstance().selectMyBicycleComment(
                    null,
                    new Callback<ReceiveObject>() {
                        @Override
                        public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                            ReceiveObject receiveObject = response.body();
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectMyBicycleComment onResponse");
                            List<Result> results = receiveObject.getResult();
                            for (Result result : results) {
                                for (Comment comment : result.getComments()) {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm", java.util.Locale.getDefault());
                                    String imageURL;
                                    if ((null == comment.getWriter().getImage())
                                            || (null == comment.getWriter().getImage().getCdnUri())
                                            || (null == comment.getWriter().getImage().getFiles())
                                            || (null == comment.getWriter().getImage().getFiles().get(0))) {
                                        imageURL = "";
                                    } else {
                                        imageURL = comment.getWriter().getImage().getCdnUri() + comment.getWriter().getImage().getFiles().get(0);
                                    }
                                    if (BuildConfig.DEBUG)
                                        Log.d(TAG, "Writer Image : " + imageURL
                                                        + "\nWriter Name : " + comment.getWriter().getName()
                                                        + "\nBicycle Name : " + result.getBike().getTitle()
                                                        + "\nCreateAt : " + simpleDateFormat.format(comment.getCreatedAt())
                                                        + "\nBody : " + comment.getBody()
                                                        + "\nPoint : " + comment.getPoint()
                                        );
                                    adapter.add(
                                            new CommentItem(
                                                    imageURL,
                                                    comment.getWriter().getName(),
                                                    result.getBike().getTitle(),
                                                    comment.getCreatedAt(),
                                                    comment.getBody(),
                                                    comment.getPoint()
                                            )
                                    );
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ReceiveObject> call, Throwable t) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectMyBicycleComment onFailure", t);
                        }
                    });
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
