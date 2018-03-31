package com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.sign_up_by_email;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;


interface Contract {
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
