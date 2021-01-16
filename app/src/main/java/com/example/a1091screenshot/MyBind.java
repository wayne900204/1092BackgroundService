package com.example.a1091screenshot;

import android.os.Binder;


public class MyBind extends Binder {

    Move move;
    MyBind(Move move){
        this.move = move;
    }

    /**
     * 暂停
     */
    public void stopRunning() {
        if (move.isRunning) {
            move.stopRunning();
        }
    }

    /**
     * 播放
     */
    public void startRunning() {
        if (!move.isRunning) {
            move.startRunning();
        }
    }
    public int getCount(){
        return  move.getmCount();
    }
}
