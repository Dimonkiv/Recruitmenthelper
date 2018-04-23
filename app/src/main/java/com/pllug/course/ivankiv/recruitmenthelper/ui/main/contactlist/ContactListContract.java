package com.pllug.course.ivankiv.recruitmenthelper.ui.main.contactlist;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;

import java.util.List;

interface ContactListContract {

    interface View {

    }

    interface Presenter {
        List<ContactListItem> getData();
    }
}
