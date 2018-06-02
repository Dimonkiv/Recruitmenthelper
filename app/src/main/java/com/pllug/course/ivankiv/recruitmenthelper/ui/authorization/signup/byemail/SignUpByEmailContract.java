package com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.signup.byemail;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;


public interface SignUpByEmailContract {
    interface View {

        Context getContext();

        User getUser();

        void showToast(String message);
    }

    interface Presenter {

        boolean checkingEnterData();
    }
}
