package com.pllug.course.ivankiv.recruitmenthelper.ui.main.contactlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.contactlist.adapter.ContactListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment implements ContactListContract.View {
    private View root;
    private ContactListPresenter presenter;
    private List<ContactListItem> contacts;
    private ContactListAdapter adapter;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_contact_list, container, false);

        initView();
        initAdapter();
        initPresenter();
        showContactList();

        return root;
    }

    private void initView() {
        recyclerView = root.findViewById(R.id.contact_list_container);
    }

    private void initAdapter() {
        contacts = new ArrayList<>();

        adapter = new ContactListAdapter(getActivity(), contacts);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initPresenter() {
        presenter = new ContactListPresenter(this);
    }

    private void showContactList() {
        contacts.addAll(presenter.getData());
        adapter.notifyDataSetChanged();
    }
}
