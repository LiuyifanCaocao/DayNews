package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.entity.News_net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Show_News_Activity extends AppCompatActivity {
    Intent mIntent;
    Bundle mBundle;
    WebView mWebView;
    RequestQueue mRequestQueue;
    ArrayList<News_net> list;
    ImageView mImageView;
    Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__news_);
        mIntent = getIntent();
        mBundle = mIntent.getExtras();
        mRequestQueue = Volley.newRequestQueue(getApplication());
        mWebView = (WebView) findViewById(R.id.text_content);
        mImageView = (ImageView) findViewById(R.id.two_img);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToast == null){
                    mToast = Toast.makeText(Show_News_Activity.this,"收藏成功",Toast.LENGTH_SHORT);
                }else {
                    mToast.setText("收藏成功");
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
                mImageView.setImageResource(R.drawable.love_video_press);
                Intent intent = new Intent("attention");
                Bundle bundle = new Bundle();
                bundle.putString("user", mBundle.getString("picUrl"));
                bundle.putString("title", mBundle.getString("title"));
                bundle.putString("ctime",mBundle.getString("ctime"));
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        });
        get_text();
    }

    //获得内容
    public void get_text() {
        StringRequest mStringRequest = new StringRequest(
                mBundle.getString("url"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
                        mWebView.getSettings().setJavaScriptEnabled(true);
                        mWebView.loadDataWithBaseURL(null,response,"text/html","utf-8",null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String s = new String(new String(response.data, "utf-8"));
                    return Response.success(s, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        mRequestQueue.add(mStringRequest);
    }

    //收藏
    public void collect(){
    }

}