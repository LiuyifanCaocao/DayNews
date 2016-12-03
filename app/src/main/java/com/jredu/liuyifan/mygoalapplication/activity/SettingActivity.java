package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.util.HuanCun;

import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.SET_COL_CHOOSE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.SET_COL_CHOOSE_FLOW;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.SET_COL_COLLECTION;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.SET_COL_FONT_SIZE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.SET_COL_NETWORK_FLOW;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.SET_COL_PUSH_NOTIFICATION;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.SET_COL_THE_LIST_SHOW;

public class SettingActivity extends AppCompatActivity {
    ImageView mImageView_back;
    RelativeLayout mRelativeLayout_text_size;
    TextView mTextView_text_size;
    AlertDialog mAlertDialog;
    RelativeLayout mRelativeLayout_network_flow;
    RelativeLayout mRelativeLayout;
    Button mButton_come_back;

    //数据库表的内容
    String set_col_the_list_show;
    String set_col_font_size;
    String set_col_network_flow;
    String set_col_push_notification;
    String set_col_collection;
    String set_col_choose;
    String set_col_choose_flow;

    //列表显示摘要
    CheckBox mCheckBox_show_list;

    //字体大小
    RadioGroup mRadioGroup;
    RadioButton mRadioButton1;
    RadioButton mRadioButton2;
    RadioButton mRadioButton3;
    RadioButton mRadioButton4;

    //网络流量
    TextView mTextView_network_flow;
    RadioGroup mRadioGroup_flow;
    RadioButton mRadioButton_flow1;
    RadioButton mRadioButton_flow2;
    RadioButton mRadioButton_flow3;

    //推送通知
    CheckBox mCheckBox_push_notification;

    //收藏时转发
    CheckBox mCheckBox_collection;


    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    //缓存
    HuanCun huanCan ;
    TextView textView_HuanCun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mImageView_back = (ImageView) findViewById(R.id.back);
        mButton_come_back = (Button) findViewById(R.id.come_back);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_broadcast);
        mCheckBox_show_list = (CheckBox) findViewById(R.id.checkbox_show_list);
        mTextView_text_size = (TextView) findViewById(R.id.text_size);
        mRelativeLayout_text_size = (RelativeLayout) findViewById(R.id.relativeLayout_text_size);
        mRelativeLayout_network_flow = (RelativeLayout) findViewById(R.id.relativeLayout_network_flow);
        mTextView_network_flow = (TextView) findViewById(R.id.text_view_network_flow);
        mCheckBox_push_notification = (CheckBox) findViewById(R.id.checkbox_push_notification);
        mCheckBox_collection = (CheckBox) findViewById(R.id.checkbox_collection);
        mSharedPreferences = getSharedPreferences("偏好",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,MyBroadcastActivity.class);
                startActivity(intent);
            }
        });
        mButton_come_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putString(SET_COL_THE_LIST_SHOW, "0");
                mEditor.putString(SET_COL_FONT_SIZE, "0");
                mEditor.putString(SET_COL_NETWORK_FLOW, "0");
                mEditor.putString(SET_COL_PUSH_NOTIFICATION, "0");
                mEditor.putString(SET_COL_COLLECTION, "0");
                mEditor.putString(SET_COL_CHOOSE,"0");
                mEditor.putString(SET_COL_CHOOSE_FLOW,"0");
                mEditor.putString("isfinish","is");
                mEditor.commit();
                show();
            }
        });

        shared();
        show();
        mCheckBox_show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_checkbox_list();
            }
        });

        mRelativeLayout_text_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_text_size();
            }
        });

        mRelativeLayout_network_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change__network_flow();
            }
        });

        mCheckBox_push_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_checkbox_push_notification();
            }
        });

        mCheckBox_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_checkbox_collection();
            }
        });

        mImageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //缓存
        huanCan = new HuanCun();
        textView_HuanCun = (TextView) findViewById(R.id.huanCun);
        getHuancun();
        textView_HuanCun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("清除缓存")
                        .setMessage("是否清除缓存")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                huanCan.clearAllCache(SettingActivity.this);
                                getHuancun();
                            }
                        })
                        .show();
            }
        });


    }
    //获得字体大小
    public float get_size(){
        float f;
        if (set_col_choose == null){
            return 15;
        }else {
            switch (set_col_choose){
                case "0":
                    f = 10;
                    break;
                case "1":
                    f = 15;
                    break;
                case "2":
                    f = 20;
                    break;
                case "3":
                    f = 25;
                    break;
                default:
                    f = 15;
            }
            return f;
        }
    }

    //是否显示列表摘要
    public void change_checkbox_list(){
        if (!mCheckBox_show_list.isChecked()){
            set_col_the_list_show = "0";
        }else {
            set_col_the_list_show = "1";
        }
        up_date();
        show();
    }

    //选择字体大小
    public void set_text_size(){
        mAlertDialog = new AlertDialog.Builder(SettingActivity.this).create();
        mAlertDialog.setView(new RadioGroup(getApplication()));
        mAlertDialog.show();
        mAlertDialog.setContentView(R.layout.layout_setting_text_size);
        Window window = mAlertDialog.getWindow();
        mRadioButton1 = (RadioButton) window.findViewById(R.id.xiao);
        mRadioButton2 = (RadioButton) window.findViewById(R.id.zhong);
        mRadioButton3 = (RadioButton) window.findViewById(R.id.da);
        mRadioButton4 = (RadioButton) window.findViewById(R.id.teda);
        mRadioGroup = (RadioGroup) window.findViewById(R.id.radio_group);
        switch (set_col_font_size) {
            case "0":
                mRadioButton1.setChecked(true);
                break;
            case "1":
                mRadioButton2.setChecked(true);
                break;
            case "2":
                mRadioButton3.setChecked(true);
                break;
            case "3":
                mRadioButton4.setChecked(true);
                break;
        }
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.xiao:
                        set_col_font_size = "0";
                        set_col_choose = "0";
                        mAlertDialog.dismiss();
                        up_date();
                        show();
                        break;
                    case R.id.zhong:
                        set_col_font_size = "1";
                        set_col_choose = "1";
                        mAlertDialog.dismiss();
                        up_date();
                        show();
                        break;
                    case R.id.da:
                        set_col_font_size = "2";
                        set_col_choose = "2";
                        mAlertDialog.dismiss();
                        up_date();
                        show();
                        break;
                    case R.id.teda:
                        set_col_font_size = "3";
                        set_col_choose = "3";
                        mAlertDialog.dismiss();
                        up_date();
                        show();
                        break;
                }
            }
        });
    }

    //选择网络流量
    public void change__network_flow(){
        mAlertDialog = new AlertDialog.Builder(SettingActivity.this).create();
        mAlertDialog.setView(new RadioGroup(getApplication()));
        mAlertDialog.show();
        mAlertDialog.setContentView(R.layout.layout_setting_network_flow);
        Window window = mAlertDialog.getWindow();
        mRadioGroup_flow = (RadioGroup) window.findViewById(R.id.radio_group);
        mRadioButton_flow1 = (RadioButton) window.findViewById(R.id.big);
        mRadioButton_flow2 = (RadioButton) window.findViewById(R.id.small);
        mRadioButton_flow3 = (RadioButton) window.findViewById(R.id.no);
        switch (set_col_choose_flow){
            case "0":
                mRadioButton_flow1.setChecked(true);
                break;
            case "1":
                mRadioButton_flow2.setChecked(true);
                break;
            case "2":
                mRadioButton_flow3.setChecked(true);
                break;
        }
        mRadioGroup_flow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.big:
                        set_col_network_flow = "0";
                        set_col_choose_flow = "0";
                        mAlertDialog.dismiss();
                        up_date();
                        show();
                        break;
                    case R.id.small:
                        set_col_network_flow = "1";
                        set_col_choose_flow = "1";
                        mAlertDialog.dismiss();
                        up_date();
                        show();
                        break;
                    case R.id.no:
                        set_col_network_flow = "2";
                        set_col_choose_flow = "2";
                        mAlertDialog.dismiss();
                        up_date();
                        show();
                        break;
                }
            }
        });
    }

    //推送通知
    public void change_checkbox_push_notification(){
        if (!mCheckBox_push_notification.isChecked()){
            set_col_push_notification = "0";
        }else {
            set_col_push_notification = "1";
        }
        up_date();
        show();
    }

    //收藏时转发
    public void change_checkbox_collection(){
        if (!mCheckBox_collection.isChecked()){
            set_col_collection = "0";
        }else {
            set_col_collection = "1";
        }
        up_date();
        show();
    }

    //偏好初始化
    public void shared(){
        if (mSharedPreferences.getString("isfinish",null) == null){
            mEditor.putString(SET_COL_THE_LIST_SHOW, "0");
            mEditor.putString(SET_COL_FONT_SIZE, "0");
            mEditor.putString(SET_COL_NETWORK_FLOW, "0");
            mEditor.putString(SET_COL_PUSH_NOTIFICATION, "0");
            mEditor.putString(SET_COL_COLLECTION, "0");
            mEditor.putString(SET_COL_CHOOSE,"0");
            mEditor.putString(SET_COL_CHOOSE_FLOW,"0");
            mEditor.putString("isfinish","is");
            mEditor.commit();
        }
    }

    //调取
    public void show(){
        set_col_the_list_show = mSharedPreferences.getString(SET_COL_THE_LIST_SHOW,"0");
        set_col_font_size = mSharedPreferences.getString(SET_COL_FONT_SIZE,"0");
        set_col_network_flow = mSharedPreferences.getString(SET_COL_NETWORK_FLOW,"0");
        set_col_push_notification = mSharedPreferences.getString(SET_COL_PUSH_NOTIFICATION,"0");
        set_col_collection = mSharedPreferences.getString(SET_COL_COLLECTION,"0");
        set_col_choose = mSharedPreferences.getString(SET_COL_CHOOSE,"0");
        set_col_choose_flow = mSharedPreferences.getString(SET_COL_CHOOSE_FLOW,"0");
        //列表显示摘要初始化
        switch (set_col_the_list_show){
            case "0":
                mCheckBox_show_list.setChecked(false);
                break;
            case "1":
                mCheckBox_show_list.setChecked(true);
                break;
        }

        //字体大小初始化
        switch (set_col_choose){
            case "0":
                mTextView_text_size.setText("小");
                break;
            case "1":
                mTextView_text_size.setText("中");
                break;
            case "2":
                mTextView_text_size.setText("大");
                break;
            case "3":
                mTextView_text_size.setText("特大");
                break;
        }

        //网络流量选择初始化
        switch (set_col_choose_flow){
            case "0":
                mTextView_network_flow.setText("最佳效果(下载大图)");
                break;
            case "1":
                mTextView_network_flow.setText("较省流量(智能下图)");
                break;
            case "2":
                mTextView_network_flow.setText("不下载图");
                break;
        }

        //推送通知初始化
        switch (set_col_push_notification){
            case "0":
                mCheckBox_push_notification.setChecked(false);
                break;
            case "1":
                mCheckBox_push_notification.setChecked(true);
                break;
        }

        //收藏时转发初始化
        switch (set_col_collection){
            case "0":
                mCheckBox_collection.setChecked(false);
                break;
            case "1":
                mCheckBox_collection.setChecked(true);
                break;
        }
    }

    //更新
    public void up_date(){
        mEditor.putString(SET_COL_THE_LIST_SHOW, set_col_the_list_show);
        mEditor.putString(SET_COL_FONT_SIZE, set_col_font_size);
        mEditor.putString(SET_COL_NETWORK_FLOW, set_col_choose_flow);
        mEditor.putString(SET_COL_PUSH_NOTIFICATION, set_col_push_notification);
        mEditor.putString(SET_COL_COLLECTION, set_col_collection);
        mEditor.putString(SET_COL_CHOOSE,set_col_choose);
        mEditor.putString(SET_COL_CHOOSE_FLOW,set_col_choose_flow);
        mEditor.commit();
    }

    //广播
    public void broad(){
        Intent intent = new Intent();
        intent.setAction("no_img");
        intent.putExtra("change",set_col_push_notification);
        sendBroadcast(intent);
    }

    //
    public void getHuancun(){
        try {
            textView_HuanCun.setText(huanCan.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
