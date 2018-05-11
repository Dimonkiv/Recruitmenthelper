package com.pllug.course.ivankiv.recruitmenthelper.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.LanguageDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.RecruiterNotesDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.SkillDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.UserDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;

@Database(entities = {User.class, Contact.class, RecruiterNotes.class, Language.class, Skill.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ContactDao contactDao();

    public abstract RecruiterNotesDao recruiterNotesDao();

    public abstract LanguageDao languageDao();

    public abstract SkillDao skillDao();
}
