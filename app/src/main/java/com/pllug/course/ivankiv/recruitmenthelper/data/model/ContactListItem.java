package com.pllug.course.ivankiv.recruitmenthelper.data.model;

public class ContactListItem {

    private long id;

    private String name;

    private String photoUri;

    private long recruiterNotesId;

    private String profession;

    private String workInterests;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getWorkInterests() {
        return workInterests;
    }

    public void setWorkInterests(String workInterests) {
        this.workInterests = workInterests;
    }

    public long getRecruiterNotesId() {
        return recruiterNotesId;
    }

    public void setRecruiterNotesId(long recruitNotesId) {
        this.recruiterNotesId = recruitNotesId;
    }
}
