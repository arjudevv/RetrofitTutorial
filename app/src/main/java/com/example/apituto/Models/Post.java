package com.example.apituto.Models;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;

    private int id;

    private String title;

    @SerializedName("body")
    private String subject;

    public Post(int userId, String title, String subject) {
        this.userId = userId;
        this.title = title;
        this.subject = subject;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }
}
