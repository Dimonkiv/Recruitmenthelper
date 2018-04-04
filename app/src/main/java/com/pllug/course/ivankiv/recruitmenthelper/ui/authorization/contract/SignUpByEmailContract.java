package com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.contract;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;


public interface SignUpByEmailContract {
    interface View {

        Uri getImageUri();

        ImageView getImageView();

        Context getContext();

        User getUser();

        void showToast(String message);
    }

    interface Presenter {

        void showPhoto();

        boolean checkingEnterData();
    }
}
