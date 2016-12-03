package com.jredu.liuyifan.mygoalapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jredu.liuyifan.mygoalapplication.R;

public class DownLoadActivity extends AppCompatActivity {

    ImageView imageView_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        imageView_back = (ImageView) findViewById(R.id.back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
