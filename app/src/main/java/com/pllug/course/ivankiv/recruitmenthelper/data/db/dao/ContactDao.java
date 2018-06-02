package com.pllug.course.ivankiv.recruitmenthelper.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE selected = :isSelected")
    List<Contact> getAllBySelected(boolean isSelected);

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact getById(long id);

    @Query("SELECT * FROM Contact WHERE dateOfLatestContact IS NOT NULL ORDER BY dateOfLatestContact DESC")
    List<Contact> getAllByDate();


    @Query("SELECT * FROM Contact WHERE recruiterNotesId = :recruiterNotesId")
    Contact getByRecruiterId(long recruiterNotesId);

    @Query("SELECT Contact.id, Contact.name, Contact.photoUri, Contact.recruiterNotesId,RecruiterNotes.typeOfEmployment ,RecruiterNotes.profession, RecruiterNotes.jobInterests " +
            "FROM Contact, RecruiterNotes WHERE recruiterNotes.id == Contact.recruiterNotesId")
    List<ContactListItem> getContactWithNotes();

    @Insert
    long insert(Contact contact);

    @Update
    int update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("UPDATE Contact SET selected = :state WHERE id = :id")
    void updateSelected(boolean state, long id);
}
