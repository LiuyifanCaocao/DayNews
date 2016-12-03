package com.jredu.liuyifan.mygoalapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {

    MediaPlayer mediaPlayer;
    public MusicService() {
    }

    public class MyBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer.start();
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String path = bundle.getString("musicPath");
        String pause1 = bundle.getString("pause");
        String change = bundle.getString("change");
        mediaPlayer = new MediaPlayer();
        if (path != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
