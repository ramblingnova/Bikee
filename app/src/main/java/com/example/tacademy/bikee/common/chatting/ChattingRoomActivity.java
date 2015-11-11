package com.example.tacademy.bikee.common.chatting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.tacademy.bikee.R;

public class ChattingRoomActivity extends AppCompatActivity {

    ListView lv;
    TalkAdapter adapter;

    public ChattingRoomActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_chatting_room_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.view_chatting_room_item_conversation_list_view);
        adapter = new TalkAdapter();
        lv.setAdapter(adapter);
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
}
