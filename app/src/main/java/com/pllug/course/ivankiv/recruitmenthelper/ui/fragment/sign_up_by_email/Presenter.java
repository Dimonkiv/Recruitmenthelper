package com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.sign_up_by_email;



import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.UserDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;
import com.pllug.course.ivankiv.recruitmenthelper.utils.CircularTransformation;
import com.squareup.picasso.Picasso;

class Presenter implements Contract.Presenter {
    private Contract.View view;
    private User user;
    private AppDatabase db;
    private UserDao userDao;


    public Presenter(Contract.View view, AppDatabase db) {
        this.view = view;
        this.db = db;
        userDao = db.userDao();
    }


    @Override
    public void showPhoto() {
        //Показуємо зображення
        Picasso.with(view.getContext())
                .load(view.getImageUri())
                .transform(new CircularTransformation(600))
                .into(view.getImageView());
    }

    @Override
    public boolean checkingEnterData() {
        boolean success = false;
        //Отримуємо введені дані від view
        user = view.getUser();

        //Якщо не всі поля є заповнені
        if (isEmptyData()) {
            view.showToast("Заповніть всі поля!");
        } else {
            //Інакше, якщо всі заповнені
            //то перевіряємо чи введені паролі співпадають
            if (isEqualsPasswords()) {
                //Якщо співпаать
                //То заносимо дані в базу даних
                userDao.insert(user);
                success = true;
            }
        }
        return success;
    }

    //Метод, що перевіряє чи всі поля є заповнені
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

    //Метод, який перевіряє чи введені поля є заповнені
    private boolean isEqualsPasswords() {

        if (user.getPassword().equals(user.getReenterPassword())) {
            return true;
        }

        return false;
    }
}
