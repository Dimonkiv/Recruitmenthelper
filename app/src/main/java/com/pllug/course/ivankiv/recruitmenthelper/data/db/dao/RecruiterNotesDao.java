package com.pllug.course.ivankiv.recruitmenthelper.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;

import java.util.List;

@Dao
public interface RecruiterNotesDao {

    @Query("SELECT * FROM RecruiterNotes")
    List<RecruiterNotes> getAll();

    @Query("SELECT * FROM RecruiterNotes WHERE id = :id")
    RecruiterNotes getById(long id);

    @Insert
    long insert(RecruiterNotes recruiterNotes);

    @Update
    void update(RecruiterNotes recruiterNotes);

    @Delete
    void delete(RecruiterNotes recruiterNotes);

}
