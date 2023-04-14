package com.silverbullet.anotes.core.model;

import java.io.Serializable;

public class Note implements Serializable {

    int id;
    String title;
    String body;
    String date;
    boolean pinned;

    public Note(int id, String title, String body, String date, boolean pinned){
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.pinned  = pinned;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public boolean isPinned() {
        return pinned;
    }
}
