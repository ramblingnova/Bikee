package com.example.tacademy.bikee.common.sidemenu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Inquires;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InputInquiryActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText title_edit_text;
    private EditText description_edit_text;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_inquiry);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_input_inquiry_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        title_edit_text = (EditText)findViewById(R.id.activity_input_inquiry_title_edit_text);
        description_edit_text = (EditText)findViewById(R.id.activity_input_inquiry_description_edit_text);
        btn = (Button)findViewById(R.id.activity_input_inquiry_button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_input_inquiry_button:
                Inquires inquires = new Inquires();
                inquires.setTitle(title_edit_text.getText().toString());
                inquires.setBody(description_edit_text.getText().toString());
                NetworkManager.getInstance().insertInquiry(inquires, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Code : " + receiveObject.getCode() + ", Success : " + receiveObject.isSuccess() + ", Msg : " + receiveObject.getMsg() + ", Error : ");
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                        finish();
                    }
                });
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
