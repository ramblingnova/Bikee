package com.example.tacademy.bikee.common.chatting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.tsengvn.typekit.TypekitContextWrapper;

public class ChattingRoomActivity extends AppCompatActivity {

    private Intent intent;
    private ChattingRoomItem item;
    private ListView lv;
    private TextView tv;
    private TalkAdapter adapter;

    public ChattingRoomActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_chatting_room_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        lv = (ListView)findViewById(R.id.view_chatting_room_item_conversation_list_view);
        adapter = new TalkAdapter();
        lv.setAdapter(adapter);
        intent = getIntent();
        item = (ChattingRoomItem)intent.getSerializableExtra("bicycle");
        tv = (TextView)findViewById(R.id.view_search_result_item_bicycle_name_text_view);
        tv.setText(item.getBicycle_name());
        tv = (TextView)findViewById(R.id.view_search_result_item_payment_real_text_view);
        tv.setText(item.getPayment());
        tv = (TextView)findViewById(R.id.view_search_result_item_type_real_text_view);
        tv.setText(item.getType());
        tv = (TextView)findViewById(R.id.view_search_result_item_height_real_text_view);
        tv.setText(item.getHeight());
        tv = (TextView)findViewById(R.id.view_search_result_item_distance_text_view);
        tv.setText(item.getDistance());

        initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            adapter.add("" + i, "" + i, "" + i);
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
