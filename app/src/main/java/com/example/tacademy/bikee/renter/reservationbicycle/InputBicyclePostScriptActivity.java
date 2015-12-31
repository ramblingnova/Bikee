package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.google.gson.Gson;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InputBicyclePostScriptActivity extends AppCompatActivity {
    private Intent intent;
    @Bind(R.id.activity_input_bicycle_post_script_input_post_script_edit_text)
    EditText et;
    @Bind(R.id.activity_input_bicycle_post_script_rating_bar)
    RatingBar rb;
    private String bicycleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_bicycle_post_script);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_input_bicycle_post_script_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        ButterKnife.bind(this);

        intent = getIntent();
        bicycleId = intent.getStringExtra("ID");
    }

    @OnClick(R.id.activity_input_bicycle_post_script_input_button) void inputPostScript() {
        Comment comment = new Comment();
        comment.setBody(et.getText().toString());
        comment.setPoint((int) rb.getRating());
        NetworkManager.getInstance().insertBicycleComment(bicycleId, comment, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Code : " + receiveObject.getCode()
                                + ", Success : " + receiveObject.isSuccess()
                                + ", Msg : " + receiveObject.getMsg()
                                + ", Error : "
                );
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
                finish();
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
