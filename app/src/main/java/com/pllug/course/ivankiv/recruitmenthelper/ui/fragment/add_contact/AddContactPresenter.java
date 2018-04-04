package com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.add_contact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.PhoneContact;
import java.util.ArrayList;
import java.util.List;

class AddContactPresenter implements AddContactContract.Presenter {
    private AddContactContract.View view;
    private PhoneContact phoneContact;
    private List<PhoneContact> contacts;

    //Конструктор
    AddContactPresenter(AddContactContract.View view) {
        this.view = view;
    }

    //Метод, який повертає список контактів
    @Override
    public List<PhoneContact> getContacts() {
        loadContacts();
        return contacts;
    }

    //Метод, який підгружає контакти з телефонної книги
    private void loadContacts() {
        contacts = new ArrayList<>();
        String phone = "";

        //Отримуємо курсор з телефонної книги
        ContentResolver contentResolver = view.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                //Циклічно отримуємо дані про кожен контакт
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
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

                //Добавляємо дані про контакт в модель PhoneContact
                phoneContact = new PhoneContact();
                phoneContact.setName(name);
                phoneContact.setPhone(phone);
                phoneContact.setPhotoUri(photoUri);

                //Заносимо PhoneContact у список
                contacts.add(phoneContact);
            }
            cursor.close();
        }
    }


}
