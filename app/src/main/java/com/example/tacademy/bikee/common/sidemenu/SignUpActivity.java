package com.example.tacademy.bikee.common.sidemenu;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.dao.User;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity {
    private Intent intent;
    private String authId;
    private String authNum;

    public static final int SIGN_UP_ACTIVITY = 1;
    public static final String ACTIVITY_SIGN_UP_IMAGE = "activity_sign_up_image";
    public static final String ACTIVITY_SIGN_UP_NAME = "activity_sign_up_name";
    public static final String ACTIVITY_SIGN_UP_EMAIL = "activity_sign_up_email";
    public static final String ACTIVITY_SIGN_UP_PHONE = "activity_sign_up_phone";
    public static final String ACTIVITY_SIGN_UP_PASSWORD = "activity_sign_up_password";

    @Bind(R.id.activity_sign_up_input_name_edit_text) EditText name;
    @Bind(R.id.activity_sign_up_input_mail_address_edit_text) EditText email;
    @Bind(R.id.activity_sign_up_input_phone_edit_text) EditText phone;
    @Bind(R.id.activity_sign_up_input_password_edit_text) EditText password;
    @Bind(R.id.activity_sign_up_request_authentication_number_text_view) TextView reqAuth;
    @Bind(R.id.activity_sign_up_confirm_authentication_text_view) TextView confirmAuth;
    @Bind(R.id.activity_sign_up_sign_up_button) Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_sign_up_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.backable_tool_bar1, null);
        cView.findViewById(R.id.backable_tool_bar1_back_button_image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.backable_tool_bar1_back_button_image_view:
                        finish();
                        break;
                }
            }
        });
        getSupportActionBar().setCustomView(cView);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_sign_up_request_authentication_number_text_view) void reqAuth() {
//                // 인증번호요청하기
//                NetworkManager.getInstance().requestAuthenticationNumber(phone.getText().toString(), new Callback<ReceiveObject>() {
//                    @Override
//                    public void success(ReceiveObject receiveObject, Response response) {
//                        Log.i("result", "onResponse Code : " + receiveObject.getCode()
//                                        + ", Success : " + receiveObject.isSuccess()
//                                        + ", Msg : " + receiveObject.getMsg()
//                                        + ", Error : "
//                        );
//                        if (receiveObject.getCode() == 200) {
//                            for (Result result : receiveObject.getResult()) {
//                                Log.i("result", "onResponse Id : " + result.getId());
//                                authId = result.getId();
//                                authNum = "" + result.getAuth_number();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.e("error", "onFailure Error : " + error.toString());
//                    }
//                });
    };

    @OnClick(R.id.activity_sign_up_confirm_authentication_text_view) void confirmAuth() {
//                // 인증번호확인하기
//                String authid = "564d9d0e32a130ea2a0731dc";
//                String auth_number = "447671";
//                NetworkManager.getInstance().confirmAuthenticationNumber(authId, authNum, new Callback<ReceiveObject>() {
//                    @Override
//                    public void success(ReceiveObject receiveObject, Response response) {
//                        Log.i("result", "onResponse Code : " + receiveObject.getCode()
//                                        + ", Success : " + receiveObject.isSuccess()
//                                        + ", Msg : " + receiveObject.getMsg()
//                                        + ", Error : "
//                        );
//                        for (Result result : receiveObject.getResult()) {
//                            Log.i("result", "onResponse Accepted : " + result.isAccepted());
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.e("error", "onFailure Error : " + error.toString());
//                    }
//                });
    }

    @OnClick(R.id.activity_sign_up_sign_up_button) void btn() {
        User user = new User();
        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPhone(phone.getText().toString());
        user.setPassword(password.getText().toString());
        NetworkManager.getInstance().insertUser(user, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "회원가입 onResponse Code : " + receiveObject.getCode()
                                + ", Success : " + receiveObject.isSuccess()
                                + ", Msg : " + receiveObject.getMsg()
                );
                if (receiveObject.getCode() == 200) {
                    Toast.makeText(SignUpActivity.this, "회원가입됐습니다.", Toast.LENGTH_SHORT).show();
                    intent = getIntent();
                    intent.putExtra(ACTIVITY_SIGN_UP_NAME, name.getText().toString());
                    intent.putExtra(ACTIVITY_SIGN_UP_EMAIL, email.getText().toString());
                    intent.putExtra(ACTIVITY_SIGN_UP_PHONE, phone.getText().toString());
                    intent.putExtra(ACTIVITY_SIGN_UP_PASSWORD, password.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, receiveObject.getStack().get(0), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
            }
        });
    };

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
