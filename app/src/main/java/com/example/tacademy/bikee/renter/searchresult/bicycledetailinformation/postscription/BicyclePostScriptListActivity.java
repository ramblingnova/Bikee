package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.tacademy.bikee.R;

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
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_bicycle_post_script_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.activity_bicycle_post_script_list_list_view);
        adapter = new BicyclePostScriptAdapter();
        lv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            adapter.add("image url" + i, "렌터 이름" + i, (float)i/(float)2, "자전거에 대한 후기" + i, "15.11.04 " + i);
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
