package com.jredu.liuyifan.mygoalapplication.entity;

/**
 * Created by DELL on 2016/10/12.
 */
public class Joke {
    private String id;
    private String title;
    private String img;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Joke(String id, String title, String img) {
        this.id = id;
        this.title = title;
        this.img = img;
    }

    public Joke(String id, String title, String img, String type) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.type = type;
    }
}
