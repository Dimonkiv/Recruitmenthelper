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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;
import com.pllug.course.ivankiv.recruitmenthelper.ui.WorkWithFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.addcontact.AddContactFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.contactlist.ContactListFragment;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements MainContract.View, WorkWithFragment {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private MainPresenter presenter;
    private TextView name, lastname, email;
    private CircleImageView userAvatar;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initPresenter();
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

    //Initialization Presenter
    private void initPresenter() {
        presenter = new MainPresenter(this);
    }

    //Initialization Toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    //Initialization Navigation Drawer
    private void initNavigationDrawer() {
        initNavigationDrawerListener(navigationView);
        setHeaderDataOfNavigationDrawer();
    }

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

    //Method which set data into NavigationDrawer header from database
    private void setHeaderDataOfNavigationDrawer() {
        View header = navigationView.getHeaderView(0);

        //Get user from presenter
        User user = presenter.getUser();

        //Initialization header elements
        name = header.findViewById(R.id.header_name);
        lastname = header.findViewById(R.id.header_lastname);
        email = header.findViewById(R.id.header_email);
        userAvatar = header.findViewById(R.id.header_avatar);

        //Show data from db
        if (user.getName() != null) {
            name.setText(user.getName());
        }
        if (user.getLastname() != null) {
            lastname.setText(user.getLastname());
        }
        if (user.getEmail() != null) {
            email.setText(user.getEmail());
        }

       if (user.getImageUri() != null) {
            Glide.with(this)
                    .load(user.getImageUri())
                    .into(userAvatar);
        }

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
                showContactListFragment();
                break;
            case R.id.navigation_drawer_import_linkedin:

                break;
            case R.id.navigation_drawer_add_contact:
                showAddContact();
                break;
            case R.id.navigation_drawer_selected_contacts:

                break;
            case R.id.navigation_drawer_settings:

                break;
            case R.id.navigation_drawer_logout:

                break;
        }
        toolbar.setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    //Method which show AddContactFragment
    public void showAddContact() {
        addFragment(new AddContactFragment());
    }

    //Method, which show fragment
    private void showFragment() {
        Intent intent = getIntent();

        if (intent != null && intent.getStringExtra("fragmentName") != null) {
            String fragmentName = intent.getStringExtra("fragmentName");

            if (fragmentName.equals("AddContactFragment")) {
                showAddContact();
            } else if (fragmentName.equals("ContactListEditBtn")) {
                showContactListFragment();
            }
        } else {
            showContactListFragment();
        }
    }
    //Method which show ContactListFragment
    public void showContactListFragment() {
        toolbar.setTitle("Головний екран");
        addFragment(new ContactListFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_activity_toolbar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method for adding fragment in FrameLayout
    @Override
    public void addFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.main_activity_frame_container, fragment)
                .commit();
    }

    //Method for replace fragment
    @Override
    public void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
