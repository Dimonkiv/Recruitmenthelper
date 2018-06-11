package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.List;

public interface LastConnectContract {

    interface View {

        /*---------------------------------------Show data----------------------------------------*/
        void showContacts(List<Contact> contacts);

        void showNoResultContainer();

        void showSearchContainer();

        void showRecyclerView();

        /*---------------------------------------Hide data----------------------------------------*/
        void hideNoResultContainer();

        void hideSearchContainer();

        void hideRecyclerView();

        void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeContent);
    }

    interface Presenter {
        void loadData();

        void filterContacts(String text);

        void onContactItemClick(long id, long recruiterNotesId);
    }
}
