package com.example.tacademy.bikee.renter.sidemenu.cardmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.tacademy.bikee.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterCardActivity extends AppCompatActivity {
    @Bind(R.id.activity_register_card_card_sort_edit_text)
    EditText cardSortEditText;
    @Bind(R.id.activity_register_card_card_number_edit_text)
    EditText cardNumberEditText;
    @Bind(R.id.activity_register_card_valid_date_edit_text)
    EditText cardValidDate_EditText;
    @Bind(R.id.activity_register_card_birth_date_edit_text)
    EditText cardBirthDateEditText;
    @Bind(R.id.activity_register_card_password_edit_text)
    EditText cardPasswordEditText;
    private Intent intent;
    final public static String ACTIVITY_RGISTER_CARD_CARD_SORD_EDIT_TEXT = "activity_register_card_card_sort_edit_text";
    final public static String ACTIVITY_RGISTER_CARD_CARD_NUMBER_EDIT_TEXT = "activity_register_card_card_number_edit_text";
    final public static String ACTIVITY_RGISTER_CARD_VALID_DATE_EDIT_TEXT = "activity_register_card_valid_date_edit_text";
    final public static String ACTIVITY_RGISTER_CARD_BIRTH_DATE_EDIT_TEXT = "activity_register_card_birth_date_edit_text";
    final public static String ACTIVITY_RGISTER_CARD_PASSWORD_EDIT_TEXT = "activity_register_card_password_edit_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_register_card_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.renter_backable_tool_bar, null);
        cView.findViewById(R.id.renter_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.renter_backable_tool_bar_back_button_layout:
                        finish();
                        break;
                }
            }
        });
        getSupportActionBar().setCustomView(cView);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_register_card_register_button)
    void registerCard() {
        intent = getIntent();
        intent.putExtra(ACTIVITY_RGISTER_CARD_CARD_SORD_EDIT_TEXT, cardSortEditText.getText().toString());
        intent.putExtra(ACTIVITY_RGISTER_CARD_CARD_NUMBER_EDIT_TEXT, cardNumberEditText.getText().toString());
        intent.putExtra(ACTIVITY_RGISTER_CARD_VALID_DATE_EDIT_TEXT, cardValidDate_EditText.getText().toString());
        intent.putExtra(ACTIVITY_RGISTER_CARD_BIRTH_DATE_EDIT_TEXT, cardBirthDateEditText.getText().toString());
        intent.putExtra(ACTIVITY_RGISTER_CARD_PASSWORD_EDIT_TEXT, cardPasswordEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
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
