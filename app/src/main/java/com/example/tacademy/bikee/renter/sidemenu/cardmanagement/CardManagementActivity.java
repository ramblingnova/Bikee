package com.example.tacademy.bikee.renter.sidemenu.cardmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.tacademy.bikee.R;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardManagementActivity extends AppCompatActivity {

    Button btn;
    ListView lv;
    CardAdapter adapter;
    final private static int REGISTER_CARD_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_management);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_card_management_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView) findViewById(R.id.activity_card_management_list_view);
        adapter = new CardAdapter();
        lv.setAdapter(adapter);
        initData();

        btn = (Button) findViewById(R.id.activity_card_management_add_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardManagementActivity.this, RegisterCardActivity.class);
                startActivityForResult(intent, REGISTER_CARD_ACTIVITY);
            }
        });

        adapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardItem item) {
//                Intent intent = new Intent(CardManagementActivity.this,.class);
//                intent.putExtra("item",item);
//                startActivity(intent);
            }
        });

    }

    private void initData() {
        // TODO
        for (int i = 0; i < 10; i++) {
            adapter.add("카드종류" + i, "카드번호" + i, "유효기간" + i, "생년월일" + i, "비밀번호" + i);
        }
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
}
