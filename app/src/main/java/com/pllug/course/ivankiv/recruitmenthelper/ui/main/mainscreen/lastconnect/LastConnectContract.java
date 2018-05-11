package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.List;

public interface LastConnectContract {

    interface View {
        void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeContent);
    }

    interface Presenter {
        List<Contact> getContact();
    }
}
