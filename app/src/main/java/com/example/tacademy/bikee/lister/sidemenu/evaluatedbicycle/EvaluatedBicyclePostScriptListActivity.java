package com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.tacademy.bikee.R;

public class EvaluatedBicyclePostScriptListActivity extends AppCompatActivity {

    ListView lv;
    EvaluatedBicyclePostScriptAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluated_bicycle_post_script_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_evaluated_bicycle_post_script_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        lv = (ListView) findViewById(R.id.activity_evaluated_bicycle_post_script_list_list_view);
        adapter = new EvaluatedBicyclePostScriptAdapter();
        lv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            adapter.add("" + i, "" + i, i, "" + i, "" + i);
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
