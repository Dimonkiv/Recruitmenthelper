package com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.contentprovider.PhoneContactLoader;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class PhoneContactPresenter implements PhoneContactContract.Presenter {
    private PhoneContactContract.View view;
    private PhoneContactLoader phoneContactLoader;
    private List<Contact> contacts;


    PhoneContactPresenter(PhoneContactContract.View view) {
        this.view = view;
        phoneContactLoader = new PhoneContactLoader();
    }


    @Override
    public List<Contact> getContacts() {
        contacts = phoneContactLoader.getContacts();
        return contacts;
    }

    //Method, which create filtered contact list
    //And send it to view for showing
    @Override
    public void filterContacts(String text) {
        List<Contact> filteredContacts = new ArrayList<>();

        for (Contact contactItem : contacts) {
            if (contactItem.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredContacts.add(contactItem);
            }
        }

        view.showFilteredContacts(filteredContacts);
    }

    @Override
    public void onPhoneContactItemClick(Contact contact) {
        view.showEditContactFragment(contact);
    }
}
