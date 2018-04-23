package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.WorkWithFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailconatct.DetailContactFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.EditContactFragment;

public class SecondaryActivity extends AppCompatActivity implements WorkWithFragment {
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        showFragment();

    }

    private void showFragment() {
        Intent intent = getIntent();

        if (intent != null) {
            if (intent.getStringExtra("contactList") != null) {
                if (intent.getStringExtra("contactList").equals("contactList")) {
                    long id = intent.getLongExtra("id", 1);
                    long recruitNotesId = intent.getLongExtra("recruiterNotesId", 1);
                    showDetailContactFragment(id, recruitNotesId);
                }
            } else if (intent.getStringExtra("addContactFragment") != null) {
                if (intent.getStringExtra("addContactFragment").equals("addContactFragment")) {
                    Contact contact = (Contact) intent.getSerializableExtra("contact");
                    showEditContactFragment(contact);
                }
            } else if (intent.getStringExtra("ContactListActivity") != null) {
                if (intent.getStringExtra("ContactListActivity").equals("ContactListActivity")) {
                    showEditContactFragment();
                }
            }
        }
    }


    public void showDetailContactFragment(long id, long recruitNotesId) {
        DetailContactFragment detailContactFragment = new DetailContactFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putLong("recruitNotesId", recruitNotesId);
        detailContactFragment.setArguments(args);
        addFragment(detailContactFragment);

    }

    public void showEditContactFragment(Contact contact) {
        EditContactFragment editContactFragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putSerializable("contact", contact);
        editContactFragment.setArguments(args);
        addFragment(editContactFragment);
    }
    public void showEditContactFragment() {
        EditContactFragment editContactFragment = new EditContactFragment();
        addFragment(editContactFragment);
    }

    @Override
    public void addFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.activity_secondary_frame_container, fragment)
                .commit();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_secondary_frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
