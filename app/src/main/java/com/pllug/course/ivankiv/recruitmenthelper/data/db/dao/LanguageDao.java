package com.pllug.course.ivankiv.recruitmenthelper.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;

import java.util.List;

@Dao
public interface LanguageDao {
    @Query("SELECT * FROM Language")
    List<Language> getAll();

    @Query("SELECT * FROM Language WHERE id = :id")
    Language getById(long id);

    @Query("SELECT * FROM Language WHERE recruiterNotesId = :recruiterNotesId")
    List<Language> getAllByRecruiterNotesId(long recruiterNotesId);

    @Delete
    void delete(Language language);

    @Insert
    void insert(Language language);

    @Update
    void update(Language language);
}
