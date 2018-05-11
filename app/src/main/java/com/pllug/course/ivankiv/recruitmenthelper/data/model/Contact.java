package com.pllug.course.ivankiv.recruitmenthelper.data.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String phone;

    private String email;

    private String linkedInLink;

    private String dateOfLatestContact;

    private String photoUri;

    private long recruiterNotesId;

    private boolean selected;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public String getDateOfLatestContact() {
        return dateOfLatestContact;
    }

    public void setDateOfLatestContact(String dateOfLatestContact) {
        this.dateOfLatestContact = dateOfLatestContact;
    }

    public long getRecruiterNotesId() {
        return recruiterNotesId;
    }

    public void setRecruiterNotesId(long recruiterNotesId) {
        this.recruiterNotesId = recruiterNotesId;
    }

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
