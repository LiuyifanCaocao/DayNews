package com.jredu.liuyifan.mygoalapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class OrderBroadcast_two_Receiver extends BroadcastReceiver {
    public OrderBroadcast_two_Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String s1 = intent.getStringExtra("user");
        Bundle bundle = getResultExtras(true);
        String s2 = bundle.getString("pwd");
        Toast.makeText(context,s1,Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2)){
            Toast.makeText(context,s1+s2,Toast.LENGTH_SHORT).show();
        }
    }
}
