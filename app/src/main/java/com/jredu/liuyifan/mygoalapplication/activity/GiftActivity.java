package com.jredu.liuyifan.mygoalapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jredu.liuyifan.mygoalapplication.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GiftActivity extends AppCompatActivity {
    URL mUrl;
    GifImageView gifImageView;
    String path;
    File file;
    Button mButton_get;
    Button mButton_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        mButton_get = (Button) findViewById(R.id.get);
        mButton_set = (Button) findViewById(R.id.set);
        path = gifImageView.getContext().getCacheDir().getAbsolutePath();
        Toast.makeText(GiftActivity.this,path,Toast.LENGTH_SHORT).show();
        file = new File(path,"img_gif");
        mButton_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        downloadToStream("http://img4.hao123.com/data/3_b125ff2c27ea4375038faa018125dfec_0",file);
                    }
                }).start();

            }
        });
        mButton_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_img();
            }
        });
    }


    public long downloadToStream(String uri, final File targetFile) {

        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        long result = -1;

        try {
            try {
                final URL url = new URL(uri);
                outputStream = new FileOutputStream(targetFile);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.connect();
                if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {

                    bis = new BufferedInputStream(httpURLConnection.getInputStream());
                    result = httpURLConnection.getExpiration();
                    result = result < System.currentTimeMillis() ? System.currentTimeMillis() + 40000 : result;

                } else {

                    return -1;
                }
            } catch (final Exception ex) {
                return -1;

            }

            byte[] buffer = new byte[4096];//每4k更新进度一次
            int len = 0;
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            while ((len = bis.read(buffer)) != -1) {
                out.write(buffer, 0, len);

            }
            out.flush();

        } catch (Throwable e) {
            result = -1;


        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (final Throwable e) {

                }
            }
        }
        return result;
    }
    //网络请求
    public void get_img() {
        OutputStream os = null;
        HttpURLConnection mHttpURLConnection = null;
        BufferedInputStream bis = null;
        try {
            try {
                mUrl = new URL("http://img4.hao123.com/data/3_b125ff2c27ea4375038faa018125dfec_0");
                Toast.makeText(GiftActivity.this,"ok1",Toast.LENGTH_SHORT).show();
                mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
                os = new FileOutputStream(file);
                mHttpURLConnection.setConnectTimeout(20000);
                mHttpURLConnection.setReadTimeout(10000);
                mHttpURLConnection.setDoInput(true);
                mHttpURLConnection.setDoOutput(true);
                final int responseCode = mHttpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    bis = new BufferedInputStream(mHttpURLConnection.getInputStream());
                    Toast.makeText(GiftActivity.this,"ok2",Toast.LENGTH_SHORT).show();

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            byte[] b = new byte[4096];
            int len = 0;
            BufferedOutputStream bos = new BufferedOutputStream(os);
            Toast.makeText(GiftActivity.this,"ok3",Toast.LENGTH_SHORT).show();

            while ((len = bis.read(b)) != -1) {
                bos.write(b,0,len);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //设置图片
    public void set_img(){
        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(file);
            gifImageView.setImageDrawable(gifDrawable);
            Toast.makeText(GiftActivity.this,"ok4",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
}
