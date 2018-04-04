package com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.add_contact;

import android.content.ContentResolver;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.PhoneContact;

import java.util.List;

interface  AddContactContract {

    interface View {
        ContentResolver getContentResolver();
    }

    interface Presenter {
        List<PhoneContact> getContacts();
    }
}
