package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.WorkWithFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
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

    //Method, which get intentExtra from MainActivity and show correspondence fragment
    private void showFragment() {
        //get intent
        Intent intent = getIntent();

        //checking intent and stringExtra on null
        if (intent != null && intent.getStringExtra("fragmentName") != null) {
            String fragmentName = intent.getStringExtra("fragmentName");

            //if data was getting from AddContactFragment
            if (fragmentName.equals("AddContactFragment")) {
                Contact contact = (Contact)intent.getSerializableExtra("contact");

                showEditContactFragment(contact, fragmentName);
            }
            //if data was getting from ContactListFragment with clicked on edit button
            else if (fragmentName.equals("ContactListEditBtn")) {
                long id = intent.getLongExtra("id", 0);
                long recruiterNotesId = intent.getLongExtra("recruiterNotesId", 0);

                showEditContactFragment(id, recruiterNotesId, fragmentName);
            }
            //if data was getting from ContactListFragment
            else if (fragmentName.equals("ContactList")) {
                long id = intent.getLongExtra("id", 0);
                long recruiterNotesId = intent.getLongExtra("recruiterNotesId", 0);

                showDetailContactFragment(id, recruiterNotesId, fragmentName);
            }
        }
    }

    //Method, which show EditContactFragment with parameters Contact and fragmentName
    private void showEditContactFragment(Contact contact, String fragmentName) {
        EditContactFragment fragment = new EditContactFragment();

        Bundle args = new Bundle();
        args.putSerializable("contact", contact);
        args.putString("fragmentName", fragmentName);
        fragment.setArguments(args);

        addFragment(fragment);
    }

    //Method, which show EditContactFragment with parameters id, recruiterNotesId and fragmentName
    private void showEditContactFragment(long id, long recruiterNotesId, String fragmentName) {
        EditContactFragment fragment = new EditContactFragment();

        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putLong("recruiterNotesId", recruiterNotesId);
        args.putString("fragmentName", fragmentName);
        fragment.setArguments(args);

        addFragment(fragment);
    }

    //Method, which show DetailContactFragment with parameters id, recruiterNotesId and fragmentName
    private void showDetailContactFragment(long id, long recruiterNotesId, String fragmentName) {
        DetailContactFragment fragment = new DetailContactFragment();

        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putLong("recruiterNotesId", recruiterNotesId);
        args.putString("fragmentName", fragmentName);
        fragment.setArguments(args);

        addFragment(fragment);
    }

    //Method, which show DetailContactFragment without parameters
    public void showDetailContactFragment() {
        addFragment(new DetailContactFragment());
    }

    //Method, which go to MainActivity and passes him fragmentName
    public void goToMainActivity(String fragmentName) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("fragmentName", fragmentName);
        startActivity(intent);
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
