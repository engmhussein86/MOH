package com.moh.app.models;

/**
 * Created by pc-3 on 7/24/2016.
 */
public class NewsClass {
    String post_date, guid, post_title, post_content, image_url;
    int id;

    public NewsClass() {
    }

    public NewsClass(int id, String post_date, String guid, String post_title, String post_content, String image_url) {
        this.id = id;
        this.post_date = post_date;
        this.guid = guid;
        this.post_title = post_title;
        this.post_content = post_content;
        this.image_url = image_url;
        //Log.e("TEST","MMM");
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_content() {
        return post_content.trim();
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
