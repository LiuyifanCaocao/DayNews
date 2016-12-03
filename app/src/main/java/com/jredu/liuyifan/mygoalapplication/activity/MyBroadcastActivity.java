package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.service.MyBroadcastReceiver;

import static com.jredu.liuyifan.mygoalapplication.R.id.button_long;
import static com.jredu.liuyifan.mygoalapplication.R.id.order;

public class MyBroadcastActivity extends AppCompatActivity {
    MyBroadcastReceiver myBroadcastReceiver;
    Button mButton_short;
    Button mButton_long;
    Button mButton_order;
    IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_broadcast);
        mButton_short = (Button) findViewById(R.id.button_normal);
        mButton_long = (Button) findViewById(button_long);
        mButton_order = (Button) findViewById(order);
        mButton_short.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBroadcastReceiver = new MyBroadcastReceiver();
                intentFilter = new IntentFilter("2");
                Intent intent = new Intent("2");
                intent.putExtra("user","caocao");
                registerReceiver(myBroadcastReceiver,intentFilter);
                sendBroadcast(intent);
            }
        });
        mButton_long.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("1");
                intent.putExtra("user","cao");
                sendBroadcast(intent);
            }
        });
        mButton_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("order");
                intent.putExtra("user","liuyifan");
                sendOrderedBroadcast(intent,null);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myBroadcastReceiver != null){
            unregisterReceiver(myBroadcastReceiver);
        }
    }
}
