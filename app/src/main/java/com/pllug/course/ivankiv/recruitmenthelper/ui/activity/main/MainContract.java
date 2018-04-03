package com.pllug.course.ivankiv.recruitmenthelper.ui.activity.main;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;

interface MainContract {
    interface View {

    }

    interface Presenter {
        User getUser();
    }
}
