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

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact getById(long id);

    @Query("SELECT * FROM Contact WHERE recruiterNotesId = :recruiterNotesId")
    Contact getByRecruiterId(long recruiterNotesId);

    @Query("SELECT Contact.id, Contact.name, Contact.photoUri, Contact.recruiterNotesId, RecruiterNotes.profession, RecruiterNotes.workInterests " +
            "FROM Contact, RecruiterNotes WHERE recruiterNotes.id == Contact.recruiterNotesId")
    List<ContactListItem> getContactWithNotes();

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}
