package com.example.a1091screenshot;

import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;

public class Move   {
    String TAG = "ABC";
    int mCount;
    Vibrator mMyVibrator;
    Handler mHandler = new Handler();
    Boolean isRunning = false;
    Move(Vibrator myVibrator){
        mMyVibrator = myVibrator;
    }

    /** 讓他開始持續跑 */
    public void startRunning(){
        Log.d(TAG, "startRunning: "+"ABC");
        isRunning = true;
            mHandler.post(mRunnable);
    }
    /** 讓他停止持續跑 */
    public void stopRunning(){
        isRunning = false;
            mHandler.removeCallbacks(mRunnable);
    }
    /** 設定震動時間 */
    public void setTime(int time){
        Log.e(TAG, "StartIt: "+"ABC");

        mMyVibrator.vibrate(time);
    }
    /** 讓他無限一直跑 */
    public Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d(TAG, "run: "+"ABC");
                setTime(1000);
                mCount++;
                /**使這個執行緒每秒跑一次*/
                mHandler.postDelayed(mRunnable, 3000);
            }catch (Exception e){
            }
        }
    };
    /** 取得現在的數字是多少 */
    public int getmCount(){
        return mCount;
    }
}
