package com.jredu.liuyifan.mygoalapplication.entity;

import android.graphics.Bitmap;

/**
 * Created by DELL on 2016/9/22.
 */
public class Image {
    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;
    private Bitmap mBitmap;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Image(String picUrl, Bitmap mBitmap) {
        this.picUrl = picUrl;
        this.mBitmap = mBitmap;
    }

    public Image(String ctime, String title, String description, String picUrl, String url, Bitmap bitmap) {
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
        mBitmap = bitmap;
    }
}
