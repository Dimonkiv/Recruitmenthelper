package com.pllug.course.ivankiv.recruitmenthelper.ui.main.editcontact;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.editcontact.datepicker.DatePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;


public class EditContactActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private Contact contact;
    private Toolbar toolbar;
    private ImageView photo;
    private ImageButton loadPhoto, dateButton;
    private Spinner profession, jobInterest;
    private TextView trainee, junior, middle, senior, date;
    private RadioGroup position;
    private RadioButton traineeRBtn, juniorRBtn, middleRBtn, seniorRBtn;
    private EditText jobOrUniversity, experirnce, advantages, disadvanteges,
            notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        initView();
        initListener();
        getFromIntent();
        initToolbar();
        initProfessionSpinner();
        initJobInterestSpinner();

    }

    //Initialization View
    private void initView() {
        toolbar = findViewById(R.id.edit_contact_toolbar);

        photo = findViewById(R.id.edit_contact_photo);
        loadPhoto = findViewById(R.id.edit_contact_btn_load_photo);

        date = findViewById(R.id.edit_contact_date);
        dateButton = findViewById(R.id.edit_contact_date_btn);

        jobOrUniversity = findViewById(R.id.edit_contact_job_or_university);
        experirnce = findViewById(R.id.edit_contact_experience);
        advantages = findViewById(R.id.edit_contact_advantages);
        disadvanteges = findViewById(R.id.edit_contact_disadvantages);
        notes = findViewById(R.id.edit_contact_notes);


        profession = findViewById(R.id.edit_contact_profession);
        jobInterest = findViewById(R.id.edit_contact_job_interest);

        trainee = findViewById(R.id.edit_contact_trainee_txt);
        junior = findViewById(R.id.edit_contact_junior_txt);
        middle = findViewById(R.id.edit_contact_middle_txt);
        senior = findViewById(R.id.edit_contact_senior_txt);

        position = findViewById(R.id.edit_contact_position);

        traineeRBtn = findViewById(R.id.edit_contact_trainee_r_btn);
        juniorRBtn = findViewById(R.id.edit_contact_junior_r_btn);
        middleRBtn = findViewById(R.id.edit_contact_middle_r_btn);
        seniorRBtn = findViewById(R.id.edit_contact_senior_r_btn);
    }

    private void initListener() {
        dateButton.setOnClickListener(this);
    }

    //Get contact from addContact
    private void getFromIntent() {
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("contact");
        Log.d("myLog", contact.getName() + " ");
    }

    //Initialization toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.edit_contact);


        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditContactActivity.this, "Back!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Initialization spinner of profession
    private void initProfessionSpinner() {
        String[] professions = {"Менеджер", "Рекрутер", "Розробник", "Тестер", "TeamLead"};

        // initialization adapter
        ArrayAdapter<String> adapterProfession = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, professions);
        adapterProfession.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        profession.setAdapter(adapterProfession);

        // set title
        profession.setPrompt("Професія");
        // выделяем элемент
        //profession.setSelection(2);
        // set item listener
        profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    //Initialization JobInterest Spinner
    private void initJobInterestSpinner() {
        String[] jobInterests = {"Працює", "Шукає роботу", "Зацікавлений в нових можливостях"};

        //Set adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobInterests);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jobInterest.setAdapter(adapter);

        //Set title
        jobInterest.setPrompt("Зацікавленість в роботі");
        // устанавливаем обработчик нажатия
        jobInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    //Set icon-button allow
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_up_by_email_toolbar_menu, menu);
        return true;
    }

    //Listener for allow button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_up_by_email_toolbar_send) {
            Toast.makeText(this, "Send!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.edit_contact_date_btn:
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        date.setText(currentDateString);
    }
}
