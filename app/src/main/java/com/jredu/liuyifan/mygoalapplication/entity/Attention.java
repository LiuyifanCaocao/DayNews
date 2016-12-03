package com.jredu.liuyifan.mygoalapplication.entity;

import android.graphics.Bitmap;

/**
 * Created by DELL on 2016/10/10.
 */
public class Attention {
    private Bitmap bitmap;
    private String title;
    private String time;
    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Attention(Bitmap bitmap, String title, String time) {
        this.bitmap = bitmap;
        this.title = title;
        this.time = time;
    }
    public Attention(String picUrl, String title, String time) {
        this.picUrl = picUrl;
        this.title = title;
        this.time = time;
    }

}
