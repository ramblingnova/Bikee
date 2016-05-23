package com.example.tacademy.bikee.common.sidemenu;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Inquires;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.lister.ListerMainActivity;
import com.example.tacademy.bikee.renter.RenterMainActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputInquiryActivity extends AppCompatActivity {
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_icon_back_image_view)
    ImageView toolbarLeftIconBackImageView;
    @Bind(R.id.toolbar_center_icon_image_view)
    ImageView toolbarCenterIconImageView;
    @Bind(R.id.toolbar_right_icon_image_view)
    ImageView toolbarRightIconImageView;
    @Bind(R.id.activity_input_inquiry_title_edit_text)
    EditText title_edit_text;
    @Bind(R.id.activity_input_inquiry_description_edit_text)
    EditText description_edit_text;

    private Intent intent;
    private String from;

    private static final String TAG = "INPUT_INQUIRY_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_inquiry);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_input_inquiry_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        ButterKnife.bind(this);

        intent = getIntent();
        from = intent.getStringExtra("FROM");

        /* 툴바 배경 */
        if (Build.VERSION.SDK_INT < 23)
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue));
        else
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue, getTheme()));

        /* 툴바 왼쪽 */
        toolbarLeftIconBackImageView.setVisibility(View.VISIBLE);

        /* 툴바 가운데 */
        toolbarCenterIconImageView.setVisibility(View.VISIBLE);

        /* 툴바 오른쪽 */
        if (from.equals(RenterMainActivity.TAG))
            toolbarRightIconImageView.setImageResource(R.drawable.rider_main_icon);
        else if (from.equals(ListerMainActivity.TAG))
            toolbarRightIconImageView.setImageResource(R.drawable.lister_main_icon);
    }

    @OnClick({R.id.toolbar_left_icon_layout,
    R.id.activity_input_inquiry_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_icon_layout:
                onBackPressed();
                break;
            case R.id.activity_input_inquiry_button:
                Inquires inquires = new Inquires();
                inquires.setTitle(title_edit_text.getText().toString());
                inquires.setBody(description_edit_text.getText().toString());
                NetworkManager.getInstance().insertInquiry(
                        inquires,
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
                                finish();
                            }
                        });
                break;
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
