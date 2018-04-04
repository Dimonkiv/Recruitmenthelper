package com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.fragment;

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
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.activity.SignInActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.contract.SignUpByEmailContract;
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.presenter.SignUpByEmailPresenter;


public class SignUpByEmailFragment extends Fragment implements SignUpByEmailContract.View {
    private View root;
    private Toolbar toolbar;
    private ImageView photo;
    private ImageButton btnLoadPhoto;
    private EditText name, lastname, email, phone,
            password, reenterPasword;
    Uri imageUri;
    private SignUpByEmailPresenter presenter;
    private final int PICK_IMAGE = 1;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_sign_up_by_email, container, false);
        initView();
        initDatabase();
        initPresenter();
        initListener();
        initToolbar();
        return root;
    }

    //Прив’язка ui елементів з java кодом

    private void initDatabase() {
        database = InitDatabase.getInstance().getDatabese();
    }
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

    //Ініціалізація презентера
    private void initPresenter() {
        presenter = new SignUpByEmailPresenter(this, database);
    }

    //Обробник кліків
    private void initListener() {
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGallery();

            }
        });
    }

    //Метод для відкриття галереї
    //Та передавання вибраної картинки
    private void showGallery() {
        //Відкриваємо стандартну галерею, для вибору зображень за допомогою Intent.ACTION_PICK
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //Тип отримуваних об’єктів - image
        photoPickerIntent.setType("image/*");
        //Запускаємо intent з очікуванням повертаючого результату у вигляді інформації через intent
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    //Ініціалізуємо toolbar
    private void initToolbar() {
        //Вказуємо, що toolbar буде використовуватися,
        //як ActionBar
        ((SignInActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Реєстрація через email");
        setHasOptionsMenu(true);

        //Встановлення кнопки назад
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Обробник кліку кнопки назад
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SignInActivity) getActivity()).showSignUp();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Якщо прийдешній код рівний коду завантаження картинки
        if (requestCode == PICK_IMAGE) {

            //Приховуємо іконку фотопарату
            btnLoadPhoto.setVisibility(View.GONE);
            //Отримуємо uri вибраного зображення
            imageUri = data.getData();
            //Відображаємо фотографію в imageVew
            presenter.showPhoto();
        }
    }

    //Створення іконок для toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sign_up_by_email_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Обробник кліків на іконки в toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_up_by_email_toolbar_send) {

            if (presenter.checkingEnterData()) {
                Toast.makeText(getActivity(), "Sended", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "некоректні дані", Toast.LENGTH_SHORT).show();
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
