package com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class SelectedContactPresenter implements SelectedContactContract.Presenter {
    private SelectedContactContract.View view;
    private List<Contact> contacts;
    private ContactDao contactDao;

    SelectedContactPresenter(SelectedContactContract.View view) {
        this.view = view;
        initDao();
    }

    private void initDao() {
        AppDatabase db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
    }

    //Method, which show initial data in view
    private void showInitialData() {
        if (contacts.isEmpty()) {
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

    //Method, which load data from db
    @Override
    public void loadContacts() {
        contacts = contactDao.getAllBySelected(true);
        showInitialData();
    }

    //Method, which filter contacts for search
    @Override
    public void filterContacts(String text) {
        List<Contact> filteredContacts = new ArrayList<>();

        for (Contact contactItem : contacts) {
            if (contactItem.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredContacts.add(contactItem);
            }
        }

        view.showContacts(filteredContacts);
    }


    /*-------------------------------Button click handlers----------------------------------------*/
    @Override
    public void onStarButtonClick(Contact contact) {
        contactDao.update(contact);
        view.showToast("Контакт видалено з обраних!");
    }

    @Override
    public void onContactItemClick(long id, long recruiterNotesId) {
        view.sendDataToSecondaryActivity(id, recruiterNotesId, "SelectedContact");
    }


}
