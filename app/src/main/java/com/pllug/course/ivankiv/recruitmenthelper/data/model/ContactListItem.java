package com.pllug.course.ivankiv.recruitmenthelper.data.model;

public class ContactListItem {

    private long id;

    private String name;

    private String photoUri;

    private long recruiterNotesId;

    private String typeOfEmployment;

    private String profession;

    private String jobInterests;



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

    public String getTypeOfEmployment() {
        return typeOfEmployment;
    }

    public void setTypeOfEmployment(String typeOfEmployment) {
        this.typeOfEmployment = typeOfEmployment;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getJobInterests() {
        return jobInterests;
    }

    public void setJobInterests(String jobInterests) {
        this.jobInterests = jobInterests;
    }

    public long getRecruiterNotesId() {
        return recruiterNotesId;
    }

    public void setRecruiterNotesId(long recruitNotesId) {
        this.recruiterNotesId = recruitNotesId;
    }
}
