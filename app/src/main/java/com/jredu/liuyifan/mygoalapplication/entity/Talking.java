package com.jredu.liuyifan.mygoalapplication.entity;

import android.graphics.Bitmap;

/**
 * Created by DELL on 2016/10/24.
 */
public class Talking {
    Bitmap bitmap;
    String name;
    String content;
    String date;
    String type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Talking(Bitmap bitmap, String name, String content, String date, String type) {
        this.bitmap = bitmap;
        this.name = name;
        this.content = content;
        this.date = date;
        this.type = type;
    }
}
