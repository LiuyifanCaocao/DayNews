package com.jredu.liuyifan.mygoalapplication.entity;

/**
 * Created by DELL on 2016/9/2.
 */
public class Logon {
    private int img;
    private String title;

    public Logon(String title, int img) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
