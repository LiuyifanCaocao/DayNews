package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.adapter.LoginViewPagerAdapter;
import com.jredu.liuyifan.mygoalapplication.fragment.LoginPassWorldFragment;
import com.jredu.liuyifan.mygoalapplication.fragment.LoginPhoneFragment;
import com.jredu.liuyifan.mygoalapplication.fragment.LogonFragment;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements LoginPhoneFragment.PhoneFragment,LoginPassWorldFragment.PhoneFragment1 {
    LoginPassWorldFragment mLoginPassWorldFragment;
    LoginPhoneFragment mLoginPhoneFragment;
    TabLayout mTableLayout;
    FragmentManager mFragmentManager;
    ViewPager mViewPager;
    ImageView mImageView;
    Intent mIntent;
    Bundle mBundle;
    Toast mToast;
    final static int LOGON_ACTIVITY = -10001;

    Tencent mTencent;
    IUiListener loginListener;
    IUiListener userInfoListener;
    String nickname;
    String path;
    private String SCOPE = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTableLayout = (TabLayout) findViewById(R.id.tab_layout);
        mImageView = (ImageView) findViewById(R.id.exit);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获得list和管理器
        ArrayList<Fragment> mList = new ArrayList<>();
        mLoginPassWorldFragment = new LoginPassWorldFragment();
        mLoginPhoneFragment = new LoginPhoneFragment();
        mFragmentManager = getSupportFragmentManager();
        mList.add(mLoginPhoneFragment);
        mList.add(mLoginPassWorldFragment);
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("手机号登陆");
        titles.add("账号密码登录");

        //调用适配器
        LoginViewPagerAdapter mLoginViewPagerAdapter = new LoginViewPagerAdapter(mFragmentManager);
        mLoginViewPagerAdapter.setData(mList);
        mLoginViewPagerAdapter.setTitles(titles);
        mViewPager.setAdapter(mLoginViewPagerAdapter);
        mTableLayout.setupWithViewPager(mViewPager);

        mLoginPhoneFragment.get_phoneFragment(this);
        mLoginPassWorldFragment.get_pwdFragment(this);
    }

    @Override
    public void dismiss_phone() {
        mIntent = new Intent(LoginActivity.this,LogonFragment.class);
        mBundle = new Bundle();
        mBundle.putString("user",nickname);
        mBundle.putString("pwd",path);
        mIntent.putExtras(mBundle);
        setResult(LOGON_ACTIVITY,mIntent);
        finish();
    }

    @Override
    public void qq_login_phone() {
        qqLogin();
    }

    @Override
    public void dismiss() {
        mIntent = new Intent(LoginActivity.this,LogonFragment.class);
        mBundle = new Bundle();
        mBundle.putString("user",nickname);
        mBundle.putString("pwd",path);
        mIntent.putExtras(mBundle);
        setResult(LOGON_ACTIVITY,mIntent);
        finish();
    }

    @Override
    public void qq_login() {
        qqLogin();
    }
    //初始化QQ登录分享的需要的资源
    private void initQqLogin() {
        mTencent = Tencent.createInstance("222222", LoginActivity.this);
        //创建QQ登录回调接口
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后调用的方法

                JSONObject jo = (JSONObject) o;
                Log.e("COMPLETE:", jo.toString());
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                Log.e("LoginError:", uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(LoginActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        };

        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
               if (o == null) {
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) o;
                    int ret = jo.getInt("ret");
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    String figureurl = jo.getString("figureurl");
                    String path = jo.getString("figureurl_qq_1");
                    Toast.makeText(LoginActivity.this, "你好，" + nickName+gender, Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                }

                // 昵称
                JSONObject json = (JSONObject) o;
                try {
                    nickname = ((JSONObject) o).getString("nickname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // 头像
                try {
                    path = json.getString("figureurl_qq_1");
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                mIntent = new Intent(LoginActivity.this,LogonFragment.class);
                mBundle = new Bundle();
                mBundle.putString("name",nickname);
                mBundle.putString("picUrl",path);
                mIntent.putExtras(mBundle);
                setResult(LOGON_ACTIVITY,mIntent);
                finish();
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        };
    }

    public void qqLogin() {
        initQqLogin();
        mTencent.login(this, SCOPE, loginListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
                final UserInfo info = new UserInfo(this, mTencent.getQQToken());
                info.getUserInfo(userInfoListener);
            }
        }
    }
}
