package com.pllug.course.ivankiv.recruitmenthelper.data.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhoneContactLoader {
    private Contact contact;
    private List<Contact> contacts;

    private void loadContacts() {
        contacts = new ArrayList<>();
        String phone = "";

        //Отримуємо курсор з телефонної книги
        ContentResolver contentResolver = ContentResolverLib.ContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                //Циклічно отримуємо дані про кожен контакт
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                Long dateL = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts.LAST_TIME_CONTACTED));
                Date df = new java.util.Date(dateL);
                String date = new SimpleDateFormat("dd.MM.yyyy").format(df);
                Integer hasPhone = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));



                //Якщо є один чи більше номер телефон
                if (hasPhone > 0) {
                    Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    while (phones.moveToNext()) {
                        phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phones.close();
                }

                //Добавляємо дані про контакт в модель Contact
                contact = new Contact();
                contact.setName(name);
                contact.setPhone(phone);
                contact.setPhotoUri(photoUri);
                contact.setDateOfLatestContact(date);

                //Заносимо Contact у список
                contacts.add(contact);
            }
            cursor.close();
        }

    }

    public List<Contact> getContacts() {
        loadContacts();
        return contacts;
    }
}
