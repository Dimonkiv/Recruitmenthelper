package com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.add_contact;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.PhoneContact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.activity.main.Main;
import com.pllug.course.ivankiv.recruitmenthelper.ui.adapter.AddContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddContact extends Fragment implements AddContactContract.View, AddContactAdapter.AddContactAdapterEventListener {
    private View root;
    private AddContactPresenter presenter;
    private List<PhoneContact> contacts;
    private ListView listForContact;
    private AddContactAdapter adapter;

    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static boolean READ_CONTACTS_GRANTED = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_contact, container, false);

        initView();
        initPresenter();
        initAdapter();
        setPermission();

        return root;
    }


    //Метод для ініціалізації view елементів
    private void initView() {
        listForContact = root.findViewById(R.id.list_for_phone_contact);
    }

    //Метод для ініціалізації презентера
    private void initPresenter() {
        presenter = new AddContactPresenter(this);
    }

    //Метод, для ініціалізації адаптера
    private void initAdapter() {
        contacts = new ArrayList<>();
        adapter = new AddContactAdapter(getActivity(), contacts);
        adapter.addListener(this);

        listForContact.setAdapter(adapter);

    }

    //Метод для встановлення дозволів
    private void setPermission() {
        //Отримуємо дозвіл
        int hasReadContactPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);

        //Якщо пристрій до API 23 встановлюємо дозвіл
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true;
        } else {
            //викликаємо вікно для установки дозвілів
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }

        //Якщо дозвіл надано то підгруєаємо контакти
        if (READ_CONTACTS_GRANTED) {
            setContacts();
        }
    }

    //Метод який заповнює список контактів контактами отриманими з презентера
    private void setContacts() {
        contacts.addAll(presenter.getContacts());


        adapter.notifyDataSetChanged();
    }

    @Override
    public ContentResolver getContentResolver() {
        return ((Main) getActivity()).getContentResolver();
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
            setContacts();
        } else {
            Toast.makeText(getActivity(), "Требуется установить разрешения", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void openEditPage(int position) {
        Toast.makeText(getActivity(), "button", Toast.LENGTH_SHORT).show();
        PhoneContact contact = adapter.getItem(position);
        ((Main) getActivity()).showEditContactFragment(contact);

    }
}
