package com.pllug.course.ivankiv.recruitmenthelper.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.RecruiterNotesDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.UserDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;

@Database(entities = {User.class, Contact.class, RecruiterNotes.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ContactDao contactDao();

    public abstract RecruiterNotesDao recruiterNotesDao();
}
