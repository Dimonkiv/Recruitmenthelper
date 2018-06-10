package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;

import java.util.List;

interface ContactListContract {

    interface View {

        /*-------------------------------Show data------------------------------------------------*/
        void showContacts(List<ContactListItem> contacts);

        void showFilteredContacts(List<ContactListItem> filteredContacts);

        void showNoResultContainer();

        void showSearchContainer();

        void showRecyclerView();

        void showToast(String message);


        /*-------------------------------Hide data------------------------------------------------*/
        void hideNoResultContainer();

        void hideSearchContainer();

        void hideRecyclerView();



        void showSecondaryActivity(long id, long recruiterNotesId, String typeView);
    }

    interface Presenter {
        void filterContacts(String text);

        void loadContacts();

        /*-------------------------------Button click handlers------------------------------------*/
        void onContactsItemClick(long id, long recruiterNotesId);

        void onEditButtonClick(long id, long recruiterNotesId);

        void onDeleteButtonClick(long id, long recruiterNotesId);
    }
}
