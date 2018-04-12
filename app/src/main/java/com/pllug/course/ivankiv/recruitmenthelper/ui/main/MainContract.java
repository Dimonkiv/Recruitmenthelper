package com.pllug.course.ivankiv.recruitmenthelper.ui.main;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;

public interface MainContract {
    interface View {

    }

    interface Presenter {
        User getUser();
    }
}
