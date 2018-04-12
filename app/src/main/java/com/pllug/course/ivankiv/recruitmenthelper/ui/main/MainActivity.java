package com.pllug.course.ivankiv.recruitmenthelper.ui.main;

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

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;
import com.pllug.course.ivankiv.recruitmenthelper.ui.WorkWithFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.addcontact.AddContactFragment;


public class MainActivity extends AppCompatActivity implements MainContract.View, WorkWithFragment {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private MainPresenter presenter;
    private TextView name, lastname, email;
    private ImageView userAvatar;
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
        name.setText(user.getName());
        lastname.setText(user.getLastname());
        email.setText(user.getEmail());

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
