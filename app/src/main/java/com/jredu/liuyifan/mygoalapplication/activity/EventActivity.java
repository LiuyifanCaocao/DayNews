package com.jredu.liuyifan.mygoalapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.jredu.liuyifan.mygoalapplication.R;

public class EventActivity extends AppCompatActivity {
    ImageView mImageView_two_img;
    ImageView mImageView_back;
    ImageView mImageView_one_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mImageView_one_img = (ImageView) findViewById(R.id.one_img);
        mImageView_two_img = (ImageView) findViewById(R.id.two_img);
        mImageView_back = (ImageView) findViewById(R.id.back);
        RotateAnimation animation = new RotateAnimation(0f, 359f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(10000);//设定转一圈的时间
        animation.setRepeatCount(Animation.INFINITE);//设定无限循环
        animation.setRepeatMode(Animation.RESTART);
        mImageView_two_img.startAnimation(animation);
        mImageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
