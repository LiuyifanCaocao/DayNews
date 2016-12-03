package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.adapter.MusicAdapter;
import com.jredu.liuyifan.mygoalapplication.service.MusicService;
import com.jredu.liuyifan.mygoalapplication.util.AutoScrollTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MusicActivity extends AppCompatActivity {
    MusicService musicService;
    String musicPath;
    String musicName;
    String musicAlbumArtPath;
    String musicArtist;
    Map musicDataMap;
    ListView mListView;
    List<Map<String,String>> mList;
    RequestQueue mRequestQueue;
    CheckBox checkBox;
    Button button_back;
    Button button_next;
    int i = 0;
    Toast mToast;
    ImageView imageView_exit;
    AutoScrollTextView autoScrollTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        autoScrollTextView = (AutoScrollTextView)findViewById(R.id.TextViewNotice);
        button_back = (Button) findViewById(R.id.back);
        button_next = (Button) findViewById(R.id.next);
        imageView_exit = (ImageView) findViewById(R.id.exit);
        imageView_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRequestQueue = Volley.newRequestQueue(MusicActivity.this);
        mList = getMultiData();
        mListView = (ListView) findViewById(R.id.list_view);
        MusicAdapter musicAdapter = new MusicAdapter(MusicActivity.this,mList,mRequestQueue);
        mListView.setAdapter(musicAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(MusicActivity.this,MusicService.class);
                stopService(intent2);
                i = position;
                Intent intent = new Intent(MusicActivity.this,MusicService.class);
                Bundle bundle = new Bundle();
                bundle.putString("pause","goon");
                bundle.putString("change","notChange");
                bundle.putString("musicPath",mList.get(i).get("musicPath"));
                intent.putExtras(bundle);
                tuSi(mList.get(i).get("musicName"));
                startService(intent);
                checkBox.setChecked(true);
                change_text(mList.get(i).get("musicName"));
            }
        });
        pauseMusic(checkBox);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMusic(-1);
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMusic(1);
            }
        });
        scroll_text();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //暂停音乐
    public void pauseMusic(final CheckBox checkBox ){
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    Intent intent2 = new Intent(MusicActivity.this,MusicService.class);
                    stopService(intent2);
                    Intent intent = new Intent(MusicActivity.this,MusicService.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("pause","goon");
                    bundle.putString("change","notChange");
                    bundle.putString("musicPath",mList.get(i).get("musicPath"));
                    tuSi(mList.get(i).get("musicName"));
                    checkBox.setChecked(true);
                    intent.putExtras(bundle);
                    startService(intent);
                    change_text(mList.get(i).get("musicName"));
                }else {
                    Intent intent2 = new Intent(MusicActivity.this,MusicService.class);
                    stopService(intent2);
                    autoScrollTextView.stopScroll();
                }
            }
        });

    }

    //切换音乐
    public void changeMusic(int change){
        i = i + change;
        if (i<0){
            i = mList.size()-1;
        }
        if (i > mList.size()-1){
            i = 0;
        }
        Intent intent2 = new Intent(MusicActivity.this,MusicService.class);
        stopService(intent2);
        Intent intent = new Intent(MusicActivity.this,MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putString("pause","goon");
        bundle.putString("change","change");
        bundle.putString("musicPath",mList.get(i).get("musicPath"));
        intent.putExtras(bundle);
        tuSi(mList.get(i).get("musicName"));
        startService(intent);
        checkBox.setChecked(true);
        change_text(mList.get(i).get("musicName"));
    }

    //吐司
    public void tuSi(String s){
        if (mToast == null){
            mToast = Toast.makeText(MusicActivity.this,s,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(s);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    // 查找sdcard卡上的所有歌曲信息
    public List<Map<String,String>> getMultiData() {
        ArrayList<Map<String,String>> musicList = new ArrayList<>();
// 加入封装音乐信息的代码
// 查询所有歌曲
        ContentResolver musicResolver = this.getContentResolver();
        Cursor musicCursor = musicResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        int musicColumnIndex;
        if (null != musicCursor && musicCursor.getCount() > 0) {
            for (musicCursor.moveToFirst(); !musicCursor.isAfterLast(); musicCursor
                    .moveToNext()) {
                musicDataMap = new HashMap();
                Random random = new Random();
                int musicRating = Math.abs(random.nextInt()) % 10;
                musicDataMap.put("musicRating", musicRating);
// 取得音乐播放路径
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                musicPath = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("musicPath", musicPath);
// 取得音乐的名字
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
                musicName = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("musicName", musicName);
// 取得音乐的专辑名称
                /*musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
                musicAlbum = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("musicAlbum", musicAlbum);*/
// 取得音乐的演唱者
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
                musicArtist = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("musicArtist", musicArtist);
// 取得歌曲对应的专辑对应的Key
                /*musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_KEY);
                musicAlbumKey = musicCursor.getString(musicColumnIndex);
                String[] argArr = { musicAlbumKey };*/

                ContentResolver albumResolver = this.getContentResolver();
                Cursor albumCursor = albumResolver.query(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
                        MediaStore.Audio.AudioColumns.ALBUM_KEY + " = ?",
                        null, null);
                if (null != albumCursor && albumCursor.getCount() > 0) {
                    albumCursor.moveToFirst();
                    int albumArtIndex = albumCursor
                            .getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART);
                    musicAlbumArtPath = albumCursor.getString(albumArtIndex);
                    if (null != musicAlbumArtPath
                            && !"".equals(musicAlbumArtPath)) {
                        musicDataMap.put("musicAlbumImage", musicAlbumArtPath);
                    } else {
                        musicDataMap.put("musicAlbumImage",
                                "file:///mnt/sdcard/alb.jpg");
                    }
                } else {
// 没有专辑定义，给默认图片
                    musicDataMap.put("musicAlbumImage",
                            "file:///mnt/sdcard/alb.jpg");
                }
                musicList.add(musicDataMap);
            }
        }
        return musicList;
    }

    //字幕滚动
    public void scroll_text(){
        //启动公告滚动条
        autoScrollTextView.init(getWindowManager());
        autoScrollTextView.startScroll();
    }
    //改变字幕
    public void change_text(String s){
        //启动公告滚动条
        autoScrollTextView.setText(s);
        autoScrollTextView.init(getWindowManager());
        autoScrollTextView.startScroll();
    }
}
















/*
mButton_bind.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent = new Intent(MusicActivity.this,MusicService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        }
        });
        mButton_unbind.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        if (musicService != null){
        unbindService(serviceConnection);
        musicService = null;
        }
        }
        });
        mButton_start.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent = new Intent(MusicActivity.this,MusicService.class);
        startService(intent);
        Intent intent2 = new Intent(MusicActivity.this,MusicService.class);
        stopService(intent);
        Intent intent_stop = new Intent(MusicActivity.this,MusicService.class);
                stopService(intent_stop);
        }
        });*/
