package com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact;

import android.content.ContentResolver;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.List;

public interface PhoneContactContract {

    interface View {
        void sendDataToMainActivity(Contact contact);
    }

    interface Presenter {
        List<Contact> getContacts();
    }
}
