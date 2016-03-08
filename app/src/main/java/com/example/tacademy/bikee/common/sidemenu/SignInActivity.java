package com.example.tacademy.bikee.common.sidemenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignInActivity extends AppCompatActivity {
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private String from;

    private Intent intent;

    public static final int SIGN_IN_ACTIVITY = 1;
    public static final String PREF_NAME = "prefs";
    public static final String ACTIVITY_SIGN_IN_IMAGE = "activity_sign_in_image";
    public static final String ACTIVITY_SIGN_IN_NAME = "activity_sign_in_name";
    public static final String ACTIVITY_SIGN_IN_EMAIL = "activity_sign_in_email";
    public static final String ACTIVITY_SIGN_IN_PASSWORD = "activity_sign_in_password";

    @Bind(R.id.activity_sign_in_user_email_edit_text)
    EditText emailEditText;
    @Bind(R.id.activity_sign_in_user_password_edit_text)
    EditText passwordEditText;
    @Bind(R.id.activity_sign_in_user_email_text_view)
    TextView emailTextView;
    @Bind(R.id.activity_sign_in_user_password_text_view)
    TextView passwordTextView;
    @Bind(R.id.activity_sign_in_sign_in_button)
    Button signInButton;
    @Bind(R.id.activity_sign_in_sign_out_button)
    Button signOutButton;
    @Bind(R.id.activity_sign_in_sign_up_string)
    TextView signUpTextView;

    static final private String TAG = "SIGN_IN_ACTIVITY";
    private String deviceID;
    private String registrationID;
    private String deviceName;
    private String deviceOS = "Android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        emailEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        mPrefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();

        initLogin();
    }

    @OnEditorAction({R.id.activity_sign_in_user_email_edit_text,
            R.id.activity_sign_in_user_password_edit_text})
    boolean next(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT
                && (v.getId() == R.id.activity_sign_in_user_email_edit_text)) {
            passwordEditText.requestFocus();
            return false;
        } else if ((actionId == EditorInfo.IME_ACTION_DONE)
                && (v.getId() == R.id.activity_sign_in_user_password_edit_text)) {
            return false;
        }
        return true;
    }

    @OnClick(R.id.activity_sign_in_sign_in_button)
    void signInButton() {
        NetworkManager.getInstance().login(emailEditText.getText().toString().trim(), passwordEditText.getText().toString(), new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "SignInActivity onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg()
                    );

                if (receiveObject.getCode() == 200) {
                    Toast.makeText(SignInActivity.this, "로그인됐습니다.", Toast.LENGTH_SHORT).show();
                    NetworkManager.getInstance().selectUserName(new Callback<ReceiveObject>() {
                        @Override
                        public void success(ReceiveObject receiveObject, Response response) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onResponse Success : " + receiveObject.isSuccess()
                                                + ", Code : " + receiveObject.getCode()
                                                + ", Msg : " + receiveObject.getMsg()
                                );
                            Result result = receiveObject.getResult().get(0);
                            if ((null != result.getImage())
                                    || (null != result.getImage().getCdnUri())
                                    || (null != result.getImage().getFiles())
                                    || (null != result.getImage().getFiles().get(0))) {
//                                    PropertyManager.getInstance().setImage(result.getImage().getCdnUri() + "/detail_" + result.getImage().getFiles().get(0));
                                PropertyManager.getInstance().setImage("https://s3-ap-northeast-1.amazonaws.com/bikee/KakaoTalk_20151128_194521490.png");
                            }
                            PropertyManager.getInstance().setName(result.getName());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onFailure Error : " + error.toString());
                        }
                    });
                    PropertyManager.getInstance().setEmail(emailEditText.getText().toString().trim());
                    PropertyManager.getInstance().setPassword(passwordEditText.getText().toString());
                    initView();

                    // TODO : need GCM test
                    checkPlayService();
                    resolveDeviceID();
                    resolveDeviceName();
                    new RequestTokenThread().start();
                    registerToken();
                    showStoredToken();
                } else {
                    Toast.makeText(SignInActivity.this, receiveObject.getMsg(), Toast.LENGTH_SHORT).show();
                    intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "SignInActivity onFailure Error : " + error.toString());
            }
        });
    }

    @OnClick(R.id.activity_sign_in_sign_out_button)
    void signOutButton() {
        NetworkManager.getInstance().logout(new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onResponse Success : " + receiveObject.isSuccess()
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
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onFailure Error : " + error.toString());
            }
        });
        initView();
    }

    @OnClick(R.id.activity_sign_in_sign_up_string)
    void signUpTextView() {
        intent = getIntent();
        from = intent.getStringExtra("FROM");
        intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("FROM", from);
        startActivityForResult(intent, SignUpActivity.SIGN_UP_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && (requestCode == SignUpActivity.SIGN_UP_ACTIVITY)) {
            final String image = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_IMAGE);
            final String name = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_NAME);
            final String email = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_EMAIL);
            final String password = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_PASSWORD);
            NetworkManager.getInstance().login(email, password, new Callback<ReceiveObject>() {
                @Override
                public void success(ReceiveObject receiveObject, Response response) {
                    if (BuildConfig.DEBUG)
                        Log.d("result", "onResponse Success : " + receiveObject.isSuccess()
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
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "onFailure Error : " + error.toString());
                }
            });
        }
    }

    private void initLogin() {
        if (!PropertyManager.getInstance().getEmail().equals("")
                || !PropertyManager.getInstance().getPassword().equals("")) {
            NetworkManager.getInstance().login(PropertyManager.getInstance().getEmail(),
                    PropertyManager.getInstance().getPassword(),
                    new Callback<ReceiveObject>() {
                        @Override
                        public void success(ReceiveObject receiveObject, Response response) {
                            if (BuildConfig.DEBUG)
                                Log.d("result", "onResponse Success : " + receiveObject.isSuccess()
                                                + ", Code : " + receiveObject.getCode()
                                                + ", Msg : " + receiveObject.getMsg()
                                );
                            initView();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onFailure Error : " + error.toString());
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

    void checkPlayService() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "isGooglePlayServicesAvailable : " + resultCode);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Google Paly Service is available to bikee!");
        } else {
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 0).show();
        }
    }

    private void resolveDeviceID() {
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Android ID : " + androidID);

        deviceID = androidID;
    }

    private void resolveDeviceName() {
        deviceName = Build.DEVICE;
    }

    class RequestTokenThread extends Thread {
        @Override
        public void run() {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Trying to register device");
            try {
                InstanceID instanceID = InstanceID.getInstance(SignInActivity.this);
                final String token = instanceID.getToken(getString(R.string.GCM_SenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                if (registrationID != token) {
                    registrationID = token;
                    saveRegistrationID();
                }
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Token : " + token);
            } catch (IOException e) {
                e.printStackTrace();
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Register Exception", e);
            }
        }
    }

    void registerToken() {
        if (deviceID == null)
            resolveDeviceID();
        if (deviceName == null)
            resolveDeviceName();

        NetworkManager.getInstance().registerToken(deviceID, registrationID, deviceOS, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg()
                    );

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent = getIntent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }, 5000);
            }

            @Override
            public void failure(RetrofitError error) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onFailure Error : " + error.toString());
            }
        });
    }

    void showStoredToken() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String storedToken = sharedPref.getString("REGISTRATION_ID", null);
        if (storedToken != null) {
            registrationID = storedToken;
        }
    }

    void saveRegistrationID() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("REGISTRATION_ID", registrationID);
        editor.commit();
    }
}
