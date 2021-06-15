package com.wikimedia.assigment.data;

public class Imageinfo {
    private String timestamp;
    private String user;
    private String ImageUrl;

    public Imageinfo(String timestamp, String user, String imageUrl) {
        this.timestamp = timestamp;
        this.user = user;
        ImageUrl = imageUrl;
    }

    // getters and setters

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
