package com.jredu.liuyifan.mygoalapplication.entity;

/**
 * Created by DELL on 2016/9/21.
 */
public class SportNews {
    private String time;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public SportNews(String time, String title, String description, String picUrl, String url) {
        this.time = time;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
