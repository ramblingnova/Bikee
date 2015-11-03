package com.example.tacademy.bikee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardManagementActivity extends AppCompatActivity {

    ListView lv;
    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_management);

        Button btn = (Button)findViewById(R.id.activity_card_management_add_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardManagementActivity.this, RegisterCardActivity.class);
                startActivity(intent);
            }
        });

        lv = (ListView)findViewById(R.id.activity_card_management_list_view);
        adapter = new CardAdapter();
        lv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            adapter.add("" + i);
        }
    }
}
