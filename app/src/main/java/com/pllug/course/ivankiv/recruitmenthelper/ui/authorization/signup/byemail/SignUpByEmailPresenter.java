package com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.signup.byemail;



import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.UserDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;


public class SignUpByEmailPresenter implements SignUpByEmailContract.Presenter {
    private SignUpByEmailContract.View view;
    private User user;
    private AppDatabase db;
    private UserDao userDao;


    SignUpByEmailPresenter(SignUpByEmailContract.View view) {
        this.view = view;
        initDatabase();

    }

    //Initialization db and getting methods for work with user table
    private void initDatabase() {
        db = InitDatabase.getInstance().getDatabese();
        userDao = db.userDao();
    }

    //Method for checking entered data
    @Override
    public boolean checkingEnterData() {
        boolean success = false;
        //Get entered data from View
        user = view.getUser();

        //If even one field is empty
        if (isEmptyData()) {
            view.showToast("Заповніть всі поля!");
        } else {
            //If password and reenter password is equal
            if (isEqualsPasswords()) {
                //Put user to db
                userDao.insert(user);
                success = true;
            }
        }
        return success;
    }

    //Method which checking Are the fields empty
    private boolean isEmptyData() {
        if (user.getName().isEmpty()) {
            return true;
        }

        if (user.getLastname().isEmpty()) {
            return true;
        }
        if (user.getEmail().isEmpty()) {
            return true;
        }
        if (user.getPhone().isEmpty()) {
            return true;
        }
        if (user.getPassword().isEmpty()) {
            return true;
        }
        if (user.getReenterPassword().isEmpty()) {
            return true;
        }

        return false;
    }

    //Method which checking Are the password fields equal
    private boolean isEqualsPasswords() {
        if (user.getPassword().equals(user.getReenterPassword())) {
            return true;
        }

        return false;
    }
}
