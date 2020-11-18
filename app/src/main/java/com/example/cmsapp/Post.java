package com.example.cmsapp;

public class Post {
    private String postid;
    private String postImage;
    private String desription;
    private String publisher;

    public Post(String postid, String postImage, String desription, String publisher) {
        this.postid = postid;
        this.postImage = postImage;
        this.desription = desription;
        this.publisher = publisher;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
