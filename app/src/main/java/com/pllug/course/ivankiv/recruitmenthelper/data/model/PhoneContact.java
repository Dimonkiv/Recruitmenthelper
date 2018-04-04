package com.pllug.course.ivankiv.recruitmenthelper.data.model;

import java.io.Serializable;

public class PhoneContact implements Serializable {
    private String name;

    private String phone;

    private String photoUri;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

}
