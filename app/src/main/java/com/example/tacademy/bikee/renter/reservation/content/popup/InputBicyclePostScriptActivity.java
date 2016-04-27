package com.example.tacademy.bikee.renter.reservation.content.popup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputBicyclePostScriptActivity extends AppCompatActivity {
    private Intent intent;
    @Bind(R.id.activity_input_bicycle_post_script_input_post_script_edit_text)
    EditText et;
    @Bind(R.id.activity_input_bicycle_post_script_rating_bar)
    RatingBar rb;
    private String bicycleId;

    private static final String TAG = "INPUT_B_P_S_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_bicycle_post_script);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_input_bicycle_post_script_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_backable_tool_bar);

        ButterKnife.bind(this);

        intent = getIntent();
        bicycleId = intent.getStringExtra("ID");
    }

    @OnClick(R.id.renter_backable_tool_bar_back_button_layout)
    void back(View view) {
        super.onBackPressed();
    }

    @OnClick(R.id.activity_input_bicycle_post_script_input_button)
    void inputPostScript() {
        Comment comment = new Comment();
        comment.setBody(et.getText().toString());
        comment.setPoint((int) rb.getRating());
        NetworkManager.getInstance().insertBicycleComment(
                bicycleId,
                comment,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        Log.i("result", "onResponse Code : " + receiveObject.getCode()
                                        + ", Success : " + receiveObject.isSuccess()
                                        + ", Msg : " + receiveObject.getMsg()
                                        + ", Error : "
                        );
                        finish();
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
