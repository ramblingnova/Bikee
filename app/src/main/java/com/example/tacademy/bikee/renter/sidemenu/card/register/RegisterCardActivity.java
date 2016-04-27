package com.example.tacademy.bikee.renter.sidemenu.card.register;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.tacademy.bikee.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterCardActivity extends AppCompatActivity {
    @Bind(R.id.activity_register_card_number_block1_edit_text)
    EditText cardNumberBlock1EditText;
    @Bind(R.id.activity_register_card_number_block2_edit_text)
    EditText cardNumberBlock2EditText;
    @Bind(R.id.activity_register_card_number_block3_edit_text)
    EditText cardNumberBlock3EditText;
    @Bind(R.id.activity_register_card_number_block4_edit_text)
    EditText cardNumberBlock4EditText;
    @Bind(R.id.activity_register_expiration_date_month_edit_text)
    EditText expirationDateMonthEditText;
    @Bind(R.id.activity_register_expiration_date_year_edit_text)
    EditText expirationDateYearEditText;
    @Bind(R.id.activity_register_birth_date_edit_text)
    EditText birthDateEditText;
    @Bind(R.id.activity_register_password_edit_text)
    EditText passwordEditText;
    @Bind(R.id.activity_register_card_nickname_edit_text)
    EditText nicknameEditText;

    private Intent intent;

    private static final String TAG = "REGISTER_CARD_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_register_card_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_backable_tool_bar);

        ButterKnife.bind(this);

        intent = getIntent();
    }

    @OnClick(R.id.renter_backable_tool_bar_back_button_layout)
    void back(View view) {
        super.onBackPressed();
    }

    @OnClick(R.id.activity_register_register_card_button)
    void onClick(View view) {
        cardNumberBlock1EditText.getText();
        cardNumberBlock2EditText.getText();
        cardNumberBlock3EditText.getText();
        cardNumberBlock4EditText.getText();
        expirationDateMonthEditText.getText();
        expirationDateYearEditText.getText();
        birthDateEditText.getText();
        passwordEditText.getText();
        nicknameEditText.getText();

        intent.putExtra("CARD_NUMBER", cardNumberBlock1EditText.getText().toString()
                + cardNumberBlock2EditText.getText().toString()
                + cardNumberBlock3EditText.getText().toString()
                + cardNumberBlock4EditText.getText().toString());
        intent.putExtra("EXPIRATION_DATE", expirationDateYearEditText.getText().toString()
                + expirationDateMonthEditText.getText().toString());
        intent.putExtra("BIRTH_DATE", birthDateEditText.getText().toString());
        intent.putExtra("PASSWORD_2_DIGIT", passwordEditText.getText().toString());
        intent.putExtra("NICKNAME", nicknameEditText.getText().toString());

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
