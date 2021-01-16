package com.example.a1091screenshot;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.a1092Servicescreenshot.R;

public class MyService extends Service {

    MyBind mMyBind;
    private static final String CHANNEL_ID = "COOL";

    @Override
    public void onCreate() {
        Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        Move move = new Move(myVibrator);
        mMyBind = new MyBind(move);
        /** 有用到本Service的話，則在通知欄顯示通知
         * notificationIntent 的部分是使如果使用者點擊通知的話，便會跳回本 APP 中 */
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("注意")
                .setContentText("手機計算中ＸＤ")
                .setSmallIcon(R.drawable.ic_baseline_audiotrack_24)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /**此處為取得Notification的使用權限*/
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "COOL"
                    , NotificationManager.IMPORTANCE_DEFAULT);
        }
        NotificationManager notificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = getSystemService(NotificationManager.class);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        return super.onStartCommand(intent, flags, startId);
    }
    /**綁定音樂處理的各種方法*/
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMyBind;
    }

}
