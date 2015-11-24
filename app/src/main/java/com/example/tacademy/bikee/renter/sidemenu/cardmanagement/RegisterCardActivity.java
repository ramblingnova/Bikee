package com.example.tacademy.bikee.renter.sidemenu.cardmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tacademy.bikee.R;
//import com.tsengvn.typekit.TypekitContextWrapper;

public class RegisterCardActivity extends AppCompatActivity {
    private EditText cardSortEditText;
    private EditText cardNumberEditText;
    private EditText cardValidDate_EditText;
    private EditText cardBirthDateEditText;
    private EditText cardPasswordEditText;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        Button btn = (Button) findViewById(R.id.activity_register_card_register_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                cardSortEditText = (EditText) findViewById(R.id.activity_register_card_card_sort_edit_text);
                cardNumberEditText = (EditText) findViewById(R.id.activity_register_card_card_number_edit_text);
                cardValidDate_EditText = (EditText) findViewById(R.id.activity_register_card_valid_date_edit_text);
                cardBirthDateEditText = (EditText) findViewById(R.id.activity_register_card_birth_date_edit_text);
                cardPasswordEditText = (EditText) findViewById(R.id.activity_register_card_password_edit_text);
                intent.putExtra(ACTIVITY_RGISTER_CARD_CARD_SORD_EDIT_TEXT, cardSortEditText.getText().toString());
                intent.putExtra(ACTIVITY_RGISTER_CARD_CARD_NUMBER_EDIT_TEXT, cardNumberEditText.getText().toString());
                intent.putExtra(ACTIVITY_RGISTER_CARD_VALID_DATE_EDIT_TEXT, cardValidDate_EditText.getText().toString());
                intent.putExtra(ACTIVITY_RGISTER_CARD_BIRTH_DATE_EDIT_TEXT, cardBirthDateEditText.getText().toString());
                intent.putExtra(ACTIVITY_RGISTER_CARD_PASSWORD_EDIT_TEXT, cardPasswordEditText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
//    }
}
