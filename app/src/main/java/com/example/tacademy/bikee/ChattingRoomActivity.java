package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ChattingRoomActivity extends AppCompatActivity {

    ListView lv;
    TalkAdapter adapter;

    public ChattingRoomActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);

        lv = (ListView)findViewById(R.id.view_chatting_room_item_list_view);
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
}
