package com.example.tacademy.bikee.common.sidemenu;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Facebook;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.FacebookNetworkManager;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.sendbird.android.SendBird;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    @Bind(R.id.activity_sign_in_user_email_edit_text)
    EditText emailEditText;
    @Bind(R.id.activity_sign_in_user_password_layout)
    RelativeLayout passwordLayout;
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
    @Bind(R.id.activity_sign_in_facebook_button)
    Button facebookButton;
    @Bind(R.id.activity_sign_in_sign_up_string)
    TextView signUpTextView;

    private Intent intent;
    private String from;
    private CallbackManager callbackManager = CallbackManager.Factory.create();
    private LoginManager mLoginManager = LoginManager.getInstance();
    private AccessToken token;
    private String deviceID;
    private String registrationID;
    private String deviceName;
    private String deviceOS = "Android";
    final String appId = "2E377FE1-E1AD-4484-A66F-696AF1306F58";
    private String userId;
    private String userName;
    private String gcmRegToken;

    public static final int SIGN_IN_ACTIVITY = 1;
    private static final String TAG = "SIGN_IN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        intent = getIntent();

        emailEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        // TODO : SharedPreferences에 정보가 있더라도 인터넷 연결 여부에 따라 처리해야 한다.
        initView();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("REQUEST_CODE", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "!RESULT_CANCELED" + " resultCode : " + resultCode + ", requestCode : " + requestCode);

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if ((resultCode == RESULT_CANCELED)
                && (requestCode == SignUpActivity.SIGN_UP_FACEBOOK)) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "resultCode : RESULT_CANCELED, requestCode : SIGN_UP_FACEBOOK");

            mLoginManager.logOut();
        } else if ((resultCode == RESULT_OK)
                && (requestCode == SignUpActivity.SIGN_UP_FACEBOOK)) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "resultCode : RESULT_OK, requestCode : SIGN_UP_FACEBOOK");

            afterSignIn(PropertyManager.SIGN_IN_FACEBOOK_STATE);

            callbackManager.onActivityResult(requestCode, resultCode, data);

            finish();
        } else if ((resultCode == RESULT_OK)
                && (requestCode == SignUpActivity.SIGN_UP_LOCAL)) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "resultCode : RESULT_OK, requestCode : SIGN_UP_LOCAL");

            String email = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_EMAIL);
            String password = data.getStringExtra(SignUpActivity.ACTIVITY_SIGN_UP_PASSWORD);

            emailEditText.setText(email);
            passwordEditText.setText(password);

            PropertyManager.getInstance().setEmail(email);
            PropertyManager.getInstance().setPassword(password);

            signInLocal(email, password);
        }
    }

    @OnEditorAction({R.id.activity_sign_in_user_email_edit_text,
            R.id.activity_sign_in_user_password_edit_text})
    boolean nextFocus(TextView v, int actionId, KeyEvent event) {
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
    void signInLocalButton() {
        signInLocal(emailEditText.getText().toString().trim(), passwordEditText.getText().toString());
    }

    public void signInLocal(String email, String password) {
        NetworkManager.getInstance().login(
                email,
                password,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "SignInActivity onResponse Success : " + receiveObject.isSuccess()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Msg : " + receiveObject.getMsg()
                            );

                        if (receiveObject.getCode() == 200) {
                            Toast.makeText(SignInActivity.this, "로그인됐습니다.", Toast.LENGTH_SHORT).show();
                            afterSignIn(PropertyManager.SIGN_IN_LOCAL_STATE);
                        } else {
                            Toast.makeText(SignInActivity.this, receiveObject.getMsg(), Toast.LENGTH_SHORT).show();
                            intent = getIntent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "SignInActivity onFailure Error : " + t.toString());
                    }
                });
    }

    @OnClick(R.id.activity_sign_in_sign_out_button)
    void signOutButton() {
        switch (PropertyManager.getInstance().getSignInState()) {
            case PropertyManager.SIGN_IN_FACEBOOK_STATE:
                signOut(PropertyManager.SIGN_IN_FACEBOOK_STATE);
                break;
            case PropertyManager.SIGN_IN_LOCAL_STATE:
                signOut(PropertyManager.SIGN_IN_LOCAL_STATE);
                break;
            case PropertyManager.SIGN_OUT_STATE:
                break;
        }
    }

    private void signOut(final int signInState) {
        NetworkManager.getInstance().logout(
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();

                        if (receiveObject.isSuccess()) {
                            // TODO : null pointer exception -> receive object == null ???
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "logout onResponse Success : " + receiveObject.isSuccess()
                                        + "\nCode : " + receiveObject.getCode()
                                        + "\nMsg : " + receiveObject.getMsg());

                            PropertyManager.getInstance().setSignInState(PropertyManager.SIGN_OUT_STATE);
                            if (signInState == PropertyManager.SIGN_IN_FACEBOOK_STATE)
                                mLoginManager.logOut();
                            initView();
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "logout onResponse Success : " + receiveObject.isSuccess()
                                        + "\nCode : " + receiveObject.getCode()
                                        + "\nMsg : " + receiveObject.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
    }

    @OnClick(R.id.activity_sign_in_facebook_button)
    void signInFacebookButton(View view) {
        signInFacebook();
    }

    private void signInFacebook() {
        sleepSignOutButton();
        mLoginManager.logInWithReadPermissions(SignInActivity.this, Arrays.asList("public_profile", "email"));
        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                token = AccessToken.getCurrentAccessToken();

                if (BuildConfig.DEBUG)
                    Log.d(TAG, "AccessToken : " + token.getToken());

                FacebookNetworkManager.getInstance().loginFacebookToken(
                        SignInActivity.this,
                        token.getToken(),
                        "NOTREGISTER",
                        new FacebookNetworkManager.OnResultListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                                if (result.equals("OK")) {
                                    if (BuildConfig.DEBUG)
                                        Log.d(TAG, "OK");
                                    PropertyManager.getInstance().setFacebookId(token.getUserId());
                                    afterSignIn(PropertyManager.SIGN_IN_FACEBOOK_STATE);
                                } else if (result.equals("NOTREGISTER")) {
//                                    private boolean isLogin() {
//                                        AccessToken token = AccessToken.getCurrentAccessToken();
//                                        return token != null;
//                                    }
                                    NetworkManager.getInstance().isSignedInFacebook(
                                            token.getUserId(),
                                            null,
                                            new Callback<ReceiveObject>() {
                                                @Override
                                                public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                                    ReceiveObject receiveObject = response.body();
                                                    if (receiveObject != null) {
                                                        if (BuildConfig.DEBUG)
                                                            Log.d(TAG, "receiveObject != null");
                                                        if (receiveObject.isSuccess()) {
                                                            if (BuildConfig.DEBUG)
                                                                Log.d(TAG, "success : " + receiveObject.isSuccess());
                                                            PropertyManager.getInstance().setFacebookId(token.getUserId());
                                                            Facebook facebook = new Facebook();
                                                            facebook.setAccess_token(token.getToken());
                                                            NetworkManager.getInstance().signInFacebook(
                                                                    facebook,
                                                                    null,
                                                                    new Callback<ReceiveObject>() {
                                                                        @Override
                                                                        public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                                                            ReceiveObject receiveObject = response.body();
                                                                            afterSignIn(PropertyManager.SIGN_IN_FACEBOOK_STATE);
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                                                            if (BuildConfig.DEBUG)
                                                                                Log.d(TAG, "onFailure Error : " + t.toString());
                                                                        }
                                                                    });
                                                        } else {
                                                            if (BuildConfig.DEBUG) {
                                                                Log.d(TAG, "success : " + receiveObject.isSuccess());
                                                                Log.d(TAG, "userID : " + token.getUserId());
                                                                Log.d(TAG, "NOTREGISTER");
                                                            }
                                                            from = intent.getStringExtra("FROM");
                                                            intent = new Intent(SignInActivity.this, SignUpActivity.class);
                                                            intent.putExtra("FROM", from);
                                                            startActivityForResult(intent, SignUpActivity.SIGN_UP_FACEBOOK);
                                                        }
                                                    } else {
                                                        if (BuildConfig.DEBUG)
                                                            Log.d(TAG, "receiveObject == null");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                                    if (BuildConfig.DEBUG)
                                                        Log.d(TAG, "onFailure Error : " + t.toString());
                                                    wakeSignOutButton();
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onFail(int code) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "onFail");
                                wakeSignOutButton();
                            }
                        });
            }

            @Override
            public void onCancel() {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onCancel");
                wakeSignOutButton();
            }

            @Override
            public void onError(FacebookException error) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onError");
                wakeSignOutButton();
            }
        });
    }

    public void afterSignIn(final int signInState) {
        NetworkManager.getInstance().receiveProfile(
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();

                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg());

                        /* sendGCMToken to server */
                        checkPlayService();
                        getDeviceID();
                        getDeviceName();
                        getRegistrationID();
                        new RequestTokenThread().start();
                        sendGCMToken();

                        /* Property initialization */
                        for (Result result : receiveObject.getResult())
                            if (result.get_id() != null) {
                                PropertyManager.getInstance().set_id(result.get_id());
                                PropertyManager.getInstance().setImage("https://s3-ap-northeast-1.amazonaws.com/bikee/KakaoTalk_20151128_194521490.png");
                                PropertyManager.getInstance().setName(result.getName());
                                PropertyManager.getInstance().setEmail(result.getEmail());
                                if (signInState == PropertyManager.SIGN_IN_FACEBOOK_STATE) {
                                    PropertyManager.getInstance().setSignInState(PropertyManager.SIGN_IN_FACEBOOK_STATE);
                                } else if (signInState == PropertyManager.SIGN_IN_LOCAL_STATE) {
                                    PropertyManager.getInstance().setPassword(passwordEditText.getText().toString());
                                    PropertyManager.getInstance().setSignInState(PropertyManager.SIGN_IN_LOCAL_STATE);
                                }
                                userId = result.get_id();
                                userName = result.getName();
                                break;
                            }
                        gcmRegToken = PropertyManager.getInstance().getGCMToken();

                        /* SendBird initialization and login */
                        SendBird.init(SignInActivity.this, appId);
                        SendBird.login(SendBird.LoginOption.build(userId).setUserName(userName).setGCMRegToken(gcmRegToken));

                        /* view initialization */
                        initView();
                        wakeSignOutButton();
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
    }

    @OnClick(R.id.activity_sign_in_sign_up_string)
    void signUpTextView() {
        from = intent.getStringExtra("FROM");
        intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("FROM", from);
        startActivityForResult(intent, SignUpActivity.SIGN_UP_LOCAL);
    }

    private void initView() {
        switch (PropertyManager.getInstance().getSignInState()) {
            case PropertyManager.SIGN_IN_FACEBOOK_STATE:
                emailTextView.setVisibility(View.VISIBLE);
                emailTextView.setText(PropertyManager.getInstance().getEmail());
                emailEditText.setVisibility(View.INVISIBLE);
                passwordLayout.setVisibility(View.GONE);
                signInButton.setVisibility(View.GONE);
                signOutButton.setVisibility(View.VISIBLE);
                signUpTextView.setVisibility(View.INVISIBLE);
                break;
            case PropertyManager.SIGN_IN_LOCAL_STATE:
                emailTextView.setVisibility(View.VISIBLE);
                emailTextView.setText(PropertyManager.getInstance().getEmail());
                emailEditText.setVisibility(View.INVISIBLE);
                passwordLayout.setVisibility(View.VISIBLE);
                passwordTextView.setVisibility(View.VISIBLE);
                passwordTextView.setText(PropertyManager.getInstance().getPassword());
                passwordEditText.setVisibility(View.INVISIBLE);
                signInButton.setVisibility(View.GONE);
                signOutButton.setVisibility(View.VISIBLE);
                signUpTextView.setVisibility(View.INVISIBLE);
                facebookButton.setClickable(false);
                facebookButton.setEnabled(false);
                break;
            case PropertyManager.SIGN_OUT_STATE:
                emailTextView.setVisibility(View.INVISIBLE);
                emailEditText.setVisibility(View.VISIBLE);
                passwordLayout.setVisibility(View.VISIBLE);
                passwordTextView.setVisibility(View.INVISIBLE);
                passwordEditText.setVisibility(View.VISIBLE);
                signInButton.setVisibility(View.VISIBLE);
                signOutButton.setVisibility(View.GONE);
                signUpTextView.setVisibility(View.VISIBLE);
                facebookButton.setClickable(true);
                facebookButton.setEnabled(true);
                break;
        }
    }

    public void sleepSignOutButton() {
        signOutButton.setEnabled(false);
        signOutButton.setClickable(false);
    }

    public void wakeSignOutButton() {
        signOutButton.setEnabled(true);
        signOutButton.setClickable(true);
    }

    private void checkPlayService() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "isGooglePlayServicesAvailable : " + resultCode);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Google Play Service is available to bikee...");
        } else {
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 0).show();
        }
    }

    private void getDeviceID() {
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Android ID : " + androidID);

        deviceID = androidID;
    }

    private void getDeviceName() {
        deviceName = Build.DEVICE;
    }

    private void getRegistrationID() {
        String storedToken = PropertyManager.getInstance().getGCMToken();
        registrationID = storedToken;
    }

    private void saveRegistrationID() {
        PropertyManager.getInstance().setGCMToken(registrationID);
    }

    class RequestTokenThread extends Thread {
        @Override
        public void run() {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Trying to register device...");
            try {
                InstanceID instanceID = InstanceID.getInstance(SignInActivity.this);
                final String token = instanceID.getToken(getString(R.string.GCM_SenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                if (registrationID != token) {
                    registrationID = token;
                    saveRegistrationID();
                    sendGCMToken();
                }
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "GCM Token : " + token);
            } catch (IOException e) {
                e.printStackTrace();
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Get GCM Token Exception...", e);
            }
        }
    }

    private void sendGCMToken() {
        if (deviceID == null)
            getDeviceID();
        if (deviceName == null)
            getDeviceName();

        if (BuildConfig.DEBUG)
            Log.d(TAG, "deviceID : " + deviceID
                            + "\nregistrationID(GCM Token) : " + registrationID
                            + "\ndeviceOS : " + deviceOS
            );
        NetworkManager.getInstance().sendGCMToken(
                deviceID,
                registrationID,
                deviceOS,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "sendGCMToken onResponse Success : " + receiveObject.isSuccess()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Msg : " + receiveObject.getMsg()
                            );

                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
