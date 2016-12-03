package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.adapter.LoginViewPagerAdapter;
import com.jredu.liuyifan.mygoalapplication.fragment.FeedBack2Fragment;
import com.jredu.liuyifan.mygoalapplication.fragment.FeedBackFragment;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity {
    ImageView mImageView;
    FeedBackFragment feedBackFragment;
    FeedBack2Fragment feedBack2Fragment;
    TabLayout mTableLayout;
    FragmentManager mFragmentManager;
    ViewPager mViewPager;
    TextView mTextView_send;
    EditText mEditText;


    MyBroadcastReceiver_FeedBack myBroadcastReceiver_feedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mImageView = (ImageView) findViewById(R.id.back);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextView_send = (TextView) findViewById(R.id.send);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTableLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTextView_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_text();
            }
        });
        //获得list和管理器
        ArrayList<Fragment> mList = new ArrayList<>();
        feedBackFragment = new FeedBackFragment();
        feedBack2Fragment = new FeedBack2Fragment();
        mFragmentManager = getSupportFragmentManager();
        mList.add(feedBackFragment);
        mList.add(feedBack2Fragment);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("我的意见");
        titles.add("常见问题");

        //调用适配器
        LoginViewPagerAdapter mLoginViewPagerAdapter = new LoginViewPagerAdapter(mFragmentManager);
        mLoginViewPagerAdapter.setData(mList);
        mLoginViewPagerAdapter.setTitles(titles);
        mViewPager.setAdapter(mLoginViewPagerAdapter);
        mTableLayout.setupWithViewPager(mViewPager);

        //接受广播
        myBroadcastReceiver_feedBack = new MyBroadcastReceiver_FeedBack();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("attention");
        registerReceiver(myBroadcastReceiver_feedBack,intentFilter);

    }

    //内容
    public void send_text(){
        if (!TextUtils.isEmpty(mEditText.getText())){
            feedBackFragment.get_text(mEditText.getText().toString());
            mEditText.setText(null);
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //广播
    public class MyBroadcastReceiver_FeedBack extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String picUrl = intent.getStringExtra("picUrl");
            String name = intent.getStringExtra("name");
            feedBackFragment.get_user(picUrl,name);
        }
    }

    //注销广播

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myBroadcastReceiver_feedBack != null){
            unregisterReceiver(myBroadcastReceiver_feedBack);
        }
    }
}
