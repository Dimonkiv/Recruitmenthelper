package com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.List;

public interface SelectedContactContract {

    interface View {
        /*---------------------------------------Show data----------------------------------------*/
        void showContacts(List<Contact> contacts);

        void showNoResultContainer();

        void showSearchContainer();

        void showRecyclerView();

        void showToast(String message);

        /*---------------------------------------Hide data----------------------------------------*/
        void hideNoResultContainer();

        void hideSearchContainer();

        void hideRecyclerView();

        void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeView);
    }

    interface Presenter {

        void loadContacts();

        void filterContacts(String text);

        void onStarButtonClick(Contact contact);

        void onContactItemClick(long id, long recruiterNotesId);

    }
}
