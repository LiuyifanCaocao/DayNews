package com.jredu.liuyifan.mygoalapplication.entity;

/**
 * Created by DELL on 2016/9/9.
 */
public class News {
    private int img1;
    private int img2;
    private int img3;
    private String title;
    private String user;
    private int img4;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public int getImg2() {
        return img2;
    }

    public void setImg2(int img2) {
        this.img2 = img2;
    }

    public int getImg3() {
        return img3;
    }

    public int getImg4() {
        return img4;
    }

    public void setImg4(int img4) {
        this.img4 = img4;
    }

    public void setImg3(int img3) {
        this.img3 = img3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public News(String title, String user, int img4,int num) {
        this.title = title;
        this.user = user;
        this.img4 = img4;
        this.num = num;
    }

    public News(int img3, String title, String user, int img4,int num) {
        this.img3 = img3;
        this.title = title;
        this.user = user;
        this.img4 = img4;
        this.num = num;
    }

    public News(int img1, int img2, int img3, String title, String user, int img4,int num) {
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.title = title;
        this.user = user;
        this.img4 = img4;
        this.num = num;
    }
}
