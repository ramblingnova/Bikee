package com.example.tacademy.bikee.common.sidemenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.renter.sidemenu.cardmanagement.RegisterCardActivity;

import java.util.PropertyPermission;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    final private static int SIGN_UP_ACTIVITY = 1;

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private EditText email_edit_text;
    private EditText password_edit_text;
    private TextView email_text_view;
    private TextView password_text_view;
    private Button sign_in;
    private TextView sign_up;

    private String email_edit_text_string;
    private String password_edit_text_string;

    public static final String PREF_NAME = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_sign_in_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email_edit_text = (EditText) findViewById(R.id.activity_sign_in_user_email_edit_text);
        email_text_view = (TextView) findViewById(R.id.activity_sign_in_user_email_text_view);
        password_edit_text = (EditText) findViewById(R.id.activity_sign_in_user_password_edit_text);
        password_text_view = (TextView) findViewById(R.id.activity_sign_in_user_password_text_view);
        sign_in = (Button) findViewById(R.id.activity_sign_in_sign_in_button);
        sign_in.setOnClickListener(SignInActivity.this);
        sign_up = (TextView) findViewById(R.id.activity_sign_in_sign_up_string);
        sign_up.setOnClickListener(SignInActivity.this);

        mPrefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();

        if (!PropertyManager.getInstance().getEmail().equals("") || !PropertyManager.getInstance().getPassword().equals("") ) {
            email_text_view.setVisibility(View.VISIBLE);
            email_text_view.setText(PropertyManager.getInstance().getEmail());
            password_text_view.setVisibility(View.VISIBLE);
            password_text_view.setText(PropertyManager.getInstance().getPassword());
            email_edit_text.setVisibility(View.INVISIBLE);
            password_edit_text.setVisibility(View.INVISIBLE);
            sign_in.setText("로그인 완료");
            sign_in.setClickable(false);
            sign_up.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_sign_in_sign_in_button:
                email_edit_text_string = email_edit_text.getText().toString();
                password_edit_text_string = password_edit_text.getText().toString();
                NetworkManager.getInstance().login(email_edit_text_string, password_edit_text_string, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Success : " + receiveObject.isSuccess() + ", Code : " + receiveObject.getCode() + ", Msg : " + receiveObject.getMsg());
                        email_text_view.setVisibility(View.VISIBLE);
                        email_text_view.setText(email_edit_text_string);
                        password_text_view.setVisibility(View.VISIBLE);
                        password_text_view.setText(password_edit_text_string);
                        email_edit_text.setVisibility(View.INVISIBLE);
                        password_edit_text.setVisibility(View.INVISIBLE);
                        sign_in.setText("로그인 완료");
                        sign_in.setClickable(false);
                        sign_up.setVisibility(View.INVISIBLE);

                        PropertyManager.getInstance().setEmail(email_edit_text_string);
                        PropertyManager.getInstance().setPassword(password_edit_text_string);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                    }
                });
                break;
            case R.id.activity_sign_in_sign_up_string:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivityForResult(intent, SIGN_UP_ACTIVITY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SIGN_UP_ACTIVITY) {
            email_edit_text_string = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_INPUT_MAIL_ADDRESS_EDIT_TEXT);
            password_edit_text_string = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_INPUT_PASSWROD_EDIT_TEXT);
            NetworkManager.getInstance().login(email_edit_text_string, password_edit_text_string, new Callback<ReceiveObject>() {
                @Override
                public void success(ReceiveObject receiveObject, Response response) {
                    Log.i("result", "onResponse Success : " + receiveObject.isSuccess() + ", Code : " + receiveObject.getCode() + ", Msg : " + receiveObject.getMsg());
                    email_text_view.setVisibility(View.VISIBLE);
                    email_text_view.setText(email_edit_text_string);
                    password_text_view.setVisibility(View.VISIBLE);
                    password_text_view.setText(password_edit_text_string);
                    email_edit_text.setVisibility(View.INVISIBLE);
                    password_edit_text.setVisibility(View.INVISIBLE);
                    sign_in.setText("로그인 완료");
                    sign_in.setClickable(false);
                    sign_up.setVisibility(View.INVISIBLE);

                    PropertyManager.getInstance().setEmail(email_edit_text_string);
                    PropertyManager.getInstance().setPassword(password_edit_text_string);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("error", "onFailure Error : " + error.toString());
                }
            });
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
}
