package com.pllug.course.ivankiv.recruitmenthelper.ui.main.addcontact;

import android.content.ContentResolver;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.List;

public interface  AddContactContract {

    interface View {
        ContentResolver getContentResolver();
    }

    interface Presenter {
        List<Contact> getContacts();
    }
}
