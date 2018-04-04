package com.pllug.course.ivankiv.recruitmenthelper.ui.main.contract;

import android.content.ContentResolver;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.PhoneContact;

import java.util.List;

public interface  AddContactContract {

    interface View {
        ContentResolver getContentResolver();
    }

    interface Presenter {
        List<PhoneContact> getContacts();
    }
}
