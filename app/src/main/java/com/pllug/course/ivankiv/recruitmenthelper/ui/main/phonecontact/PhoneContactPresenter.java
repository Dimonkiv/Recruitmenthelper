package com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.contentprovider.PhoneContactLoader;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.List;

public class PhoneContactPresenter implements PhoneContactContract.Presenter {
    private PhoneContactContract.View view;
    private PhoneContactLoader phoneContactLoader;
    private Contact contact;
    private List<Contact> contacts;

    //Конструктор
    PhoneContactPresenter(PhoneContactContract.View view) {
        this.view = view;
        phoneContactLoader = new PhoneContactLoader();
    }

    //Метод, який повертає список контактів
    @Override
    public List<Contact> getContacts() {
        return phoneContactLoader.getContacts();
    }

    public void setContact(Contact contact) {
        view.sendDataToMainActivity(contact);
    }

}
