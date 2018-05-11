package com.pllug.course.ivankiv.recruitmenthelper.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Language {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String language;

    private String languageLevel;

    private long recruiterNotesId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
    }

    public long getRecruiterNotesId() {
        return recruiterNotesId;
    }

    public void setRecruiterNotesId(long recruiterNotesId) {
        this.recruiterNotesId = recruiterNotesId;
    }
}
