package com.bigtion.bikee.etc.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bigtion.bikee.R;
import com.bigtion.bikee.common.SplashActivity;
import com.bigtion.bikee.etc.manager.PropertyManager;
import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

   private static final String TAG = "GCM_Example";

   // 커스텀 메세지 받기
   @Override
   public void onMessageReceived(String from, Bundle data) {
      String title = data.getString("title");
      String message = data.getString("body");

      Log.d(TAG, "GCMListener - onMessageReceived");
       if (PropertyManager.getInstance().isPushEnable())

           // 도착한 메세지를 사용자에게 알린다.
           sendNotification(title, message);
   }

   private void sendNotification(String title, String message) {
      // 알림 터치시 - MainActivity가 나타나도록
      Intent intent = new Intent(this, SplashActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

      // 알림 효과음
      Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

      // 알림 객체
       Notification notification = new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.icon_a)    // 아이콘
              .setContentTitle(title)             // 제목
              .setContentText(message)            // 내용
              .setContentIntent(pendingIntent)    // 알림 선택시
              .setSound(soundUri)                 // 알림음
              .setAutoCancel(true)                // 센터에서 자동 삭제
              .build();

      // 알림 매니저를 통해서 발송
      NotificationManager notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      notiManager.notify(0, notification);
   }
}