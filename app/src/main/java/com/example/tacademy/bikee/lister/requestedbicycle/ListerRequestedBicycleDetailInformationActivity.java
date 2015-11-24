package com.example.tacademy.bikee.lister.requestedbicycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.dialog.NoChoiceDialogFragment;
//import com.tsengvn.typekit.TypekitContextWrapper;

public class ListerRequestedBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ChoiceDialogFragment dialog1;
    private NoChoiceDialogFragment dialog2;
    private Intent intent;
    private Button cancelButton;
    private Button approvalButton;
    private Button cancelButton2;
    private Button inputPostBackButton;
    private Button smallMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_requested_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_lister_requested_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        intent = getIntent();
        int i = intent.getIntExtra("STATE", -1);
        Toast.makeText(ListerRequestedBicycleDetailInformationActivity.this, "STATE : " + i, Toast.LENGTH_SHORT).show();
        cancelButton = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button);
        cancelButton.setOnClickListener(this);
        approvalButton = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_approval_button);
        approvalButton.setOnClickListener(this);
        cancelButton2 = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button2);
        cancelButton2.setOnClickListener(this);
        inputPostBackButton = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button);
        inputPostBackButton.setOnClickListener(this);
        smallMapButton = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_small_map_button);
        smallMapButton.setOnClickListener(this);
        switch (i) {
            case 0:
                cancelButton.setVisibility(View.VISIBLE);
                approvalButton.setVisibility(View.VISIBLE);
                cancelButton2.setVisibility(View.GONE);
                inputPostBackButton.setVisibility(View.GONE);
                break;
            case 1:
                cancelButton.setVisibility(View.GONE);
                approvalButton.setVisibility(View.GONE);
                cancelButton2.setVisibility(View.VISIBLE);
                inputPostBackButton.setVisibility(View.GONE);
                break;
            case 2:
                cancelButton.setVisibility(View.GONE);
                approvalButton.setVisibility(View.GONE);
                cancelButton2.setVisibility(View.GONE);
                inputPostBackButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_lister_requested_bicycle_detail_information_cancel_button:
                dialog1 = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.LISTER_CANCEL_RESERVATION);
                dialog1.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_approval_button:
                dialog2 = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.LISTER_APPROVE_RESERVATION, NoChoiceDialogFragment.LISTER_MOVE_TO_LISTER_REQUESTED);
                dialog2.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_cancel_button2:
                dialog1 = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.LISTER_CANCEL_RESERVATION);
                dialog1.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button:
                finish();
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_small_map_button:
                Intent intent = new Intent(ListerRequestedBicycleDetailInformationActivity.this, SmallMapActivity.class);
                startActivity(intent);
                break;
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
