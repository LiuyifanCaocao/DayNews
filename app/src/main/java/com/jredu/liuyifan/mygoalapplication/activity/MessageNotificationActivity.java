package com.jredu.liuyifan.mygoalapplication.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.adapter.LoginViewPagerAdapter;
import com.jredu.liuyifan.mygoalapplication.fragment.MessageCommentFragment;
import com.jredu.liuyifan.mygoalapplication.fragment.MessagePraiseFragment;
import com.jredu.liuyifan.mygoalapplication.fragment.MessageSystemNotificationFragment;

import java.util.ArrayList;

public class MessageNotificationActivity extends AppCompatActivity {
    ImageView mImageView;

    MessageCommentFragment messageCommentFragment;
    MessagePraiseFragment messagePraiseFragment;
    MessageSystemNotificationFragment messageSystemNotificationFragment;


    ArrayList<Fragment> fragments;
    ArrayList<String> titles;

    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notification);
        mImageView = (ImageView) findViewById(R.id.back);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        messageCommentFragment = new MessageCommentFragment();
        messagePraiseFragment = new MessagePraiseFragment();
        messageSystemNotificationFragment = new MessageSystemNotificationFragment();

        fragments = new ArrayList<>();

        fragments.add(messageCommentFragment);
        fragments.add(messagePraiseFragment);
        fragments.add(messageSystemNotificationFragment);

        titles = new ArrayList<>();
        titles.add("评论");
        titles.add("赞");
        titles.add("系统通知");

        fragmentManager = getSupportFragmentManager();
        LoginViewPagerAdapter loginViewPagerAdapter = new LoginViewPagerAdapter(fragmentManager);
        loginViewPagerAdapter.setData(fragments);
        loginViewPagerAdapter.setTitles(titles);
        viewPager.setAdapter(loginViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
