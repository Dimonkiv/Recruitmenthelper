package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect.adapter.LastConnectAdapter;

import java.util.ArrayList;
import java.util.List;


public class LastConnectFragment extends Fragment implements LastConnectContract.View{
    View root;
    private List<Contact> contacts;
    private LastConnectPresenter presenter;
    private LastConnectAdapter adapter;

    //View
    private RecyclerView recyclerView;
    private EditText searchEdit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_last_connect, container, false);

        initView();
        initEditSearchListener();
        initPresenter();
        initAdapter();
        getDataFromPresenter();


        return root;
    }



    //InitializationView
    private void initView() {
        recyclerView = root.findViewById(R.id.last_connect_recycler);
        searchEdit = root.findViewById(R.id.last_connect_search_edit);
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new LastConnectPresenter(this);
    }

    //Initialization adapter
    private void initAdapter() {
        contacts = new ArrayList<>();
        adapter = new LastConnectAdapter(contacts, getActivity(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //Method, which get contact list from presenter
    private void getDataFromPresenter() {
        contacts.addAll(presenter.getContact());
        adapter.notifyDataSetChanged();
    }

    //Initialization TextChangeListener for search edit text
    private void initEditSearchListener() {
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    //Method, which checking coincidence between entered text and date in contact list
    private void filter(String text) {
        List<Contact> filteredContacts = new ArrayList<>();

        for (Contact contactItem : contacts) {
            if (contactItem.getDateOfLatestContact().toLowerCase().contains(text.toLowerCase())) {
                filteredContacts.add(contactItem);
            }
        }

        adapter.filterList(filteredContacts);
    }

    @Override
    public void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeContent) {
        ((MainActivity)getActivity()).goToSecondaryActivity(id, recruiterNotesId, typeContent);
    }
}
