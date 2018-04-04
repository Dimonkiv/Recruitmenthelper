package com.pllug.course.ivankiv.recruitmenthelper.ui.activity.main;

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
import com.pllug.course.ivankiv.recruitmenthelper.data.model.PhoneContact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;
import com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.ContactsList;
import com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.add_contact.AddContact;


public class Main extends AppCompatActivity implements MainContract.View {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private MainPresenter presenter;
    private User user;
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

        //Отримуємо дані від presenter
        getUserFromPresenter();
        initNavigationDrawer();
        initMDrawerToggle();
        showMainFragment();
    }


    //Метод, для ініціалізації змінних
    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.main_activity_toolbar);
    }

    //Метод для ініціалізації презентера
    private void initPresenter() {
        presenter = new MainPresenter(this);
    }

    //Метод, для ініціалізації toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);
    }


    //Метод для отримання даних від презентера
    private void getUserFromPresenter() {
        user = presenter.getUser();
    }

    //Метод, для ініціалізації Navigation Drawer
    private void initNavigationDrawer() {
        initNavigationDrawerListener(navigationView);
        setHeaderDataOfNavigationDrawer();
    }

    //Метод для ініціалізації ActionBarDrawerToggle
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

    //Метод, для встановлення інформації з бд в шапку navigation drawer
    private void setHeaderDataOfNavigationDrawer() {
        View header = navigationView.getHeaderView(0);

        //Ініціалізація внутрішніх елементів шапки
        name = header.findViewById(R.id.header_name);
        lastname = header.findViewById(R.id.header_lastname);
        email = header.findViewById(R.id.header_email);
        userAvatar = header.findViewById(R.id.header_avatar);

        //Показ даних з бд в шапці
        name.setText(user.getName());
        lastname.setText(user.getLastname());
        email.setText(user.getEmail());

    }

    //Метод, для відстежування кліків на внутрішні елементи navigation drawer
    private void initNavigationDrawerListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemNavigationDrawer(item);
                return true;
            }
        });
    }

    //метод, для визначення, який внутрішній елемент Navigation drawer був натиснутий
    private void selectItemNavigationDrawer(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_drawer_my_page:
                Toast.makeText(this, "my page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_drawer_main_screen:
                showMainFragment();
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

    //Метод для додавання фрагментів у frame layout
    private void addFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.main_activity_frame_container, fragment)
                .commit();
    }

    //Метод для заміщення фрагментів у frame layout
    private void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    //Метод, який покаже головну сторінку
    public void showMainFragment() {
        toolbar.setTitle("Головний екран");
        addFragment(new ContactsList());
    }

    //Метод, який показує екран для додавання контактів
    public void showAddContact() {
        replaceFragment(new AddContact());
    }

    //Метод, який показує фрагмет редагування екрану
    public void showEditContactFragment (PhoneContact contact) {
        EditContact editFragment = new EditContact();
        Bundle args = new Bundle();
        args.putSerializable("contact", contact);
        editFragment.setArguments(args);

        replaceFragment(editFragment);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_activity_toolbar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
