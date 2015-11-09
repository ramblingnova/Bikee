package com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.tacademy.bikee.R;

public class EvaluatingBicyclePostScriptListActivity extends AppCompatActivity {

    ListView lv;
    EvaluatingBicyclePostScriptAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluating_bicycle_post_script_list);

        lv = (ListView)findViewById(R.id.activity_evaluating_bicycle_post_script_list_view);
        adapter = new EvaluatingBicyclePostScriptAdapter();
        lv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            adapter.add("" + i, "" + i, "" + i, "" + i);
        }
    }
}
