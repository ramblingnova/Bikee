package com.bikee.www.etc.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bikee.www.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * GCM 서버에 기기를 등록하고 토큰을 발급받는 서비스다.
 */
public class RegistrationIntentService extends IntentService {
    static final private String TAG = "GCM_Example";
    static public final String REGISTRATION_COMPLETE_BROADCAST = "REGISTRATION_COMPLETE_BROADCAST";

    // 파라미터 없는 public 생성자 꼭 필요
    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "RegistrationIntentService Start!");
        try {
            // 발급받은 토큰
            InstanceID instanceID = InstanceID.getInstance(this);
            final String token = instanceID.getToken(getString(R.string.GCM_SenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.d(TAG, "Token : " + token);
            Intent completeIntent = new Intent(REGISTRATION_COMPLETE_BROADCAST);
            completeIntent.putExtra("TOKEN", token);
            completeIntent.getStringExtra("TOKEN");
            LocalBroadcastManager.getInstance(this).sendBroadcast(completeIntent);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Regist Exception", e);
        }
    }
}
