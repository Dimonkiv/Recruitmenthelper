package com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import java.util.List;

public interface PhoneContactContract {

    interface View {
        void showEditContactFragment(Contact contact);

        void showFilteredContacts(List<Contact> filteredContacts);
    }

    interface Presenter {

        List<Contact> getContacts();

        void filterContacts(String text);

        void onPhoneContactItemClick(Contact contact);
    }
}
