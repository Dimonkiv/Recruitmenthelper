package com.pllug.course.ivankiv.recruitmenthelper.ui.main.addcontact;

import android.Manifest;
import android.content.ContentResolver;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.addcontact.adapter.AddContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddContactFragment extends Fragment implements AddContactContract.View {
    private View root;
    private AddContactPresenter presenter;
    private List<Contact> contacts;
    private RecyclerView listForContact;
    private ProgressBar progress;
    private AddContactAdapter adapter;
    private ConatctAsynkTask mTask;

    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static boolean READ_CONTACTS_GRANTED = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_contact, container, false);

        initView();
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
    }

    //Initialization Presenter
    private void initPresenter() {
        presenter = new AddContactPresenter(this);
    }

    //Initialization Adapter
    private void initAdapter() {
        contacts = new ArrayList<>();
        adapter = new AddContactAdapter(getActivity(), contacts);
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
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }

        //If permission allow
        if (READ_CONTACTS_GRANTED) {
            mTask.execute();
        }
    }


    @Override
    public ContentResolver getContentResolver() {
        return ((MainActivity) getActivity()).getContentResolver();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    READ_CONTACTS_GRANTED = true;
                }
        }

        if (READ_CONTACTS_GRANTED) {
            mTask.execute();
        } else {
            Toast.makeText(getActivity(), "Требуется установить разрешения", Toast.LENGTH_LONG).show();
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
