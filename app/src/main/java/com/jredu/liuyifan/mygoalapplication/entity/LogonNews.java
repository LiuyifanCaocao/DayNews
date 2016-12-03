package com.jredu.liuyifan.mygoalapplication.entity;

/**
 * Created by DELL on 2016/9/21.
 */
public class LogonNews {
    private String title;
    private String src;
    private String comm;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LogonNews(String title, String src, String comm, String date) {
        this.title = title;
        this.src = src;
        this.comm = comm;
        this.date = date;
    }
}
