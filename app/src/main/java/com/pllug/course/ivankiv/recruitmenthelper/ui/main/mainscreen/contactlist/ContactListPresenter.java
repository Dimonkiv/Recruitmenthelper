package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.LanguageDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.RecruiterNotesDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.SkillDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;

import java.util.List;

public class ContactListPresenter implements ContactListContract.Presenter{
    private ContactListContract.View view;
    private AppDatabase db;
    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;
    private LanguageDao languageDao;
    private SkillDao skillDao;


    ContactListPresenter(ContactListContract.View view) {
        this.view = view;
        initDao();
    }

    private void initDao() {
        db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
        recruiterNotesDao = db.recruiterNotesDao();
        languageDao = db.languageDao();
        skillDao = db.skillDao();
    }

    public void setDataFromAdapter(long id, long recruiterNotesId, String typeView) {
        view.sendDataToSecondaryActivity(id, recruiterNotesId, typeView);
    }

    public void deleteContact(long id, long recruiterNotesId) {
        contactDao.delete(contactDao.getById(id));
        recruiterNotesDao.delete(recruiterNotesDao.getById(recruiterNotesId));
        for (Language language : languageDao.getAllByRecruiterNotesId(recruiterNotesId)) {
            languageDao.delete(language);
        }

        for (Skill skill: skillDao.getAllByRecruiterNotesId(recruiterNotesId)) {
            skillDao.delete(skill);
        }

    }

    @Override
    public List<ContactListItem> getData() {
        List<ContactListItem> contactList = contactDao.getContactWithNotes();

        return contactList;
    }


}
