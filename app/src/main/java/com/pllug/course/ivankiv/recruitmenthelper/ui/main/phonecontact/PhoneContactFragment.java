package com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact.adapter.PhoneContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhoneContactFragment extends Fragment implements PhoneContactContract.View {
    private View root;
    private PhoneContactPresenter presenter;
    private List<Contact> contacts;
    private PhoneContactAdapter adapter;
    private ConatctAsynkTask mTask;

    //View
    private RecyclerView listForContact;
    private EditText searchEdit;
    private ProgressBar progress;

    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static boolean READ_CONTACTS_GRANTED = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_phone_contact, container, false);

        initView();
        initEditSearchListener();
        initPresenter();
        initAdapter();
        initAsynkTask();
        setPermission();

        return root;
    }


    //Initialization View
    private void initView() {
        listForContact = root.findViewById(R.id.add_contact_recycler_view);
        progress = root.findViewById(R.id.add_contact_progress_bar);
        searchEdit = root.findViewById(R.id.phone_contact_search_edit);
    }

    //Initialization Presenter
    private void initPresenter() {
        presenter = new PhoneContactPresenter(this);
    }

    //Initialization Adapter
    private void initAdapter() {
        contacts = new ArrayList<>();
        adapter = new PhoneContactAdapter(getActivity(), contacts, presenter);
        listForContact.setAdapter(adapter);
        listForContact.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initAsynkTask() {
        mTask = new ConatctAsynkTask();
    }

    //Method for set permission READ_CONTACT
    private void setPermission() {
        //get permission from manifest
        int hasReadContactPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);

        //If device has API to 23, set permission
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true;
        } else {
            //Call Compat Activity for set permission
            requestPermissions( new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }

        //If permission allow
        if (READ_CONTACTS_GRANTED) {
            mTask.execute();
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

    //Method, which send data to MainActivity
    @Override
    public void sendDataToMainActivity(Contact contact) {
        ((MainActivity)getActivity()).goToSecondaryActivity(contact, "PhoneContactFragment");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    READ_CONTACTS_GRANTED = true;
                }
        }

        if (READ_CONTACTS_GRANTED) {
            mTask.execute();
        } else {
            Toast.makeText(getActivity(), "Потрібно дозволити доступ до контактів", Toast.LENGTH_LONG).show();
        }
    }


    public class ConatctAsynkTask extends AsyncTask<Void, Void, List<Contact>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Contact> doInBackground(Void... voids) {
            return presenter.getContacts();
        }

        @Override
        protected void onPostExecute(List<Contact> contactList) {
            super.onPostExecute(contacts);
            contacts.addAll(contactList);
            adapter.notifyDataSetChanged();
            progress.setVisibility(View.GONE);
        }
    }


}
