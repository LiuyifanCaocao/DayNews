package com.jredu.liuyifan.mygoalapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    String no_img;
    public MyBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String user = intent.getStringExtra("user");
        if (TextUtils.isEmpty(user)){
            user = "false";
        }
        Toast.makeText(context,user,Toast.LENGTH_SHORT).show();
        no_img = intent.getStringExtra("change");
    }
    interface myReceiver{
        void change();
    }
    public String get_no_img(){
        return no_img;
    }
}
