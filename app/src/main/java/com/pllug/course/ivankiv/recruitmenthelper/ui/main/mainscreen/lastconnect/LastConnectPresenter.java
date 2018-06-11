package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class LastConnectPresenter implements LastConnectContract.Presenter {
    private LastConnectContract.View view;
    private ContactDao contactDao;
    private List<Contact> contacts;

    LastConnectPresenter(LastConnectContract.View view) {
        this.view = view;
        initDao();
    }

    private void initDao() {
        AppDatabase db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
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

    //Method, which load data from db
    @Override
    public void loadData() {
        contacts = contactDao.getAllByDate();
        showInitialData();
    }

    //Method, which filter contacts fro search
    @Override
    public void filterContacts(String text) {
        List<Contact> filteredContacts = new ArrayList<>();

        for (Contact contactItem : contacts) {
            if (contactItem.getDateOfLatestContact().toLowerCase().contains(text.toLowerCase())) {
                filteredContacts.add(contactItem);
            }
        }

        view.showContacts(filteredContacts);
    }

    //Click handler
    @Override
    public void onContactItemClick(long id, long recruiterNotesId) {
        view.sendDataToSecondaryActivity(id, recruiterNotesId, "LastConnect");
    }


}
