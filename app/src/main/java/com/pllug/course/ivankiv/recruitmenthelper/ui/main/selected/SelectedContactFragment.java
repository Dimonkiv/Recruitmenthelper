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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected.adapter.SelectedContactAdapter;

import java.util.List;

public class SelectedContactFragment extends Fragment implements SelectedContactContract.View {
    private View root;
    private SelectedContactPresenter presenter;
    private SelectedContactAdapter adapter;

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

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.loadContacts();
    }

    /*--------------------------------------Initialization----------------------------------------*/
    private void initView() {
        recyclerView = root.findViewById(R.id.selected_contact_recycler);
        searchEdit = root.findViewById(R.id.selected_contact_search_edit);
        search_container = root.findViewById(R.id.selected_contact_search_container);
        no_result_container = root.findViewById(R.id.selected_contact_no_result_container);
    }

    private void initPresenter() {
        presenter = new SelectedContactPresenter(this);
    }

    private void initAdapter() {
        adapter = new SelectedContactAdapter(getActivity(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

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
                presenter.filterContacts(editable.toString());
            }
        });
    }


    /*---------------------------------------Show data--------------------------------------------*/
    @Override
    public void showContacts(List<Contact> contacts) {
        adapter.addAllContacts(contacts);
    }

    @Override
    public void showNoResultContainer() {
        no_result_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchContainer() {
        search_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    /*---------------------------------------Hide data--------------------------------------------*/
    @Override
    public void hideNoResultContainer() {
        no_result_container.setVisibility(View.GONE);
    }

    @Override
    public void hideSearchContainer() {
        search_container.setVisibility(View.GONE);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }


    @Override
    public void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeView) {
        ((MainActivity)getActivity()).goToSecondaryActivity(id, recruiterNotesId, typeView);
    }
}
