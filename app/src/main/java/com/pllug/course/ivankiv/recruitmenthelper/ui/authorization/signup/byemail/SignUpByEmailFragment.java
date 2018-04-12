package com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.signup.byemail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.User;
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.AuthorizationActivity;


public class SignUpByEmailFragment extends Fragment implements SignUpByEmailContract.View {
    private View root;
    private Toolbar toolbar;
    private ImageView photo;
    private ImageButton btnLoadPhoto;
    private EditText name, lastname, email, phone,
            password, reenterPasword;

    private Uri imageUri;
    private SignUpByEmailPresenter presenter;

    private final int PICK_IMAGE = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_sign_up_by_email, container, false);

        initView();
        initPresenter();
        initListener();
        initToolbar();

        return root;
    }

    //Initialization View
    private void initView() {
        toolbar = root.findViewById(R.id.sign_up_by_email_toolbar);

        photo = root.findViewById(R.id.sign_up_by_email_photo);
        btnLoadPhoto = root.findViewById(R.id.sign_up_by_email_btn_load_photo);

        name = root.findViewById(R.id.sign_up_by_email_name);
        lastname = root.findViewById(R.id.sign_up_by_email_lastname);
        email = root.findViewById(R.id.sign_up_by_email_email);
        phone = root.findViewById(R.id.sign_up_by_email_phone);
        password = root.findViewById(R.id.sign_up_by_email_password);
        reenterPasword = root.findViewById(R.id.sign_up_by_email_reenter_password);
    }

    //Initialization Presenter
    private void initPresenter() {
        presenter = new SignUpByEmailPresenter(this);
    }

    //Initialization Listener
    private void initListener() {
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGallery();

            }
        });
    }

    //Initialization  Toolbar
    private void initToolbar() {
        //Said that toolbar will be use such as ActionBar
        ((AuthorizationActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Реєстрація через email");
        setHasOptionsMenu(true);

        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthorizationActivity) getActivity()).showSignUp();
            }
        });
    }

    //Method for choices photo from system gallery
    private void showGallery() {
        //Open system gallery
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //Type of received object - image
        photoPickerIntent.setType("image/*");
        //Start activity for result
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If received cod is equal to PICK_IMAGE code
        if (requestCode == PICK_IMAGE) {

            //Hide camera icon
            btnLoadPhoto.setVisibility(View.GONE);
            //Get uri from choices image
            imageUri = data.getData();
            //Show photo in imageVew
            presenter.showPhoto();
        }
    }

    //Set icon-button allow
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sign_up_by_email_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Listener for allow button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_up_by_email_toolbar_send) {

            if (presenter.checkingEnterData()) {
                showToast("Send!");
            } else {
                showToast("Incorrect data!");
            }

        }
        return true;
    }

    @Override
    public Uri getImageUri() {
        return imageUri;
    }

    @Override
    public ImageView getImageView() {
        return photo;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public User getUser() {
        User user = new User();

        user.setImageUri(imageUri.toString());
        user.setName(name.getText().toString());
        user.setLastname(lastname.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPhone(phone.getText().toString());
        user.setPassword(password.getText().toString());
        user.setReenterPassword(reenterPasword.getText().toString());
        return user;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
