package com.jredu.liuyifan.mygoalapplication.util;

import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DELL on 2016/9/21.
 */
public class GetNet {
    public String sn1;
    public String sn2;
    public String sn3;
    public int whats;
    public WebView mWebView;

    public GetNet(String sn1, String sn2, String sn3, int whats, WebView webView) {
        this.sn1 = sn1;
        this.sn2 = sn2;
        this.sn3 = sn3;
        this.whats = whats;
        mWebView = webView;

    }

    public void getNetMethod(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnectionGet();
            }
        });
    }

    public void HttpURLConnectionGet() {
        HttpURLConnection mHttpURLConnection;
        InputStream mInputStream;
        StringBuffer sb = null;
        try {
            URL mURL = new URL(sn1);
            mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
            mHttpURLConnection.setConnectTimeout(5*1000);
            mHttpURLConnection.setReadTimeout(5*1000);
            mHttpURLConnection.setRequestProperty(sn2,sn3);
            mHttpURLConnection.connect();
            if (mHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                mInputStream = mHttpURLConnection.getInputStream();
                int i;
                byte[] bytes = new byte[1024];
                sb = new StringBuffer();
                while ((i = mInputStream.read(bytes)) != -1){
                    sb.append(new String(bytes,0,i,"utf-8"));
                }
                mInputStream.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = mHandler.obtainMessage(whats,sb.toString());
        mHandler.sendMessage(message);
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null && msg.what == whats ){
                String s = (String) msg.obj;
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setDefaultTextEncodingName("utf-8");
                mWebView.loadDataWithBaseURL(null,s,"text/html","utf-8",null);
            }
        }
    };

}
