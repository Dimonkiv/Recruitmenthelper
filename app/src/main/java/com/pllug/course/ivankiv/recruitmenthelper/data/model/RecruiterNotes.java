package com.pllug.course.ivankiv.recruitmenthelper.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RecruiterNotes {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String typeOfEmployment;

    private String profession;

    private String position;

    private String jobOrUniversity;

    private String workInterests;

    private String experience;

    private String advantages;

    private String disadvantages;

    private String notes;

    private String language;

    private String skill;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobOrUniversity() {
        return jobOrUniversity;
    }

    public void setJobOrUniversity(String jobOrUniversity) {
        this.jobOrUniversity = jobOrUniversity;
    }

    public String getWorkInterests() {
        return workInterests;
    }

    public void setWorkInterests(String workInterests) {
        this.workInterests = workInterests;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAdvantages() {
        return advantages;
    }

    public void setAdvantages(String advantages) {
        this.advantages = advantages;
    }

    public String getDisadvantages() {
        return disadvantages;
    }

    public void setDisadvantages(String disadvantages) {
        this.disadvantages = disadvantages;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
