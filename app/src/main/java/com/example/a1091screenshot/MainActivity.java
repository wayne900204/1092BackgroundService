package com.example.a1091screenshot;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1092Servicescreenshot.R;

public class MainActivity extends AppCompatActivity {
    Intent mMyIntent;
    MyBind mMyBind;
    Button mStart, mStop;
    Handler mHandler = new Handler();
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /** 啟用 Service */
        mMyIntent = new Intent(this, MyService.class);
        startService(mMyIntent);
        /** 將Service的播放狀態進行監聽，並綁定給介面 */
        bindService(mMyIntent, mServiceConnection, BIND_AUTO_CREATE);
        /** 取得介面 */
        mStart = findViewById(R.id.start);
        mStop = findViewById(R.id.stop);
        mTextView = findViewById(R.id.textView);

    }

    /**
     * 如果介面有和MediaService成功綁定時，便會跳至onServiceConnected，反之onServiceDisconnected
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            /**取用Binder中的各種 function */
            mMyBind = (MyBind) iBinder;
            mStart.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mMyBind.startRunning();
                        }
                    }
            );
            mStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMyBind.stopRunning();
                    stopService(mMyIntent);
                    finish();
                }
            });
            /** 開始使用執行緒，使之每秒更新 textView */
            mHandler.post(runnable);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            unbindService(mServiceConnection);
        }

    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                mTextView.setText(mMyBind.getCount() + "");
                mHandler.postDelayed(runnable, 1000);
            } catch (Exception e) {
            }
        }
    };
    /**畫面消失時，則不監聽目前的狀態*/
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }
}