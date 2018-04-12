package com.pllug.course.ivankiv.recruitmenthelper.ui.main.addcontact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.pllug.course.ivankiv.recruitmenthelper.data.contentprovider.PhoneContactLoader;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class AddContactPresenter implements AddContactContract.Presenter {
    private AddContactContract.View view;
    private PhoneContactLoader phoneContactLoader;
    private Contact contact;
    private List<Contact> contacts;

    //Конструктор
    public AddContactPresenter(AddContactContract.View view) {
        this.view = view;
        phoneContactLoader = new PhoneContactLoader();
    }

    //Метод, який повертає список контактів
    @Override
    public List<Contact> getContacts() {
        return phoneContactLoader.getContacts();
    }

    //Метод, який підгружає контакти з телефонної книги
    private void loadContacts() {

    }


}
