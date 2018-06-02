package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist.adapter.ContactListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment implements ContactListContract.View {
    private View root;
    private ContactListPresenter presenter;
    private List<ContactListItem> contacts;
    private ContactListAdapter adapter;

    //View
    private RecyclerView recyclerView;
    private EditText searchEdit;
    private RelativeLayout no_result_container;
    private LinearLayout search_container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_contact_list, container, false);

        initView();
        initEditSearchListener();
        initPresenter();
        initAdapter();
        showContactList();

        return root;
    }

    //Initialization view
    private void initView() {
        recyclerView = root.findViewById(R.id.contact_list_container);
        searchEdit = root.findViewById(R.id.contact_list_search_edit);
        search_container = root.findViewById(R.id.contact_list_search_container);
        no_result_container = root.findViewById(R.id.contact_list_no_result_container);
    }

    //Initialization adapter
    private void initAdapter() {
        contacts = new ArrayList<>();

        adapter = new ContactListAdapter(getActivity(), contacts, presenter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new ContactListPresenter(this);
    }

    //Method, which show contact list
    private void showContactList() {
        contacts.addAll(presenter.getData());

        if (contacts.isEmpty()) {
            no_result_container.setVisibility(View.VISIBLE);
            search_container.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        } else {
            no_result_container.setVisibility(View.GONE);
            search_container.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
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

    //Method, which checking coincidence between entered text and name in contact list
    private void filter(String text) {
        List<ContactListItem> filteredContacts = new ArrayList<>();

        for (ContactListItem contactItem : contacts) {
            if (contactItem.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredContacts.add(contactItem);
            }
        }

        adapter.filterList(filteredContacts);
    }

    //Method, which open Secondary Activity and sent data to him
    @Override
    public void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeView) {
        ((MainActivity)getActivity()).goToSecondaryActivity(id, recruiterNotesId, typeView);
    }
}
