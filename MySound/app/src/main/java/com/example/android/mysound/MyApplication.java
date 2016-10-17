package com.example.android.mysound;

import android.app.Application;

/**
 * Created by Long Nguyen on 10/17/2016.
 */
public class MyApplication extends Application {
    private int songId;
    private String section;
    private String song;

    public int getSongId() {
        return songId;
    }

    public String getSection() {
        return section;
    }

    public String getSong() {
        return song;
    }

    public void setSongId(int current_id) {
        this.songId = current_id;
    }

    public void setSection(String current_section) {
        this.section = current_section;
    }

    public void setSong(String current_song) {
        this.song = current_song;
    }
}
