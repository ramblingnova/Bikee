package com.bigtion.bikee.common.sidemenu;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigtion.bikee.etc.manager.NetworkManager;
import com.bigtion.bikee.renter.RenterMainActivity;
import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.dao.Inquires;
import com.bigtion.bikee.etc.dao.ReceiveObject;
import com.bigtion.bikee.lister.ListerMainActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryActivity extends AppCompatActivity {
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_back_icon_image_view)
    ImageView toolbarLeftBackIconImageView;
    @Bind(R.id.toolbar_center_text_view)
    TextView toolbarCenterTextView;
    @Bind(R.id.toolbar_right_mode_icon_image_view)
    ImageView toolbarRightModeIconImageView;
    @Bind(R.id.activity_inquiry_title_edit_text)
    EditText title_edit_text;
    @Bind(R.id.activity_inquiry_description_edit_text)
    EditText description_edit_text;
    @Bind(R.id.activity_inquiry_register_button)
    Button registerButton;

    private Intent intent;
    private String from;
    private boolean[] conditions;

    private static final int MAX_CONDITION = 2;
    private static final String TAG = "INQUIRY_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_inquiry_toolbar);
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
        toolbarLeftBackIconImageView.setVisibility(View.VISIBLE);

        /* 툴바 가운데 */
        toolbarCenterTextView.setText("고객센터");
        if (Build.VERSION.SDK_INT < 23)
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
        else
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
        toolbarCenterTextView.setVisibility(View.VISIBLE);

        /* 툴바 오른쪽 */
        if (from.equals(RenterMainActivity.TAG))
            toolbarRightModeIconImageView.setImageResource(R.drawable.rider_main_icon);
        else if (from.equals(ListerMainActivity.TAG))
            toolbarRightModeIconImageView.setImageResource(R.drawable.lister_main_icon);
        toolbarRightModeIconImageView.setVisibility(View.VISIBLE);

        conditions = new boolean[MAX_CONDITION];
        for (int i = 0; i < MAX_CONDITION; i++)
            conditions[i] = false;

        title_edit_text.addTextChangedListener(
                new CustomizedTextWatcher(
                        R.id.activity_inquiry_title_edit_text
                )
        );
        description_edit_text.addTextChangedListener(
                new CustomizedTextWatcher(
                        R.id.activity_inquiry_description_edit_text
                ));
    }

    @OnClick({R.id.toolbar_left_layout,
            R.id.activity_inquiry_back_button,
            R.id.activity_inquiry_register_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_inquiry_back_button:
            case R.id.toolbar_left_layout:
                onBackPressed();
                break;
            case R.id.activity_inquiry_register_button:
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
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "insertInquiry onResponse Code : " + receiveObject.getCode()
                                                    + "\nSuccess : " + receiveObject.isSuccess()
                                                    + "\nMsg : " + receiveObject.getMsg()
                                    );
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "insertInquiry onFailure Error", t);
                            }
                        });
                break;
        }
    }

    private class CustomizedTextWatcher implements TextWatcher {
        private int id;

        public CustomizedTextWatcher(int id) {
            this.id = id;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (id) {
                case R.id.activity_inquiry_title_edit_text:
                    if (s.length() > 0)
                        conditions[0] = true;
                    else
                        conditions[0] = false;
                    break;
                case R.id.activity_inquiry_description_edit_text:
                    if (s.length() > 0)
                        conditions[1] = true;
                    else
                        conditions[1] = false;
                    break;
            }
            enableButton();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public void enableButton() {
        for (int i = MAX_CONDITION - 1; i >= 0; i--)
            if (!conditions[i]) {
                registerButton.setEnabled(false);
                return;
            }
        registerButton.setEnabled(true);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
