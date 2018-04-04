package com.pllug.course.ivankiv.recruitmenthelper.ui.main.contract;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;

public interface MainContract {
    interface View {

    }

    interface Presenter {
        User getUser();
    }
}
