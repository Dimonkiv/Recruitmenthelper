package com.pllug.course.ivankiv.recruitmenthelper.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.SignUp;
import com.pllug.course.ivankiv.recruitmenthelper.ui.fragment.sign_up_by_email.SignUpByEmail;

public class SignIn extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        showSignUp();
    }

    //Метод для додавання фрагментів у frame layout
    private void addFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_sign_in, fragment)
                .commit();
    }

    //Метод для заміщення фрагментів у frame layout
    private void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_sign_in, fragment)
                .addToBackStack(null)
                .commit();
    }

    //Метод, який відкриває sign_up fragment
    public void showSignUp() {
        addFragment(new SignUp());
    }

    //Метод, який відкриває sign_up_by_email_fragment
    public void showSignUpByEmail() {
        replaceFragment(new SignUpByEmail());
    }
}
