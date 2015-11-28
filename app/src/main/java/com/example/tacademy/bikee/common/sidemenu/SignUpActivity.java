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

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.User;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText password;
    private Button btn;

    public static final int SIGN_UP_ACTIVITY = 1;
    public static final String ACTIVITY_SIGN_UP_IMAGE = "activity_sign_up_image";
    public static final String ACTIVITY_SIGN_UP_NAME = "activity_sign_up_name";
    public static final String ACTIVITY_SIGN_UP_EMAIL = "activity_sign_up_email";
    public static final String ACTIVITY_SIGN_UP_PHONE = "activity_sign_up_phone";
    public static final String ACTIVITY_SIGN_UP_PASSWORD = "activity_sign_up_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_sign_up_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        name = (EditText) findViewById(R.id.activity_sign_up_input_name_edit_text);
        email = (EditText) findViewById(R.id.activity_sign_up_input_mail_address_edit_text);
        phone = (EditText) findViewById(R.id.activity_sign_up_input_cellphone_edit_text);
        password = (EditText) findViewById(R.id.activity_sign_up_input_password_edit_text);
        btn = (Button)findViewById(R.id.activity_sign_up_sign_up_button);
        btn.setOnClickListener(SignUpActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_sign_up_sign_up_button:
                User user = new User();
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setPassword(password.getText().toString());
                NetworkManager.getInstance().insertUser(user, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Code : " + receiveObject.getCode()
                                + ", Success : " + receiveObject.isSuccess()
                                + ", Msg : " + receiveObject.getMsg()
                        );
                        intent = getIntent();
                        intent.putExtra(ACTIVITY_SIGN_UP_NAME, name.getText().toString());
                        intent.putExtra(ACTIVITY_SIGN_UP_EMAIL, email.getText().toString());
                        intent.putExtra(ACTIVITY_SIGN_UP_PHONE, phone.getText().toString());
                        intent.putExtra(ACTIVITY_SIGN_UP_PASSWORD, password.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                    }
                });
                break;
            case R.id.activity_sign_up_authentication_button:
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
