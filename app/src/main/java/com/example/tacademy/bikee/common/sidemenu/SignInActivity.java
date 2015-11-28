package com.example.tacademy.bikee.common.sidemenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private Intent intent;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView emailTextView;
    private TextView passwordTextView;
    private Button signInButton;
    private Button signOutButton;
    private TextView signUpTextView;

    public static final int SIGN_IN_ACTIVITY = 1;
    public static final String PREF_NAME = "prefs";
    public static final String ACTIVITY_SIGN_IN_IMAGE = "activity_sign_in_image";
    public static final String ACTIVITY_SIGN_IN_NAME = "activity_sign_in_name";
    public static final String ACTIVITY_SIGN_IN_EMAIL = "activity_sign_in_email";
    public static final String ACTIVITY_SIGN_IN_PASSWORD = "activity_sign_in_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = (EditText) findViewById(R.id.activity_sign_in_user_email_edit_text);
        emailTextView = (TextView) findViewById(R.id.activity_sign_in_user_email_text_view);
        passwordEditText = (EditText) findViewById(R.id.activity_sign_in_user_password_edit_text);
        passwordTextView = (TextView) findViewById(R.id.activity_sign_in_user_password_text_view);
        signInButton = (Button) findViewById(R.id.activity_sign_in_sign_in_button);
        signInButton.setOnClickListener(SignInActivity.this);
        signOutButton = (Button) findViewById(R.id.activity_sign_in_sign_out_button);
        signOutButton.setOnClickListener(SignInActivity.this);
        signUpTextView = (TextView) findViewById(R.id.activity_sign_in_sign_up_string);
        signUpTextView.setOnClickListener(SignInActivity.this);

        mPrefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();

        initLogin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_sign_in_sign_in_button:
                NetworkManager.getInstance().login(emailEditText.getText().toString(), passwordEditText.getText().toString(), new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                        + ", Code : " + receiveObject.getCode()
                                        + ", Msg : " + receiveObject.getMsg()
                        );
                        NetworkManager.getInstance().selectUserName(new Callback<ReceiveObject>() {
                            @Override
                            public void success(ReceiveObject receiveObject, Response response) {
                                Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                                + ", Code : " + receiveObject.getCode()
                                                + ", Msg : " + receiveObject.getMsg()
                                );
                                Result result = receiveObject.getResult().get(0);
                                if ((null != result.getImage())
                                        || (null != result.getImage().getCdnUri())
                                        || (null != result.getImage().getFiles())
                                        || (null != result.getImage().getFiles().get(0))) {
                                    PropertyManager.getInstance().setImage(result.getImage().getCdnUri() + "/mini_" + result.getImage().getFiles().get(0));
                                }
                                PropertyManager.getInstance().setName(result.getName());
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e("error", "onFailure Error : " + error.toString());
                            }
                        });
                        PropertyManager.getInstance().setEmail(emailEditText.getText().toString());
                        PropertyManager.getInstance().setPassword(passwordEditText.getText().toString());
                        initView();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                    }
                });
                break;
            case R.id.activity_sign_in_sign_out_button:
                NetworkManager.getInstance().logout(new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                        + ", Code : " + receiveObject.getCode()
                                        + ", Msg : " + receiveObject.getMsg()
                        );
                        PropertyManager.getInstance().setImage("");
                        PropertyManager.getInstance().setName("");
                        PropertyManager.getInstance().setEmail("");
                        PropertyManager.getInstance().setPassword("");
                        initView();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                    }
                });
                initView();
                break;
            case R.id.activity_sign_in_sign_up_string:
                intent = new Intent(this, SignUpActivity.class);
                startActivityForResult(intent, SignUpActivity.SIGN_UP_ACTIVITY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SignUpActivity.SIGN_UP_ACTIVITY) {
            final String image = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_IMAGE);
            final String name = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_NAME);
            final String email = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_EMAIL);
            final String password = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_PASSWORD);
            NetworkManager.getInstance().login(email, password, new Callback<ReceiveObject>() {
                @Override
                public void success(ReceiveObject receiveObject, Response response) {
                    Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg()
                    );
                    PropertyManager.getInstance().setImage(image);
                    PropertyManager.getInstance().setName(name);
                    PropertyManager.getInstance().setEmail(email);
                    PropertyManager.getInstance().setPassword(password);
                    initView();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("error", "onFailure Error : " + error.toString());
                }
            });
        }
    }

    private void initLogin() {
        if (!PropertyManager.getInstance().getEmail().equals("")
                || !PropertyManager.getInstance().getPassword().equals("")) {
            NetworkManager.getInstance().login(PropertyManager.getInstance().getEmail(), PropertyManager.getInstance().getPassword(), new Callback<ReceiveObject>() {
                @Override
                public void success(ReceiveObject receiveObject, Response response) {
                    Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg()
                    );
                    initView();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("error", "onFailure Error : " + error.toString());
                }
            });
        }
    }

    /* SharedPreferences에 정보가 있는 경우 ->
    *       이메일, 패스워드에 대한 에디트텍스트가 텍스트뷰로 변경,
    *       로그인 버튼이 로그아웃 버튼으로 변경,
    *       회원가입 텍스트뷰가 사라짐  */
    private void initView() {
        // SharedPreferences에 정보가 있더라도 인터넷 연결 여부에 따라 처리해야 한다.
        if (!PropertyManager.getInstance().getEmail().equals("")
                || !PropertyManager.getInstance().getPassword().equals("")) {
            emailTextView.setVisibility(View.VISIBLE);
            emailTextView.setText(PropertyManager.getInstance().getEmail());
            emailEditText.setVisibility(View.INVISIBLE);
            passwordTextView.setVisibility(View.VISIBLE);
            passwordTextView.setText(PropertyManager.getInstance().getPassword());
            passwordEditText.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
            signUpTextView.setVisibility(View.INVISIBLE);
        } else {
            emailTextView.setVisibility(View.INVISIBLE);
            emailEditText.setVisibility(View.VISIBLE);
            passwordTextView.setVisibility(View.INVISIBLE);
            passwordEditText.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
            signUpTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        intent = getIntent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
