package com.example.tacademy.bikee.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tv = (TextView) findViewById(R.id.sign_up);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_id_password:
                Toast.makeText(this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
