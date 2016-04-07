package com.example.tacademy.bikee.common.sidemenu;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Facebook;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.User;
import com.example.tacademy.bikee.etc.manager.FacebookNetworkManager;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.etc.utils.RegExUtil;
import com.example.tacademy.bikee.lister.ListerMainActivity;
import com.example.tacademy.bikee.renter.RenterMainActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity {
    private Intent intent;
    private String from;
    private int requestCode;
    private String password;
    private String authId;
    private String authNum;
    private AccessToken token;

    @Bind(R.id.activity_sign_up_input_name_edit_text)
    EditText nameEditText;
    @Bind(R.id.activity_sign_up_input_mail_address_edit_text)
    EditText emailEditText;
    @Bind(R.id.activity_sign_up_confirm_duplication)
    TextView emailDuplicationTextView;
    @Bind(R.id.activity_sign_up_password_layout)
    RelativeLayout passwordLayout;
    @Bind(R.id.activity_sign_up_input_password_edit_text)
    EditText passwordEditText;
    @Bind(R.id.activity_sign_up_password_check_image_view)
    ImageView passwordCheckImageView;
    @Bind(R.id.activity_sign_up_password_confirm_layout)
    RelativeLayout passwordConfirmLayout;
    @Bind(R.id.activity_sign_up_password_confirm_edit_text)
    EditText passwordConfirmEditText;
    @Bind(R.id.activity_sign_up_password_confirm_check_image_view)
    ImageView passwordConfirmCheckImageView;
    @Bind(R.id.activity_sign_up_input_phone_edit_text)
    EditText phoneEditText;
    @Bind(R.id.activity_sign_up_request_authentication_number_text_view)
    TextView reqAuthNumTextView;
    @Bind(R.id.activity_sign_up_authentication_number_edit_text)
    EditText authNumEditText;
    @Bind(R.id.activity_sign_up_confirm_authentication_text_view)
    TextView confirmAuthTextView;
    @Bind(R.id.activity_sign_up_password_alert_layout)
    RelativeLayout passwordAlertLayout;

    public static final int SIGN_UP_ABNORMAL = 0;
    public static final int SIGN_UP_LOCAL = 1;
    public static final int SIGN_UP_FACEBOOK = 2;
    public static final String ACTIVITY_SIGN_UP_IMAGE = "ACTIVITY_SIGN_UP_IMAGE";
    public static final String ACTIVITY_SIGN_UP_NAME = "ACTIVITY_SIGN_UP_NAME";
    public static final String ACTIVITY_SIGN_UP_EMAIL = "ACTIVITY_SIGN_UP_EMAIL";
    public static final String ACTIVITY_SIGN_UP_PHONE = "ACTIVITY_SIGN_UP_PHONE";
    public static final String ACTIVITY_SIGN_UP_PASSWORD = "ACTIVITY_SIGN_UP_PASSWORD";
    public static final String ACTIVITY_SIGN_UP_FACEBOOK_USER_ID = "ACTIVITY_SIGN_UP_FACEBOOK_USER_ID";
    private static final String TAG = "SIGN_UP_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_sign_up_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ButterKnife.bind(this);

        intent = getIntent();
        from = intent.getStringExtra("FROM");
        requestCode = intent.getIntExtra("REQUEST_CODE", SIGN_UP_ABNORMAL);

        View cView = null;
        if (from.equals(RenterMainActivity.from)) {
            cView = getLayoutInflater().inflate(R.layout.renter_backable_tool_bar, null);
            cView.findViewById(R.id.renter_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            });
        } else if (from.equals(ListerMainActivity.from)) {
            cView = getLayoutInflater().inflate(R.layout.lister_backable_tool_bar, null);
            cView.findViewById(R.id.lister_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            });
        }
        getSupportActionBar().setCustomView(cView);

        emailEditText.addTextChangedListener(tw);
        phoneEditText.addTextChangedListener(tw);
        authNumEditText.addTextChangedListener(tw);

        if (requestCode == SIGN_UP_FACEBOOK) {
            passwordLayout.setVisibility(View.GONE);
            passwordConfirmLayout.setVisibility(View.GONE);
            signUpFacebook();
        } else if (requestCode == SIGN_UP_LOCAL) {
            passwordEditText.addTextChangedListener(tw);
            passwordConfirmEditText.addTextChangedListener(tw);
        }
    }

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.hashCode() == emailEditText.getText().hashCode()) {
                if (s.toString().matches(RegExUtil.REGEX_EMAIL)) {
                    if (Build.VERSION.SDK_INT < 23)
                        emailDuplicationTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
                    else
                        emailDuplicationTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
                    emailDuplicationTextView.setClickable(true);
                } else {
                    if (Build.VERSION.SDK_INT < 23)
                        emailDuplicationTextView.setTextColor(getResources().getColor(R.color.bikeeLine));
                    else
                        emailDuplicationTextView.setTextColor(getResources().getColor(R.color.bikeeLine, getTheme()));
                    emailDuplicationTextView.setClickable(false);
                }
            } else if (s.hashCode() == passwordEditText.getText().hashCode()) {
                if (s.toString().matches(RegExUtil.REGEX_PASSWORD)) {
                    password = s.toString();
                    passwordCheckImageView.setVisibility(View.VISIBLE);
                } else {
                    password = null;
                    passwordCheckImageView.setVisibility(View.INVISIBLE);
                }
            } else if (s.hashCode() == passwordConfirmEditText.getText().hashCode()) {
                passwordConfirmCheckImageView.setVisibility(View.VISIBLE);
                if (s.toString().matches(RegExUtil.REGEX_PASSWORD) && s.toString().equals(password)) {
                    if (Build.VERSION.SDK_INT < 23)
                        passwordConfirmCheckImageView.setImageDrawable(getResources().getDrawable(R.drawable.signin_icon_ok));
                    else
                        passwordConfirmCheckImageView.setImageDrawable(getResources().getDrawable(R.drawable.signin_icon_ok, getTheme()));
                    passwordAlertLayout.setVisibility(View.INVISIBLE);
                } else {
                    if (Build.VERSION.SDK_INT < 23)
                        passwordConfirmCheckImageView.setImageDrawable(getResources().getDrawable(R.drawable.signin_icon_reconfirm));
                    else
                        passwordConfirmCheckImageView.setImageDrawable(getResources().getDrawable(R.drawable.signin_icon_reconfirm, getTheme()));
                    passwordAlertLayout.setVisibility(View.VISIBLE);
                }
            } else if (s.hashCode() == phoneEditText.getText().hashCode()) {
                StringBuilder builder = new StringBuilder(s.toString().replace("-", ""));
                switch (builder.length()) {
                    case 10:
                    case 9:
                    case 8:
                    case 7:
                        builder.insert(6, "-");
                    case 6:
                    case 5:
                    case 4:
                        builder.insert(3, "-");
                    case 3:
                    case 2:
                    case 1:
                    case 0:
                        break;
                    default:
                        builder.insert(7, "-").insert(3, "-");
                        break;
                }
                phoneEditText.removeTextChangedListener(tw);
                phoneEditText.setText(builder.toString());
                phoneEditText.setSelection(builder.length());
                phoneEditText.addTextChangedListener(tw);

                if (s.toString().replace("-", "").matches(RegExUtil.REGEX_PHONE)) {
                    if (Build.VERSION.SDK_INT < 23)
                        reqAuthNumTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
                    else
                        reqAuthNumTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
                    reqAuthNumTextView.setClickable(true);
                } else {
                    if (Build.VERSION.SDK_INT < 23)
                        reqAuthNumTextView.setTextColor(getResources().getColor(R.color.bikeeLine));
                    else
                        reqAuthNumTextView.setTextColor(getResources().getColor(R.color.bikeeLine, getTheme()));
                    reqAuthNumTextView.setClickable(false);
                }
            } else if (s.hashCode() == authNumEditText.getText().hashCode()) {
                if (s.toString().length() > 0) {
                    if (Build.VERSION.SDK_INT < 23)
                        confirmAuthTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
                    else
                        confirmAuthTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
                    confirmAuthTextView.setClickable(true);
                } else {
                    if (Build.VERSION.SDK_INT < 23)
                        confirmAuthTextView.setTextColor(getResources().getColor(R.color.bikeeLine));
                    else
                        confirmAuthTextView.setTextColor(getResources().getColor(R.color.bikeeLine, getTheme()));
                    confirmAuthTextView.setClickable(false);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void signUpFacebook() {
        FacebookNetworkManager.getInstance().signupFacebook(
                SignUpActivity.this,
                "message",
                new FacebookNetworkManager.OnResultListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        token = AccessToken.getCurrentAccessToken();
                        PropertyManager.getInstance().setFacebookId(token.getUserId());

                        Profile profile = Profile.getCurrentProfile();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "profile.getLinkUri : " + profile.getLinkUri()
                                            + "\n.getName : " + profile.getName()
                                            + "\n.getProfilePictureUri : " + profile.getProfilePictureUri(100, 100)
                            );

                        nameEditText.setText("" + profile.getName());
                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            if (!object.isNull("email"))
                                                emailEditText.setText("" + object.getString("email"));
                                            else {
                                                if (BuildConfig.DEBUG)
                                                    Log.d(TAG, "email address isn't exist in Facebook...");
                                            }
                                        } catch (Exception e) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "invoked Exception at object.getString(\"email\")", e);
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onFail(int code) {

                    }
                });
    }

    @OnClick(R.id.activity_sign_up_confirm_duplication)
    void clickEmailDuplicationCheck(View view) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "emailDuplicationTextView");
        // TODO : Email 중복 확인 작업이 필요함
    }

    @OnClick(R.id.activity_sign_up_request_authentication_number_text_view)
    void reqAuth() {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "reqAuthNumTextView");
//        // 인증번호요청하기
//        NetworkManager.getInstance().requestAuthenticationNumber(phoneEditText.getText().toString(), new Callback<ReceiveObject>() {
//            @Override
//            public void success(ReceiveObject receiveObject, Response response) {
//                if (BuildConfig.DEBUG)
//                Log.d(TAG, "onResponse Code : " + receiveObject.getCode()
//                                + ", Success : " + receiveObject.isSuccess()
//                                + ", Msg : " + receiveObject.getMsg()
//                                + ", Error : "
//                );
//                if (receiveObject.getCode() == 200) {
//                    for (Result result : receiveObject.getResult()) {
//                        if (BuildConfig.DEBUG)
//                        Log.d(TAG, "onResponse Id : " + result.getId());
//                        authId = result.getId();
//                        authNum = "" + result.getAuth_number();
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d(TAG, "onFailure Error : " + error.toString());
//            }
//        });
    }

    @OnClick(R.id.activity_sign_up_confirm_authentication_text_view)
    void confirmAuth() {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "confirmAuthTextView");
//        // 인증번호확인하기
//        String authid = "564d9d0e32a130ea2a0731dc";
//        String auth_number = "447671";
//        NetworkManager.getInstance().confirmAuthenticationNumber(authId, authNum, new Callback<ReceiveObject>() {
//            @Override
//            public void success(ReceiveObject receiveObject, Response response) {
//                Log.d(TAG, "onResponse Code : " + receiveObject.getCode()
//                                + ", Success : " + receiveObject.isSuccess()
//                                + ", Msg : " + receiveObject.getMsg()
//                                + ", Error : "
//                );
//                for (Result result : receiveObject.getResult()) {
//                    Log.d(TAG, "onResponse Accepted : " + result.isAccepted());
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d(TAG, "onFailure Error : " + error.toString());
//            }
//        });
    }

    @OnClick(R.id.activity_sign_up_sign_up_button)
    void signUp() {
        if (requestCode == SIGN_UP_FACEBOOK) {
            Facebook facebook = new Facebook();
            facebook.setAccess_token(token.getToken());
            facebook.setEmail(emailEditText.getText().toString());
            facebook.setPhone(phoneEditText.getText().toString());
            facebook.setUsername(nameEditText.getText().toString());
            if (BuildConfig.DEBUG)
                Log.d(TAG, "token : " + token.getToken()
                        + "\nemail : " + emailEditText.getText().toString()
                        + "\nphone : " + phoneEditText.getText().toString()
                        + "\nname : " + nameEditText.getText().toString());
            NetworkManager.getInstance().signUpFacebook(
                    facebook,
                    new Callback<ReceiveObject>() {
                        @Override
                        public void success(ReceiveObject receiveObject, Response response) {
                            if (receiveObject != null) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "receiveObject isn't null");

                                intent.putExtra(ACTIVITY_SIGN_UP_FACEBOOK_USER_ID, token.getUserId());
                                intent.putExtra(ACTIVITY_SIGN_UP_NAME, nameEditText.getText().toString());
                                intent.putExtra(ACTIVITY_SIGN_UP_EMAIL, emailEditText.getText().toString());
                                intent.putExtra(ACTIVITY_SIGN_UP_PHONE, phoneEditText.getText().toString());
                                setResult(RESULT_OK, intent);

                                finish();
                            } else if (BuildConfig.DEBUG)
                                Log.d(TAG, "receiveObject is null");
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "signUpFacebook failure : ", error);
                        }
                    });
        } else if (requestCode == SIGN_UP_LOCAL) {
            User user = new User();
            user.setName(nameEditText.getText().toString());
            user.setEmail(emailEditText.getText().toString());
            user.setPhone(phoneEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            NetworkManager.getInstance().insertUser(
                    user,
                    new Callback<ReceiveObject>() {
                        @Override
                        public void success(ReceiveObject receiveObject, Response response) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "회원가입 onResponse Code : " + receiveObject.getCode()
                                                + "\nSuccess : " + receiveObject.isSuccess()
                                                + "\nMsg : " + receiveObject.getMsg()
                                );
                            if (receiveObject.getCode() == 200) {
                                Toast.makeText(SignUpActivity.this, "회원가입됐습니다.", Toast.LENGTH_SHORT).show();

                                intent.putExtra(ACTIVITY_SIGN_UP_NAME, nameEditText.getText().toString());
                                intent.putExtra(ACTIVITY_SIGN_UP_EMAIL, emailEditText.getText().toString());
                                intent.putExtra(ACTIVITY_SIGN_UP_PASSWORD, passwordEditText.getText().toString());
                                intent.putExtra(ACTIVITY_SIGN_UP_PHONE, phoneEditText.getText().toString());
                                setResult(RESULT_OK, intent);

                                finish();
                            } else
                                Toast.makeText(SignUpActivity.this, receiveObject.getStack().get(0), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onFailure Error : " + error.toString());
                        }
                    });
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
