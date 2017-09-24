package com.theengineerscafe.nativeadsdemo;

/**
 * Created by tecstaff on 10/09/17.
 */

public class User {

    String name, details;
    int imageId;

    public User() {
    }

    public User(String name, String details, int imageId) {
        this.name = name;
        this.details = details;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
