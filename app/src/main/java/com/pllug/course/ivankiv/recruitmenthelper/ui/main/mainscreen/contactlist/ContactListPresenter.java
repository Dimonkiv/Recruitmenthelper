package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.LanguageDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.RecruiterNotesDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.SkillDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class ContactListPresenter implements ContactListContract.Presenter{
    private ContactListContract.View view;

    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;
    private LanguageDao languageDao;
    private SkillDao skillDao;
    private List<ContactListItem> contacts;


    ContactListPresenter(ContactListContract.View view) {
        this.view = view;
        initDao();
    }

    //Initialization database
    private void initDao() {
        AppDatabase db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
        recruiterNotesDao = db.recruiterNotesDao();
        languageDao = db.languageDao();
        skillDao = db.skillDao();
    }

    //Method, which load contacts from db
    @Override
    public void loadContacts() {
        contacts = contactDao.getContactWithNotes();
        showInitialData();
    }

    //Method, which show initial data in view
    private void showInitialData() {
        if (contacts == null || contacts.isEmpty()) {
            view.showNoResultContainer();
            view.hideSearchContainer();
            view.hideRecyclerView();
        } else {
            view.hideNoResultContainer();
            view.showSearchContainer();
            view.showRecyclerView();
            view.showContacts(contacts);
        }
    }

    //Method, which filters list of contact for search
    @Override
    public void filterContacts(String text) {
        List<ContactListItem> filteredContacts = new ArrayList<>();

        for (ContactListItem contactItem : contacts) {
            if (contactItem.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredContacts.add(contactItem);
            }
        }

        view.showFilteredContacts(filteredContacts);
    }


    /*---------------------------------Button listener handlers-----------------------------------*/
    @Override
    public void onContactsItemClick(long id, long recruiterNotesId) {
        view.showSecondaryActivity(id, recruiterNotesId, "ContactList");
    }

    @Override
    public void onEditButtonClick(long id, long recruiterNotesId) {
        view.showSecondaryActivity(id, recruiterNotesId, "ContactListEditBtn");
    }

    @Override
    public void onDeleteButtonClick(long id, long recruiterNotesId) {
        contactDao.delete(contactDao.getById(id));
        recruiterNotesDao.delete(recruiterNotesDao.getById(recruiterNotesId));

        for (Language language : languageDao.getAllByRecruiterNotesId(recruiterNotesId)) {
            languageDao.delete(language);
        }

        for (Skill skill: skillDao.getAllByRecruiterNotesId(recruiterNotesId)) {
            skillDao.delete(skill);
        }

        view.showToast("Контакт видалено!");
    }
}
