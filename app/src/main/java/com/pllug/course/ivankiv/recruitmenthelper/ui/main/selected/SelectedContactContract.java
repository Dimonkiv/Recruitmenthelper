package com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.List;

public interface SelectedContactContract {

    interface View {
        void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeView);
    }

    interface Presenter {
        List<Contact> getContact();
    }
}
