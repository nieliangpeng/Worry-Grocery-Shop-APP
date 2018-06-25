package com.example.worrygroceryshop.bean;

/**
 * Created by 夏天 on 2018/6/1.
 */

public class Mmsic {
    private Integer music_id;
    private String music_name;
    private String music_auth;
    private String music_url;
    private String music_img;

    public Mmsic(Integer music_id, String music_name, String music_auth, String music_url, String music_img) {
        this.music_id = music_id;
        this.music_name = music_name;
        this.music_auth = music_auth;
        this.music_url = music_url;
        this.music_img = music_img;
    }

    public Integer getMusic_id() {
        return music_id;
    }

    public String getMusic_name() {
        return music_name;
    }

    public String getMusic_auth() {
        return music_auth;
    }

    public String getMusic_url() {
        return music_url;
    }

    public String getMusic_img() {
        return music_img;
    }

    public void setMusic_id(Integer music_id) {
        this.music_id = music_id;
    }

    public void setMusic_auth(String music_auth) {
        this.music_auth = music_auth;
    }

    public void setMusic_img(String music_img) {
        this.music_img = music_img;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }
}
