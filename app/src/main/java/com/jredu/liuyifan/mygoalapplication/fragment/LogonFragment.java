package com.jredu.liuyifan.mygoalapplication.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.activity.DownLoadActivity;
import com.jredu.liuyifan.mygoalapplication.activity.EventActivity;
import com.jredu.liuyifan.mygoalapplication.activity.FeedbackActivity;
import com.jredu.liuyifan.mygoalapplication.activity.LoginActivity;
import com.jredu.liuyifan.mygoalapplication.activity.MessageNotificationActivity;
import com.jredu.liuyifan.mygoalapplication.activity.MusicActivity;
import com.jredu.liuyifan.mygoalapplication.activity.SettingActivity;
import com.jredu.liuyifan.mygoalapplication.adapter.LogonAdapter;
import com.jredu.liuyifan.mygoalapplication.entity.Logon;
import com.jredu.liuyifan.mygoalapplication.folable.UnfoldableDetailsActivity;
import com.jredu.liuyifan.mygoalapplication.util.Back_Ground_Bmp;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogonFragment extends Fragment {
    ListView listView;
    List<Logon> list;
    Logon setting;
    ImageView mImageView;
    RelativeLayout mRelativeLayout_set;
    RelativeLayout mRelativeLayout_music;
    RelativeLayout mRelativeLayout;
    Toast mToast;
    String picUrl;
    String name;
    ImageView mImageView_login_img;
    TextView mTextView_login_name;
    final static int LOGON_FRAGMENT = 10001;
    final static int LOGON_ACTIVITY = -10001;
    RequestQueue mRequestQueue;

    Tencent mTencent;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGON_FRAGMENT && resultCode == LOGON_ACTIVITY){
            Bundle bundle_data = data.getExtras();
            picUrl = bundle_data.getString("picUrl");
            name = bundle_data.getString("name");
            if (picUrl == null){
                mImageView_login_img.setImageResource(R.drawable.w2345_image_file_copy_13);
                Bitmap bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();

                mImageView_login_img.setImageBitmap(Back_Ground_Bmp.toRoundBitmap(bitmap));
                mTextView_login_name.setText("草草");
            }else {
                change_item_img(mImageView_login_img,picUrl);
                mTextView_login_name.setText(name);
                Intent intent = new Intent("attention");
                Bundle bundle = new Bundle();
                bundle.putString("picUrl", picUrl);
                bundle.putString("name", name);
                intent.putExtras(bundle);
                getActivity().sendBroadcast(intent);
            }
        }
    }

    public LogonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logon, container, false);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.isPlaying();
        mRelativeLayout_music = (RelativeLayout) v.findViewById(R.id.music);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mRelativeLayout = (RelativeLayout) v.findViewById(R.id.toast);
        mImageView_login_img = (ImageView) v.findViewById(R.id.login_img);
        mRelativeLayout_set = (RelativeLayout) v.findViewById(R.id.set);
        mRelativeLayout_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        mRelativeLayout_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MusicActivity.class);
                startActivity(intent);
            }
        });
        mTextView_login_name = (TextView) v.findViewById(R.id.login_name);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UnfoldableDetailsActivity.class);
                startActivity(intent);
            }
        });
        listView = (ListView) v.findViewById(R.id.list);
        list = new ArrayList<Logon>();
        setting = new Logon("消息通知",R.drawable.goon);
        list.add(setting);
        setting = new Logon("离线",R.drawable.goon);
        list.add(setting);
        setting = new Logon("活动",R.drawable.goon);
        list.add(setting);
        setting = new Logon("商城",R.drawable.goon);
        list.add(setting);
        setting = new Logon("音乐",R.drawable.goon);
        list.add(setting);
        setting = new Logon("反馈",R.drawable.goon);
        list.add(setting);
        final LogonAdapter logonAdapter = new LogonAdapter(getActivity(),list);
        listView.setAdapter(logonAdapter);
        mImageView = (ImageView) v.findViewById(R.id.login_img);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextView_login_name.getText().equals("登录推荐更精准")){
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivityForResult(intent,LOGON_FRAGMENT);
                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("用户信息")
                            .setMessage("是否注销")
                            .setNegativeButton("取消",null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mImageView_login_img.setImageResource(R.drawable.log_in_register);
                                    mTextView_login_name.setText("登录推荐更精准");
                                }
                            })
                            .show();

                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(), MessageNotificationActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), DownLoadActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), EventActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), UnfoldableDetailsActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), MusicActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), FeedbackActivity.class));
                        break;
                }
            }
        });
        return v;
    }
    //每一项的图片
    public void change_item_img(final ImageView imageView, final String s) {

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
                        mImageView_login_img.setImageResource(R.drawable.w2345_image_file_copy_2);
                        Bitmap bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
                        mImageView_login_img.setImageBitmap(Back_Ground_Bmp.toRoundBitmap(bitmap));
                    }
                }
        );
        mRequestQueue.add(mImageRequest);
    }

}
