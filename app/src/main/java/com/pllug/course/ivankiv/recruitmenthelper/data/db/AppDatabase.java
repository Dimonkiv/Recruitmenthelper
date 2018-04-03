package com.pllug.course.ivankiv.recruitmenthelper.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.UserDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
