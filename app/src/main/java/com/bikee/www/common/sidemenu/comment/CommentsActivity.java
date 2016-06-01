package com.bikee.www.common.sidemenu.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bikee.www.common.content.ContentActivity;
import com.bikee.www.renter.RenterMainActivity;
import com.bikee.www.BuildConfig;
import com.bikee.www.R;
import com.bikee.www.etc.dao.Comment;
import com.bikee.www.etc.dao.ReceiveObject;
import com.bikee.www.etc.dao.Result;
import com.bikee.www.etc.dao.UserCommentReceiveObject;
import com.bikee.www.etc.manager.NetworkManager;
import com.bikee.www.lister.ListerMainActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {
    // TODO : android api 버전 23이상은 필요한 권한을 체크해야 함
    // INTERNET : Network통신을 하기 위함, Glide를 통해 이미지를 받기 위함
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_icon_back_image_view)
    ImageView toolbarLeftIconBackImageView;
    @Bind(R.id.toolbar_center_text_view)
    TextView toolbarCenterTextView;
    @Bind(R.id.toolbar_right_icon_image_view)
    ImageView toolbarRightIconImageView;
    @Bind(R.id.activity_comments_recycler_view)
    RecyclerView recyclerView;

    private Intent intent;
    private LinearLayoutManager layoutManager;
    private CommentAdapter adapter;
    private String from;
    private String bicycleId;

    public static final int RENTER_COMMENT = 1;
    public static final int BICYCLE_COMMENT = 2;
    public static final int LISTER_COMMENT = 3;
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
        getSupportActionBar().setCustomView(R.layout.toolbar);

        ButterKnife.bind(this);

        intent = getIntent();
        from = intent.getStringExtra("FROM");
        if (from.equals(ContentActivity.TAG))
            bicycleId = intent.getStringExtra("BICYCLE_ID");

        if (from.equals(RenterMainActivity.TAG)
                || from.equals(ListerMainActivity.TAG)) {
            /* 툴바 배경색 */
            if (Build.VERSION.SDK_INT < 23)
                toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue));
            else
                toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue, getTheme()));

            /* 툴바 왼쪽 */
            toolbarLeftIconBackImageView.setVisibility(View.VISIBLE);
            toolbarLeftIconBackImageView.setImageResource(R.drawable.icon_before_w);

            /* 툴바 가운데 */
            toolbarCenterTextView.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT < 23)
                toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
            else
                toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
            if (from.equals(RenterMainActivity.TAG)) {
                toolbarCenterTextView.setText("내가 쓴 후기");

                /* 툴바 오른쪽 */
                toolbarRightIconImageView.setImageResource(R.drawable.rider_main_icon);
            } else if (from.equals(ListerMainActivity.TAG)) {
                toolbarCenterTextView.setText("내 자전거 후기");

                /* 툴바 오른쪽 */
                toolbarRightIconImageView.setImageResource(R.drawable.lister_main_icon);
            }
        } else if (from.equals(ContentActivity.TAG)) {
            /* 툴바 배경색 */
            if (Build.VERSION.SDK_INT < 23)
                toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeWhite));
            else
                toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeWhite, getTheme()));

            /* 툴바 왼쪽 */
            toolbarLeftIconBackImageView.setVisibility(View.VISIBLE);
            toolbarLeftIconBackImageView.setImageResource(R.drawable.icon_before);

            /* 툴바 가운데 */
            toolbarCenterTextView.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT < 23)
                toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeBlack));
            else
                toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeBlack, getTheme()));
            toolbarCenterTextView.setText("후기");
        }

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommentAdapter();

        recyclerView.setAdapter(adapter);

        init();
    }

    @OnClick(R.id.toolbar_left_icon_layout)
    void back(View view) {
        super.onBackPressed();
    }

    private void init() {
        if (from.equals(RenterMainActivity.TAG)) {
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
                                                    point,
                                                    RENTER_COMMENT
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
        } else if (from.equals(ContentActivity.TAG)){
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
                                            imageURL = comment.getWriter().getImage().getCdnUri() + comment.getWriter().getImage().getFiles().get(0);
                                        }
                                        Log.d(TAG, "onResponse ImageURL : " + imageURL
                                                        + ", WriterName : " + comment.getWriter().getName()
                                                        + ", Point : " + comment.getPoint()
                                                        + ", PostScript : " + comment.getBody()
                                                        + ", CreateAt : " + simpleDateFormat.format(comment.getCreatedAt())
                                        );
                                        adapter.add(
                                                new CommentItem(
                                                        imageURL,
                                                        comment.getWriter().getName(),
                                                        comment.getCreatedAt(),
                                                        comment.getBody(),
                                                        comment.getPoint(),
                                                        BICYCLE_COMMENT
                                                )
                                        );
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
        } else if (from.equals(ListerMainActivity.TAG)) {
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
                                                    comment.getPoint(),
                                                    LISTER_COMMENT
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
