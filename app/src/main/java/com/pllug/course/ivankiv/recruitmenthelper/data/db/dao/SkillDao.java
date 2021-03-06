package com.pllug.course.ivankiv.recruitmenthelper.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.SkillForExpandedSearch;

import java.util.List;

@Dao
public interface SkillDao {
    @Query("SELECT * FROM Skill")
    List<Skill> getAll();

    @Query("SELECT * FROM Skill WHERE id = :id")
    Skill getById(long id);

    @Query("SELECT * FROM Skill WHERE recruiterNotesId = :recruiterNotesId")
    List<Skill> getAllByRecruiterNotesId(long recruiterNotesId);

    @Query("SELECT Contact.id, Contact.name, Contact.photoUri, Skill.skill, Skill.recruiterNotesId FROM Skill, Contact WHERE Contact.recruiterNotesId == Skill.recruiterNotesId")
    List<SkillForExpandedSearch> getAllSkillForSearch();

    @Insert
    long insert(Skill skill);

    @Update
    void update(Skill skill);

    @Delete
    void delete(Skill skill);
}
