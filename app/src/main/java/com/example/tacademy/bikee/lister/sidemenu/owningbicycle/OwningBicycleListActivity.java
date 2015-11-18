package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.RegisterBicycleActivity;

public class OwningBicycleListActivity extends AppCompatActivity {

    ListView lv;
    OwningBicycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owning_bicycle_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_owning_bicycle_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        lv = (ListView)findViewById(R.id.activity_owning_bicycle_list_list_view);
        adapter = new OwningBicycleAdapter();
        lv.setAdapter(adapter);
        initData();

        Button btn = (Button)findViewById(R.id.activity_owning_bicycle_list_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwningBicycleListActivity.this, RegisterBicycleActivity.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OwningBicycleItem item = (OwningBicycleItem) lv.getItemAtPosition(position);
                Toast.makeText(OwningBicycleListActivity.this, "position : " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OwningBicycleListActivity.this, OwningBicycleDetailInformationActivity.class);
                // TODO
                startActivity(intent);
            }
        });
    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            adapter.add("" + i, "" + i);
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