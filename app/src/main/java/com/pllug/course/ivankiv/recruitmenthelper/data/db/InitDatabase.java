package com.pllug.course.ivankiv.recruitmenthelper.data.db;

import android.app.Application;
import android.arch.persistence.room.Room;

public class InitDatabase extends Application {
    public static InitDatabase instance;
    private AppDatabase databese;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        databese = Room.databaseBuilder(this, AppDatabase.class, "recruitment_database")
                .allowMainThreadQueries()
                .build();
    }

    public static InitDatabase getInstance() {
        return instance;
    }

    public AppDatabase getDatabese() {
        return databese;
    }
}
