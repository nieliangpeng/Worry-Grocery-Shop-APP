package com.sky.Bean;

public class Word {
    private int word_id;
    private String word_imgResource;
    private String word_musicResource;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWord_id() {
        return word_id;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public String getWord_imgResource() {
        return word_imgResource;
    }

    public void setWord_imgResource(String word_imgResource) {
        this.word_imgResource = word_imgResource;
    }

    public String getWord_musicResource() {
        return word_musicResource;
    }

    public void setWord_musicResource(String word_musicResource) {
        this.word_musicResource = word_musicResource;
    }
}
