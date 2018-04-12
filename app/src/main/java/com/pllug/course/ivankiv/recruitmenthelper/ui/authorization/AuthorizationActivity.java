package com.pllug.course.ivankiv.recruitmenthelper.ui.authorization;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.WorkWithFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.signup.SignUpFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.signup.byemail.SignUpByEmailFragment;

public class AuthorizationActivity extends AppCompatActivity implements WorkWithFragment {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        showSignUp();
    }


    //Method which open SignUpFragment
    public void showSignUp() {
        addFragment(new SignUpFragment());
    }

    //Method which open SignUpByEmailFragment
    public void showSignUpByEmail() {
        replaceFragment(new SignUpByEmailFragment());
    }

    //Method, which open MainActivity
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Method for adding fragment in FrameLayout
    @Override
    public void addFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_sign_in, fragment)
                .commit();
    }

    //Method for replacing fragment
    @Override
    public void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_sign_in, fragment)
                .addToBackStack(null)
                .commit();
    }
}
