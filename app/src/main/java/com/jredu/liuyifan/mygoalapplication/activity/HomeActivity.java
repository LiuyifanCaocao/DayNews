package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.folable.UnfoldableDetailsActivity;
import com.jredu.liuyifan.mygoalapplication.fragment.AttentionFragment;
import com.jredu.liuyifan.mygoalapplication.fragment.HomeBodyFragment;
import com.jredu.liuyifan.mygoalapplication.fragment.HomeFragment;
import com.jredu.liuyifan.mygoalapplication.fragment.LogonFragment;
import com.jredu.liuyifan.mygoalapplication.fragment.VideoFragment;
import com.jredu.liuyifan.mygoalapplication.util.Back_Ground_Bmp;
import com.jredu.liuyifan.mygoalapplication.util.Constant;

public class HomeActivity extends AppCompatActivity implements HomeBodyFragment.dismiss {
    AttentionFragment mAttentionFragment;
    VideoFragment mVideoFragment;
    HomeFragment mHomeFragment;
    LogonFragment mLogonFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ImageView imageView;
    TextView textView;
    RelativeLayout relativeLayout_1;
    RelativeLayout relativeLayout_2;
    RelativeLayout relativeLayout_3;


    RadioGroup mRadioGroup;
    FrameLayout mFrameLayoutHome;
    RadioButton mRadioButton1;
    RadioButton mRadioButton2;
    RadioButton mRadioButton3;
    RadioButton mRadioButton4;

    PopupWindow popupWindow;

    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    RelativeLayout mRelativeLayout_left;

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    //广播
    MyBroadcastReceiver_Attention myBroadcastReceiver_attention;

    RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRequestQueue = Volley.newRequestQueue(HomeActivity.this);
        mSharedPreferences = getSharedPreferences("偏好",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        relativeLayout_1 = (RelativeLayout) findViewById(R.id.relativeLayout_1);
        relativeLayout_2 = (RelativeLayout) findViewById(R.id.relativeLayout_2);
        relativeLayout_3 = (RelativeLayout) findViewById(R.id.relativeLayout_3);
        relativeLayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new Intent(HomeActivity.this,UnfoldableDetailsActivity.class)));
            }
        });
        relativeLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new Intent(HomeActivity.this,MusicActivity.class)));

            }
        });
        relativeLayout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SettingActivity.class));
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mRelativeLayout_left = (RelativeLayout) findViewById(R.id.relativeLayout_left);
        mToolbar = (Toolbar) findViewById(R.id.tool);

        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.w2345_image_file_copy_13);
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        imageView.setImageBitmap(Back_Ground_Bmp.toRoundBitmap(bitmap));
        textView = (TextView) findViewById(R.id.text_view);

        mRadioButton1 = (RadioButton) findViewById(R.id.homeFragment_RadioButton);
        mRadioButton2 = (RadioButton) findViewById(R.id.videoFragment_RadioButton);
        mRadioButton3 = (RadioButton) findViewById(R.id.attentionFragment_RadioButton);
        mRadioButton4 = (RadioButton) findViewById(R.id.logonFragment_RadioButton);
        mRadioGroup = (RadioGroup) findViewById(R.id.choose);
        set_image();

        //打开左边的
        /*  mRadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mRelativeLayout_left);

            }
        });*/

        mHomeFragment = new HomeFragment();
        mVideoFragment = new VideoFragment();
        mAttentionFragment = new AttentionFragment();
        mLogonFragment = new LogonFragment();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.homeFragment, mHomeFragment);
        mFragmentTransaction.add(R.id.homeFragment, mVideoFragment);
        mFragmentTransaction.add(R.id.homeFragment, mAttentionFragment);
        mFragmentTransaction.add(R.id.homeFragment, mLogonFragment);
        mFragmentTransaction.hide(mVideoFragment);
        mFragmentTransaction.hide(mAttentionFragment);
        mFragmentTransaction.hide(mLogonFragment);
        mFragmentTransaction.commit();
        mFrameLayoutHome = (FrameLayout) findViewById(R.id.homeFragment);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.hide(mHomeFragment);
                mFragmentTransaction.hide(mVideoFragment);
                mFragmentTransaction.hide(mAttentionFragment);
                mFragmentTransaction.hide(mLogonFragment);
                mFragmentTransaction.commit();
                switch (checkedId) {
                    case R.id.homeFragment_RadioButton:
                        change(mHomeFragment);
                        break;
                    case R.id.videoFragment_RadioButton:
                        change(mVideoFragment);
                        break;
                    case R.id.attentionFragment_RadioButton:
                        mFragmentManager = getSupportFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mAttentionFragment.show_list();
                        mFragmentTransaction.commit();
                        change(mAttentionFragment);
                        break;
                    case R.id.logonFragment_RadioButton:
                        change(mLogonFragment);
                        break;
                }
            }
        });

        //广播
        myBroadcastReceiver_attention = new MyBroadcastReceiver_Attention();
        IntentFilter filter = new IntentFilter();
        filter.addAction("attention");
        registerReceiver(myBroadcastReceiver_attention, filter);
    }

    //显示特定的fragment
    public void change(Fragment fragment) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.show(fragment);
        mFragmentTransaction.commit();
    }

    //点击其他地方关闭popupWindow
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
            Toast.makeText(getApplication(), "1", Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }

    private long exitTime = 0;

    //二次点击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplication(), "再点一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setpopup(PopupWindow window) {
        popupWindow = window;
    }

    //设置底层文件
    public void set_image(){
        Drawable drawable1 = getResources().getDrawable(R.drawable.checkhome);
        drawable1.setBounds(0, 0, 40, 40);
        mRadioButton1.setCompoundDrawables(null, drawable1, null, null);
        Drawable drawable2 = getResources().getDrawable(R.drawable.checkvedio);
        drawable2.setBounds(0, 0, 40, 40);
        mRadioButton2.setCompoundDrawables(null, drawable2, null, null);
        Drawable drawable3 = getResources().getDrawable(R.drawable.checkattention);
        drawable3.setBounds(0, 0, 40, 40);
        mRadioButton3.setCompoundDrawables(null, drawable3, null, null);
        Drawable drawable4 = getResources().getDrawable(R.drawable.checklogon);
        drawable4.setBounds(0, 0, 40, 40);
        mRadioButton4.setCompoundDrawables(null, drawable4, null, null);
    }

    //读取偏好
    public void get_shared(){
        String s = mSharedPreferences.getString(Constant.NewTable.SET_COL_CHOOSE_FLOW,"0");
        mHomeFragment.show_not(s);
        mVideoFragment.show_or_not(s);
    }

    //重启读取偏好设置
    @Override
    protected void onResume() {
        super.onResume();
        get_shared();

    }

    //关注的广播
    public class MyBroadcastReceiver_Attention extends BroadcastReceiver {
        public MyBroadcastReceiver_Attention() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String user = intent.getStringExtra("user");
            String title = intent.getStringExtra("title");
            String ctime = intent.getStringExtra("ctime");
            String picUrl = intent.getStringExtra("picUrl");
            String name = intent.getStringExtra("name");
            if (picUrl != null&& name != null){
                textView.setText(name);
                set_img(imageView,picUrl);
            }
            //mAttentionFragment.get_broadcast(user,title,ctime);
            //mAttentionFragment.get_list();
        }
    }

    //注销广播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myBroadcastReceiver_attention != null){
            unregisterReceiver(myBroadcastReceiver_attention);
        }
    }
    //设置图片
    private void set_img(final ImageView imageView, String s ){
        ImageRequest mImageRequest = new ImageRequest(
                s,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(Back_Ground_Bmp.toRoundBitmap(response));

                    }
                },
                0,
                0,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.drawable.w2345_image_file_copy_7);
                    }
                }
        );
        mRequestQueue.add(mImageRequest);
    }
}
