package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.RecruiterNotesDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;

public class EditContactPresenter implements EditContactContract.Presenter {

    private EditContactContract.View view;
    private AppDatabase db;
    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;


    EditContactPresenter(EditContactContract.View view) {
        this.view = view;
        initDao();
    }

    private void initDao() {
        db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
        recruiterNotesDao = db.recruiterNotesDao();
    }

    @Override
    public Contact getContact(long id) {
        return contactDao.getById(id);
    }

    @Override
    public RecruiterNotes getRecruiterNote(long recruiterNotesId) {
        return recruiterNotesDao.getById(recruiterNotesId);
    }

    @Override
    public boolean checkedEnteredData() {
        Contact contact = view.getContact();
        RecruiterNotes recruiterNotes = view.getRecruiterNotes();

        long id = insertRecruiterNotes(recruiterNotes);

        insertContact(contact, id);

        return true;
    }

    private long insertRecruiterNotes(RecruiterNotes recruiterNotes) {
        return recruiterNotesDao.insert(recruiterNotes);
    }

    private void insertContact(Contact contact, long id) {
        contact.setRecruiterNotesId(id);
        contactDao.insert(contact);
    }
}
