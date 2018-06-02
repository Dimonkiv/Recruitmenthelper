package com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected;

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
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected.adapter.SelectedContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectedContactFragment extends Fragment implements SelectedContactContract.View {
    private View root;
    private SelectedContactPresenter presenter;
    private SelectedContactAdapter adapter;

    private List<Contact> contacts;

    //View
    private RecyclerView recyclerView;
    private EditText searchEdit;
    private RelativeLayout no_result_container;
    private LinearLayout search_container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_selected_contact, container, false);

        initView();
        initEditSearchListener();
        initPresenter();
        initAdapter();
        getDataFromPresenter();
        return root;
    }


    //InitializationView
    private void initView() {
        recyclerView = root.findViewById(R.id.selected_contact_recycler);
        searchEdit = root.findViewById(R.id.selected_contact_search_edit);
        search_container = root.findViewById(R.id.selected_contact_search_container);
        no_result_container = root.findViewById(R.id.selected_contact_no_result_container);
    }

    //Initialization Presenter
    private void initPresenter() {
        presenter = new SelectedContactPresenter(this);
    }

    //initialization adapter
    private void initAdapter() {
        contacts = new ArrayList<>();
        adapter = new SelectedContactAdapter(getActivity(), contacts, presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //Method, which get Contact from presenter
    private void getDataFromPresenter() {
        contacts.addAll(presenter.getContact());
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
        List<Contact> filteredContacts = new ArrayList<>();

        for (Contact contactItem : contacts) {
            if (contactItem.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredContacts.add(contactItem);
            }
        }

        adapter.filterList(filteredContacts);
    }


    @Override
    public void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeView) {
        ((MainActivity)getActivity()).goToSecondaryActivity(id, recruiterNotesId, typeView);
    }
}
