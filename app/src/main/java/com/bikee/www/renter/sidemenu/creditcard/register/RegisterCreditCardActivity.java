package com.bikee.www.renter.sidemenu.creditcard.register;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bikee.www.renter.sidemenu.creditcard.register.popup.GetDialogResultListener;
import com.bikee.www.BuildConfig;
import com.bikee.www.R;
import com.bikee.www.renter.sidemenu.creditcard.register.popup.SelectDateDialogFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class RegisterCreditCardActivity extends AppCompatActivity implements GetDialogResultListener {
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_icon_back_image_view)
    ImageView toolbarLeftIconBackImageView;
    @Bind(R.id.toolbar_center_text_view)
    TextView toolbarCenterTextView;
    @Bind(R.id.activity_register_credit_card_number_edit_layout)
    RelativeLayout cardNumberEditLayout;
    @Bind(R.id.activity_register_credit_card_number_block1_edit_text)
    EditText cardNumberBlock1EditText;
    @Bind(R.id.activity_register_credit_card_number_block2_edit_text)
    EditText cardNumberBlock2EditText;
    @Bind(R.id.activity_register_credit_card_number_block3_edit_text)
    EditText cardNumberBlock3EditText;
    @Bind(R.id.activity_register_credit_card_number_block4_edit_text)
    EditText cardNumberBlock4EditText;
    @Bind(R.id.activity_register_credit_card_expiration_date_month_layout)
    RelativeLayout expirationDateMonthLayout;
    @Bind(R.id.activity_register_credit_card_expiration_date_month_edit_text)
    EditText expirationDateMonthEditText;
    @Bind(R.id.activity_register_credit_card_expiration_date_month_extension_image_view)
    ImageView expirationDateMonthExtensionImageView;
    @Bind(R.id.activity_register_credit_card_expiration_date_year_layout)
    RelativeLayout expirationDateYearLayout;
    @Bind(R.id.activity_register_credit_card_expiration_date_year_edit_text)
    EditText expirationDateYearEditText;
    @Bind(R.id.activity_register_credit_card_expiration_date_year_extension_image_view)
    ImageView expirationDateYearExtensionImageView;
    @Bind(R.id.activity_register_credit_card_birth_date_day_layout)
    RelativeLayout birthDateDayLayout;
    @Bind(R.id.activity_register_credit_card_birth_date_day_edit_text)
    EditText birthDateDayEditText;
    @Bind(R.id.activity_register_credit_card_birth_date_day_extension_image_view)
    ImageView birthDateDayExtensionImageView;
    @Bind(R.id.activity_register_credit_card_birth_date_month_layout)
    RelativeLayout birthDateMonthLayout;
    @Bind(R.id.activity_register_credit_card_birth_date_month_edit_text)
    EditText birthDateMonthEditText;
    @Bind(R.id.activity_register_credit_card_birth_date_month_extension_image_view)
    ImageView birthDateMonthExtensionImageView;
    @Bind(R.id.activity_register_credit_card_birth_date_year_layout)
    RelativeLayout birthDateYearLayout;
    @Bind(R.id.activity_register_credit_card_birth_date_year_edit_text)
    EditText birthDateYearEditText;
    @Bind(R.id.activity_register_credit_card_birth_date_year_extension_image_view)
    ImageView birthDateYearExtensionImageView;
    @Bind(R.id.activity_register_credit_card_password_first_edit_text)
    EditText passwordFirstEditText;
    @Bind(R.id.activity_register_credit_card_password_second_edit_text)
    EditText passwordSecondEditText;
    @Bind(R.id.activity_register_credit_card_card_nickname_edit_text)
    EditText nicknameEditText;
    @Bind(R.id.activity_register_credit_card_register_card_button)
    Button registerCardButton;

    /*@Bind(R.id.activity_register_card_temp_spinner)
    Spinner tempSpinner;
    @Bind(R.id.activity_register_card_temp_spinner2)
    Spinner tempSpinner2;
    @Bind(R.id.activity_register_card_temp_spinner3)
    Spinner tempSpinner3;
    @Bind(R.id.activity_register_card_temp_spinner4)
    Spinner tempSpinner4;
    @Bind(R.id.activity_register_card_temp_spinner5)
    Spinner tempSpinner5;*/

    private Intent intent;
    private InputMethodManager imm;
    private SelectDateDialogFragment selectYearDialogFragment;
    private SelectDateDialogFragment selectMonthDialogFragment;
    private SelectDateDialogFragment selectDayDialogFragment;
    private String birthDateYear;
    private String birthDateMonth;
    private String birthDateDay;
    private boolean conditions[];

    private static final int MAX_CONDITION = 9;
    private static final String TAG = "REGISTER_CARD_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_credit_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_register_credit_card_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        ButterKnife.bind(this);

        /* 툴바 배경 */
        if (Build.VERSION.SDK_INT < 23)
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeWhite));
        else
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeWhite, getTheme()));

        /* 툴바 왼쪽 */
        toolbarLeftIconBackImageView.setVisibility(View.VISIBLE);
        toolbarLeftIconBackImageView.setImageResource(R.drawable.icon_before);

        /* 툴바 가운데 */
        toolbarCenterTextView.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT < 23)
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeBlack));
        else
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeBlack, getTheme()));
        toolbarCenterTextView.setText("결제카드 등록");

        intent = getIntent();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        init();
    }

    @Override
    public void getDialogResult(int mode, String result) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "result : " + result);
        switch (mode) {
            case SelectDateDialogFragment.EXPIRATION_DATE_MONTH:
                if (result.equals("직접입력")) {
                    // TODO : 직접입력했을 경우에 soft-keyboard를 자동적으로 띄워야 함
                    openEditTextBlock(expirationDateMonthEditText);
                    expirationDateMonthExtensionImageView.setVisibility(View.GONE);
                } else {
                    expirationDateMonthEditText.setText(result);
                }
                break;
            case SelectDateDialogFragment.EXPIRATION_DATE_YEAR:
                if (result.equals("직접입력")) {
                    openEditTextBlock(expirationDateYearEditText);
                    expirationDateYearExtensionImageView.setVisibility(View.GONE);
                } else {
                    expirationDateYearEditText.setText(result);
                }
                break;
            case SelectDateDialogFragment.BIRTH_DATE_YEAR:
                if (result.equals("직접입력")) {
                    openEditTextBlock(birthDateYearEditText);
                    birthDateYearExtensionImageView.setVisibility(View.GONE);
                } else {
                    birthDateYear = result;
                    birthDateYearEditText.setText(result);
                }
                break;
            case SelectDateDialogFragment.BIRTH_DATE_MONTH:
                if (result.equals("직접입력")) {
                    openEditTextBlock(birthDateMonthEditText);
                    birthDateMonthExtensionImageView.setVisibility(View.GONE);
                } else {
                    birthDateMonth = result;
                    birthDateMonthEditText.setText(result);
                }
                break;
            case SelectDateDialogFragment.BIRTH_DATE_DAY:
                if (result.equals("직접입력")) {
                    openEditTextBlock(birthDateDayEditText);
                    birthDateDayExtensionImageView.setVisibility(View.GONE);
                } else {
                    birthDateDay = result;
                    birthDateDayEditText.setText(result);
                }
                break;
        }
    }

    @OnClick({R.id.toolbar_left_icon_layout,
            R.id.activity_register_credit_card_expiration_date_month_layout,
            R.id.activity_register_credit_card_expiration_date_month_edit_text,
            R.id.activity_register_credit_card_expiration_date_month_extension_image_view,
            R.id.activity_register_credit_card_expiration_date_year_layout,
            R.id.activity_register_credit_card_expiration_date_year_edit_text,
            R.id.activity_register_credit_card_expiration_date_year_extension_image_view,
            R.id.activity_register_credit_card_birth_date_day_layout,
            R.id.activity_register_credit_card_birth_date_day_edit_text,
            R.id.activity_register_credit_card_birth_date_day_extension_image_view,
            R.id.activity_register_credit_card_birth_date_month_layout,
            R.id.activity_register_credit_card_birth_date_month_edit_text,
            R.id.activity_register_credit_card_birth_date_month_extension_image_view,
            R.id.activity_register_credit_card_birth_date_year_layout,
            R.id.activity_register_credit_card_birth_date_year_edit_text,
            R.id.activity_register_credit_card_birth_date_year_extension_image_view,
            R.id.activity_register_credit_card_register_card_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_icon_layout:
                super.onBackPressed();
                break;
            case R.id.activity_register_credit_card_expiration_date_month_layout:
            case R.id.activity_register_credit_card_expiration_date_month_edit_text:
            case R.id.activity_register_credit_card_expiration_date_month_extension_image_view:
                if (!expirationDateMonthEditText.isFocusable()) {
                    SelectDateDialogFragment selectDateDialogFragment
                            = SelectDateDialogFragment.newInstance(SelectDateDialogFragment.EXPIRATION_DATE_MONTH);
                    selectDateDialogFragment.setGetDialogResultListener(this);
                    selectDateDialogFragment.show(getSupportFragmentManager(), TAG);
                }
                break;
            case R.id.activity_register_credit_card_expiration_date_year_layout:
            case R.id.activity_register_credit_card_expiration_date_year_edit_text:
            case R.id.activity_register_credit_card_expiration_date_year_extension_image_view:
                if (!expirationDateYearEditText.isFocusable()) {
                    SelectDateDialogFragment selectDateDialogFragment
                            = SelectDateDialogFragment.newInstance(SelectDateDialogFragment.EXPIRATION_DATE_YEAR);
                    selectDateDialogFragment.setGetDialogResultListener(this);
                    selectDateDialogFragment.show(getSupportFragmentManager(), TAG);
                }
                break;
            case R.id.activity_register_credit_card_birth_date_day_layout:
            case R.id.activity_register_credit_card_birth_date_day_edit_text:
            case R.id.activity_register_credit_card_birth_date_day_extension_image_view:
                if (!birthDateDayEditText.isFocusable()
                        && (birthDateYear != null)
                        && (birthDateMonth != null)) {
                    selectDayDialogFragment = SelectDateDialogFragment.newInstance(
                            SelectDateDialogFragment.BIRTH_DATE_DAY,
                            birthDateYear,
                            birthDateMonth,
                            birthDateDay
                    );
                    selectDayDialogFragment.setGetDialogResultListener(this);
                    selectDayDialogFragment.show(getSupportFragmentManager(), TAG);
                } else {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "else");
                    // TODO : 연/월을 중에 하나라도 입력하지 않고 팝업을 열 때, 연/월 중에 비어있는 곳으로 포커스 이동
                }
                break;
            case R.id.activity_register_credit_card_birth_date_month_layout:
            case R.id.activity_register_credit_card_birth_date_month_edit_text:
            case R.id.activity_register_credit_card_birth_date_month_extension_image_view:
                if (!birthDateMonthEditText.isFocusable()) {
                    if (birthDateYearEditText != null) {
                        selectMonthDialogFragment = SelectDateDialogFragment.newInstance(
                                SelectDateDialogFragment.BIRTH_DATE_MONTH,
                                birthDateYear,
                                birthDateMonth,
                                birthDateDay
                        );
                        selectMonthDialogFragment.setGetDialogResultListener(this);
                        selectMonthDialogFragment.show(getSupportFragmentManager(), TAG);
                    } else {

                    }
                }
                break;
            case R.id.activity_register_credit_card_birth_date_year_layout:
            case R.id.activity_register_credit_card_birth_date_year_edit_text:
            case R.id.activity_register_credit_card_birth_date_year_extension_image_view:
                if (!birthDateYearEditText.isFocusable()) {
                    selectYearDialogFragment = SelectDateDialogFragment.newInstance(
                            SelectDateDialogFragment.BIRTH_DATE_YEAR,
                            birthDateYear,
                            birthDateMonth,
                            birthDateDay
                    );

                    selectYearDialogFragment.setGetDialogResultListener(this);
                    selectYearDialogFragment.show(getSupportFragmentManager(), TAG);
                }
                break;
            case R.id.activity_register_credit_card_register_card_button:
                intent.putExtra("CARD_NUMBER", cardNumberBlock1EditText.getText().toString()
                                + cardNumberBlock2EditText.getText().toString()
                                + cardNumberBlock3EditText.getText().toString()
                                + cardNumberBlock4EditText.getText().toString()
                );
                intent.putExtra("EXPIRATION_DATE", expirationDateYearEditText.getText().toString()
                                + expirationDateMonthEditText.getText().toString()
                );
                intent.putExtra("BIRTH_DATE", birthDateYearEditText.getText().toString().substring(2, 4)
                                + birthDateMonthEditText.getText().toString()
                                + birthDateDayEditText.getText().toString()
                );
                intent.putExtra("PASSWORD_2_DIGIT", passwordFirstEditText.getText().toString()
                                + passwordSecondEditText.getText().toString()
                );
                intent.putExtra("NICKNAME", nicknameEditText.getText().toString());

                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @OnEditorAction({R.id.activity_register_credit_card_number_block4_edit_text,
            R.id.activity_register_credit_card_expiration_date_month_edit_text,
            R.id.activity_register_credit_card_expiration_date_year_edit_text,
            R.id.activity_register_credit_card_birth_date_year_edit_text,
            R.id.activity_register_credit_card_birth_date_month_edit_text,
            R.id.activity_register_credit_card_birth_date_day_edit_text})
    boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        switch (view.getId()) {
            case R.id.activity_register_credit_card_number_block4_edit_text:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    openEditTextBlock(expirationDateMonthEditText);
                    expirationDateMonthExtensionImageView.setVisibility(View.GONE);
                    return false;
                }
                break;
            case R.id.activity_register_credit_card_expiration_date_month_edit_text:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    openEditTextBlock(expirationDateYearEditText);
                    expirationDateYearExtensionImageView.setVisibility(View.GONE);
                    return false;
                }
                break;
            case R.id.activity_register_credit_card_expiration_date_year_edit_text:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    openEditTextBlock(birthDateYearEditText);
                    birthDateYearExtensionImageView.setVisibility(View.GONE);
                    return false;
                }
                break;
            case R.id.activity_register_credit_card_birth_date_year_edit_text:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    openEditTextBlock(birthDateMonthEditText);
                    birthDateMonthExtensionImageView.setVisibility(View.GONE);
                    return false;
                }
                break;
            case R.id.activity_register_credit_card_birth_date_month_edit_text:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    openEditTextBlock(birthDateDayEditText);
                    birthDateDayExtensionImageView.setVisibility(View.GONE);
                    return false;
                }
                break;
            case R.id.activity_register_credit_card_birth_date_day_edit_text:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    openEditTextBlock(passwordFirstEditText);
                    return false;
                }
                break;
        }
        return true;
    }

    public void init() {
        conditions = new boolean[MAX_CONDITION];

        for (int i = 0; i < MAX_CONDITION; i++)
            conditions[i] = false;

        cardNumberBlock1EditText.addTextChangedListener(
                new CustomedTextWatcher(
                        cardNumberBlock1EditText.getId())

        );
        cardNumberBlock2EditText.addTextChangedListener(
                new CustomedTextWatcher(
                        cardNumberBlock2EditText.getId()
                )
        );
        cardNumberBlock3EditText.addTextChangedListener(
                new CustomedTextWatcher(
                        cardNumberBlock3EditText.getId()
                )
        );
        cardNumberBlock4EditText.addTextChangedListener(
                new CustomedTextWatcher(
                        cardNumberBlock4EditText.getId()
                )
        );
        expirationDateMonthEditText.addTextChangedListener(
                new CustomedTextWatcher(
                        expirationDateMonthEditText.getId()
                )
        );
        expirationDateYearEditText.addTextChangedListener(
                new CustomedTextWatcher(
                        expirationDateYearEditText.getId()
                )
        );
        birthDateYearEditText.addTextChangedListener(
                new CustomedTextWatcher(
                        birthDateYearEditText.getId()
                )
        );
        birthDateMonthEditText.addTextChangedListener(
                new CustomedTextWatcher(
                        birthDateMonthEditText.getId()
                )
        );
        birthDateDayEditText.addTextChangedListener(
                new CustomedTextWatcher(
                        birthDateDayEditText.getId()
                )
        );
        passwordFirstEditText.addTextChangedListener(
                new CustomedTextWatcher(
                        passwordFirstEditText.getId()
                )
        );
        passwordSecondEditText.addTextChangedListener(
                new CustomedTextWatcher(
                        passwordSecondEditText.getId()
                )
        );
        nicknameEditText.addTextChangedListener(
                new CustomedTextWatcher(
                        nicknameEditText.getId()
                )
        );

        birthDateYear = null;
        birthDateMonth = null;
        birthDateDay = null;

//        tempSpinner.setAdapter(new SelectDateSpinnerAdapter());
//        tempSpinner2.setAdapter(new SelectDateSpinnerAdapter());
//        tempSpinner3.setAdapter(new SelectDateSpinnerAdapter());
//        tempSpinner4.setAdapter(new SelectDateSpinnerAdapter());
//        tempSpinner5.setAdapter(new SelectDateSpinnerAdapter());
    }

    public class CustomedTextWatcher implements TextWatcher {
        int id;

        public CustomedTextWatcher(int id) {
            this.id = id;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (id) {
                case R.id.activity_register_credit_card_number_block1_edit_text:
                case R.id.activity_register_credit_card_number_block2_edit_text:
                case R.id.activity_register_credit_card_number_block3_edit_text:
                case R.id.activity_register_credit_card_number_block4_edit_text:
                    if (16 == cardNumberBlock1EditText.length()
                            + cardNumberBlock2EditText.length()
                            + cardNumberBlock3EditText.length()
                            + cardNumberBlock4EditText.length()) {
                        cardNumberEditLayout.setBackgroundResource(R.drawable.inputbox_after);
                        conditions[0] = true;
                    } else {
                        cardNumberEditLayout.setBackgroundResource(R.drawable.inputbox_before);
                        conditions[0] = false;
                    }
                    break;
                case R.id.activity_register_credit_card_expiration_date_month_edit_text:
                    if (expirationDateMonthEditText.length() == 2) {
                        expirationDateMonthLayout.setBackgroundResource(R.drawable.inputbox_after);
                        conditions[1] = true;
                    } else {
                        expirationDateMonthLayout.setBackgroundResource(R.drawable.inputbox_before);
                        conditions[1] = false;
                    }
                    break;
                case R.id.activity_register_credit_card_expiration_date_year_edit_text:
                    if (expirationDateYearEditText.length() == 4) {
                        expirationDateYearLayout.setBackgroundResource(R.drawable.inputbox_after);
                        conditions[2] = true;
                    } else {
                        expirationDateYearLayout.setBackgroundResource(R.drawable.inputbox_before);
                        conditions[2] = false;
                    }
                    break;
                case R.id.activity_register_credit_card_birth_date_day_edit_text:
                    if (birthDateDayEditText.length() == 2) {
                        birthDateDayLayout.setBackgroundResource(R.drawable.inputbox_after);
                        birthDateDay = birthDateDayEditText.getText().toString();
                        conditions[3] = true;
                    } else {
                        birthDateDayLayout.setBackgroundResource(R.drawable.inputbox_before);
                        birthDateDay = null;
                        conditions[3] = false;
                    }
                    break;
                case R.id.activity_register_credit_card_birth_date_month_edit_text:
                    if (birthDateMonthEditText.length() == 2) {
                        birthDateMonthLayout.setBackgroundResource(R.drawable.inputbox_after);
                        birthDateMonth = birthDateMonthEditText.getText().toString();
                        conditions[4] = true;
                    } else {
                        birthDateMonthLayout.setBackgroundResource(R.drawable.inputbox_before);
                        birthDateMonth = null;
                        conditions[4] = false;
                    }
                    break;
                case R.id.activity_register_credit_card_birth_date_year_edit_text:
                    if (birthDateYearEditText.length() == 4) {
                        birthDateYearLayout.setBackgroundResource(R.drawable.inputbox_after);
                        birthDateYear = birthDateYearEditText.getText().toString();
                        conditions[5] = true;
                    } else {
                        birthDateYearLayout.setBackgroundResource(R.drawable.inputbox_before);
                        birthDateYear = null;
                        conditions[5] = false;
                    }
                    break;
                case R.id.activity_register_credit_card_password_first_edit_text:
                    if (passwordFirstEditText.length() == 1) {
                        passwordFirstEditText.setBackgroundResource(R.drawable.inputbox_after);
                        conditions[6] = true;
                    } else {
                        passwordFirstEditText.setBackgroundResource(R.drawable.inputbox_before);
                        conditions[6] = false;
                    }
                    break;
                case R.id.activity_register_credit_card_password_second_edit_text:
                    if (passwordSecondEditText.length() == 1) {
                        passwordSecondEditText.setBackgroundResource(R.drawable.inputbox_after);
                        conditions[7] = true;
                    } else {
                        passwordSecondEditText.setBackgroundResource(R.drawable.inputbox_before);
                        conditions[7] = false;
                    }
                    break;
                case R.id.activity_register_credit_card_card_nickname_edit_text:
                    if (nicknameEditText.length() > 0) {
                        nicknameEditText.setBackgroundResource(R.drawable.inputbox_after);
                        conditions[8] = true;
                    } else {
                        nicknameEditText.setBackgroundResource(R.drawable.inputbox_before);
                        conditions[8] = false;
                    }
                    break;
            }
            enableButton();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public void enableButton() {
        for (int i = MAX_CONDITION - 1; i >= 0; i++)
            if (!conditions[i]) {
                registerCardButton.setEnabled(false);
                registerCardButton.setBackgroundResource(R.drawable.detailpage_button1);
                return;
            }
        registerCardButton.setEnabled(true);
        registerCardButton.setBackgroundResource(R.drawable.detailpage_button2);
    }

    public void openEditTextBlock(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setSelection(editText.length());
        editText.requestFocus();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
