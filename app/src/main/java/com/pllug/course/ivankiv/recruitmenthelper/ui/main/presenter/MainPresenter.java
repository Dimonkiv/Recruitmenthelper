package com.pllug.course.ivankiv.recruitmenthelper.ui.main.presenter;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.UserDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.contract.MainContract;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private AppDatabase db;
    private UserDao userDao;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        initDB();
    }

    //Метод, для ініціалізації бази даних
    private void initDB() {
        db = InitDatabase.getInstance().getDatabese();
        userDao = db.userDao();
    }

    @Override
    public User getUser() {
        return userDao.getById(1);
    }
}
