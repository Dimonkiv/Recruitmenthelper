package com.pllug.course.ivankiv.recruitmenthelper.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.MainScreenFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact.PhoneContactFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist.ContactListFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected.SelectedContactFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private TextView name, lastname, email;
    private CircleImageView userAvatar;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolbar();
        initNavigationDrawer();
        initMDrawerToggle();
        showFragment();

    }

    //Initialization View
    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.main_activity_toolbar);
    }


    //Initialization Toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    //Initialization Navigation Drawer
    private void initNavigationDrawer() {
        initNavigationDrawerListener(navigationView);    }

    //Initialization ActionBarDrawerToggle
    private void initMDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

    }


    //Method for initialization Navigation Drawer Listener
    private void initNavigationDrawerListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemNavigationDrawer(item);
                return true;
            }
        });
    }

    //Listener for Navigation Drawer item
    private void selectItemNavigationDrawer(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_drawer_my_page:
                Toast.makeText(this, "my page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_drawer_main_screen:
                showMainScreenFragment("noneFragment");
                break;
            case R.id.navigation_drawer_import_linkedin:

                break;
            case R.id.navigation_drawer_add_contact:
                showAddContact();
                break;
            case R.id.navigation_drawer_selected_contacts:
                showSelectedContactFragment();
                break;
            case R.id.navigation_drawer_settings:

                break;
            case R.id.navigation_drawer_logout:

                break;
        }
        toolbar.setTitle(item.getTitle());
        drawerLayout.closeDrawers();

    }

    //Method, which show fragment
    private void showFragment() {
        Intent intent = getIntent();

        if (intent != null && intent.getStringExtra("fragmentName") != null) {
            String fragmentName = intent.getStringExtra("fragmentName");

            if (fragmentName.equals("PhoneContactFragment")) {
                showAddContact();
            } else if (fragmentName.equals("ContactListEditBtn") || fragmentName.equals("ContactList") || fragmentName.equals("LastConnect")) {
                showMainScreenFragment(fragmentName);
            } else if (fragmentName.equals("SelectedContact")) {
                showSelectedContactFragment();
            }
        } else {
            showMainScreenFragment("noneFragment");
        }
    }

    //Method which show PhoneContactFragment
    public void showAddContact() {
        replaceFragment(new PhoneContactFragment());
    }

    //Method, which show MainScreenFragment
    public void showMainScreenFragment(String fragmentName) {
        getSupportActionBar().setTitle("Головний екран");
        MainScreenFragment fragment = new MainScreenFragment();

        Bundle args = new Bundle();
        args.putString("fragmentName", fragmentName);
        fragment.setArguments(args);

        replaceFragment(fragment);
    }

    //Method which show ContactListFragment
    public void showContactListFragment() {
        getSupportActionBar().setTitle("Головний екран");
        replaceFragment(new ContactListFragment());
    }

    //Method which show SelectedContactFragment
    public void showSelectedContactFragment() {
        getSupportActionBar().setTitle("Вибрані контакти");
        replaceFragment(new SelectedContactFragment());
    }

    //Method, which go to SecondaryActivity and send Contact
    public void goToSecondaryActivity(Contact contact, String fragmentName) {
        Intent intent = new Intent(this, SecondaryActivity.class);
        intent.putExtra("fragmentName", fragmentName);
        intent.putExtra("contact", contact);
        startActivity(intent);
        finish();
    }

    //Method, which go to SecondaryActivity and send recruiterNotesId, id
    public void goToSecondaryActivity(long recruiterNotesId, long id, String fragmentName) {
        Intent intent = new Intent(this, SecondaryActivity.class);
        intent.putExtra("fragmentName", fragmentName);
        intent.putExtra("recruiterNotesId", recruiterNotesId);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_activity_toolbar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method for replace fragment
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
