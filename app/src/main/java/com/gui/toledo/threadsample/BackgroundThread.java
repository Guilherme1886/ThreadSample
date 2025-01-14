package com.gui.toledo.threadsample;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class BackgroundThread extends Thread {

    private Handler backgroundHandler;
    private Handler mainHandler;

    public BackgroundThread(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        Looper.prepare();

        backgroundHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                simulateBackgroundTask();
                quit();
            }
        };

        backgroundHandler.sendEmptyMessage(0);

        Looper.loop();
    }

    private void simulateBackgroundTask() {
        try {
            for (int i = 1; i <= 5; i++) {
                Thread.sleep(1000);
                Message message = mainHandler.obtainMessage();
                message.obj = "Message: Task " + i + " completed on: " + Thread.currentThread().getName();
                message.arg1 = i;
                mainHandler.sendMessage(message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        Looper.myLooper().quit();
    }
}
