package com.example.tacademy.bikee.renter.sidemenu.cardmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.tacademy.bikee.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardManagementActivity extends AppCompatActivity {
    @Bind(R.id.activity_card_management_list_view) ListView lv;
    private CardAdapter adapter;
    final private static int REGISTER_CARD_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_management);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_card_management_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);
        ButterKnife.bind(this);

        adapter = new CardAdapter();
        lv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            adapter.add("카드종류" + i, "카드번호" + i, "유효기간" + i, "생년월일" + i, "비밀번호" + i);
        }
    }

    @OnItemClick(R.id.activity_card_management_list_view) void get(int position) {

    }

    @OnClick(R.id.activity_card_management_add_button) void add() {
        Intent intent = new Intent(CardManagementActivity.this, RegisterCardActivity.class);
        startActivityForResult(intent, REGISTER_CARD_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REGISTER_CARD_ACTIVITY) {
            adapter.add(data.getStringExtra(RegisterCardActivity.ACTIVITY_RGISTER_CARD_CARD_SORD_EDIT_TEXT),
                    data.getStringExtra(RegisterCardActivity.ACTIVITY_RGISTER_CARD_CARD_NUMBER_EDIT_TEXT),
                    data.getStringExtra(RegisterCardActivity.ACTIVITY_RGISTER_CARD_VALID_DATE_EDIT_TEXT),
                    data.getStringExtra(RegisterCardActivity.ACTIVITY_RGISTER_CARD_BIRTH_DATE_EDIT_TEXT),
                    data.getStringExtra(RegisterCardActivity.ACTIVITY_RGISTER_CARD_PASSWORD_EDIT_TEXT));
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
