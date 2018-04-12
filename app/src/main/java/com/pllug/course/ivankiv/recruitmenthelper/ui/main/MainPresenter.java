package com.pllug.course.ivankiv.recruitmenthelper.ui.main;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.UserDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private AppDatabase db;
    private UserDao userDao;

    MainPresenter(MainContract.View view) {
        this.view = view;
        initDB();
    }

    //Initialization database
    private void initDB() {
        db = InitDatabase.getInstance().getDatabese();
        userDao = db.userDao();
    }

    //Get user from db
    @Override
    public User getUser() {
        return userDao.getById(1);
    }
}
