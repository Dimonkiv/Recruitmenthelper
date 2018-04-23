package com.pllug.course.ivankiv.recruitmenthelper.ui.main.contactlist;

import android.util.Log;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.RecruiterNotesDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;

import java.util.List;

class ContactListPresenter implements ContactListContract.Presenter{
    private ContactListContract.View view;
    private AppDatabase db;
    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;

    ContactListPresenter(ContactListContract.View view) {
        this.view = view;
        initDao();
    }

    private void initDao() {
        db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
        recruiterNotesDao = db.recruiterNotesDao();
    }

    @Override
    public List<ContactListItem> getData() {
        List<ContactListItem> contactList = contactDao.getContactWithNotes();

        return contactList;
    }
}
