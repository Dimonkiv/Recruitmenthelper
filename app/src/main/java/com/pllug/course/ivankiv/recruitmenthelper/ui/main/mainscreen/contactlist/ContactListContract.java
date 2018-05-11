package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;

import java.util.List;

interface ContactListContract {

    interface View {
        void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeView);
    }

    interface Presenter {
        List<ContactListItem> getData();
    }
}
