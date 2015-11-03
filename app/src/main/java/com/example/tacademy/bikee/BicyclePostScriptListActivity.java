package com.example.tacademy.bikee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptListActivity extends AppCompatActivity {

    ListView lv;
    BicyclePostScriptAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicycle_post_script_list);

        lv = (ListView)findViewById(R.id.activity_bicycle_post_script_list_list_view);
        adapter = new BicyclePostScriptAdapter();
        lv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            adapter.add("" + i, "" + i, "" + i);
        }
    }
}
