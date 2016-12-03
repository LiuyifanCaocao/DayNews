package com.jredu.liuyifan.mygoalapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class OrderBroadcast_one_Receiver extends BroadcastReceiver {
    public OrderBroadcast_one_Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String s1 = intent.getStringExtra("user");
        if (!TextUtils.isEmpty(s1)){
            Toast.makeText(context,s1,Toast.LENGTH_SHORT).show();
        }
        Bundle bundle = new Bundle();
        bundle.putString("pwd","liuyifan2");
        setResultExtras(bundle);
    }
}
