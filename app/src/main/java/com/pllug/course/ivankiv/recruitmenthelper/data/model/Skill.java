package com.pllug.course.ivankiv.recruitmenthelper.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Skill {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String skill;

    private long recruiterNotesId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public long getRecruiterNotesId() {
        return recruiterNotesId;
    }

    public void setRecruiterNotesId(long recruiterNotesId) {
        this.recruiterNotesId = recruiterNotesId;
    }
}
