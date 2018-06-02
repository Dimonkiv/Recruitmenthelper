package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter.LanguageEditTextAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter.SkillAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.datepicker.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditContactView implements EditContactContract.View, View.OnClickListener {
    //View
    private Toolbar toolbar;
    private CircleImageView photo;
    private ImageButton loadPhoto, dateButton, addLanguage, addSkill;
    private RelativeLayout image_pen;
    private LinearLayout experience_container;
    private Spinner jobInterest;
    private TextView date;
    private RecyclerView languageRecycler, skillRecycler;
    private RadioGroup experience_group, type_of_employment_group;
    private RadioButton traineeRBtn, juniorRBtn, middleRBtn, seniorRBtn, techLeadRBtn, studentRBtn, workRBtn;
    private EditText name, phone, email, linkedIn, jobOrUniversity, profession, advantages, disadvanteges, notes;

    //Varies
    private String photoUri, experienceStr, fragmentName, typeOfEmploymentStr;
    private Long id, recruiterNotesId;

    //Adapter
    private LanguageEditTextAdapter languageEditTextAdapter;
    private SkillAdapter skillAdapter;

    //Other
    private DatePickerDialog.OnDateSetListener onDatelistener;
    private EditContactFragment fragment;
    private EditContactPresenter presenter;
    private Context mContext;
    private View root;



    public EditContactView(EditContactFragment fragment, EditContactPresenter presenter, Context mContext, View root) {
        this.fragment = fragment;
        this.presenter = presenter;
        this.mContext = mContext;
        this.root = root;
        initView();
        initLanguageAdapter();
        initSkillAdapter();
        initListener();
        initToolbar();
        initDateListener();
        initJobInterestSpinner();

    }
    //Initialization View
    private void initView() {

        toolbar = root.findViewById(R.id.edit_contact_toolbar);

        photo = root.findViewById(R.id.edit_contact_photo);
        loadPhoto = root.findViewById(R.id.edit_contact_btn_load_photo);

        date = root.findViewById(R.id.edit_contact_date);
        dateButton = root.findViewById(R.id.edit_contact_date_btn);

        name = root.findViewById(R.id.edit_contact_name);
        phone = root.findViewById(R.id.edit_contact_phone);
        email = root.findViewById(R.id.edit_contact_email);
        linkedIn = root.findViewById(R.id.edit_contact_linkedin);
        jobOrUniversity = root.findViewById(R.id.edit_contact_job_or_university);
        advantages = root.findViewById(R.id.edit_contact_advantages);
        disadvanteges = root.findViewById(R.id.edit_contact_disadvantages);
        notes = root.findViewById(R.id.edit_contact_notes);


        languageRecycler = root.findViewById(R.id.edit_contact_language_container);
        addLanguage = root.findViewById(R.id.edit_contact_add_language);

        skillRecycler = root.findViewById(R.id.edit_contact_skills_container);
        addSkill = root.findViewById(R.id.edit_contact_add_skill);

        profession = root.findViewById(R.id.edit_contact_profession);
        jobInterest = root.findViewById(R.id.edit_contact_job_interest);


        experience_group = root.findViewById(R.id.edit_contact_experience_group);
        type_of_employment_group = root.findViewById(R.id.edit_contact_type_of_employment_group);

        traineeRBtn = root.findViewById(R.id.edit_contact_trainee_r_btn);
        juniorRBtn = root.findViewById(R.id.edit_contact_junior_r_btn);
        middleRBtn = root.findViewById(R.id.edit_contact_middle_r_btn);
        seniorRBtn = root.findViewById(R.id.edit_contact_senior_r_btn);
        techLeadRBtn = root.findViewById(R.id.edit_contact_tech_lead_r_btn);
        studentRBtn = root.findViewById(R.id.edit_contact_student_r_btn);
        workRBtn = root.findViewById(R.id.edit_contact_worker_r_btn);

        image_pen = root.findViewById(R.id.edit_contact_image_pen);
        experience_container = root.findViewById(R.id.edit_contact_experience_container);

    }

    //Initialization listener
    private void initListener() {
        dateButton.setOnClickListener(this);
        photo.setOnClickListener(this);
        traineeRBtn.setOnClickListener(this);
        juniorRBtn.setOnClickListener(this);
        middleRBtn.setOnClickListener(this);
        seniorRBtn.setOnClickListener(this);
        techLeadRBtn.setOnClickListener(this);
        workRBtn.setOnClickListener(this);
        studentRBtn.setOnClickListener(this);
        addLanguage.setOnClickListener(this);
        addSkill.setOnClickListener(this);

    }

    //Initialization toolbar
    private void initToolbar() {
        ((SecondaryActivity) mContext).setSupportActionBar(toolbar);

        //Set title
        ((SecondaryActivity) mContext).getSupportActionBar().setTitle("Редагування контакту");
        fragment.setHasOptionsMenu(true);
        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fragment.setDataToMainActivity(fragmentName);
            }
        });
    }

    //Method, which show image for image container
    private void showImagePen() {
        image_pen.setVisibility(View.VISIBLE);
    }

    //Initialization JobInterest Spinner
    private void initJobInterestSpinner() {
        String[] jobInterests = {"Не працює", "Працює", "Шукає роботу", "Зацікавлений в нових можливостях"};

        //Set adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, jobInterests);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jobInterest.setAdapter(adapter);
    }

    //Method, which hide profession_edit_text, container for position radio button and experience_edit_text
    private void hideDeveloperFields() {
        jobOrUniversity.setHint("Місце навчання");
        profession.setVisibility(View.GONE);
        experience_container.setVisibility(View.GONE);
    }

    //Method, which show profession_edit_text, container for position radio button and experience_edit_text
    private void showDeveloperFields() {
        jobOrUniversity.setHint("Місце роботи");
        profession.setVisibility(View.VISIBLE);
        experience_container.setVisibility(View.VISIBLE);
    }

    //Method, which show DatePickerDialog
    private void showDatePicker() {
        final DatePickerFragment datePicker = new DatePickerFragment();
        //Set up current Date into dialog

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        datePicker.setArguments(args);

        //Set callback to capture selected date
        datePicker.setCallBack(onDatelistener);
        datePicker.show(fragment.getFragmentManager(), "Date Picker");


    }

    //Initialization DatePickerListener
    private void initDateListener() {
        onDatelistener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.setText(String.valueOf(dayOfMonth) + "." + String.valueOf(monthOfYear + 1)
                        + "." + String.valueOf(year));
            }
        };
    }

    //Method for choices photo from system gallery
    private void showGallery() {
        //Open system gallery
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //Type of received object - image
        photoPickerIntent.setType("image/*");
        //Start activity for result
        fragment.startActivityForResult(photoPickerIntent);
    }

    //Initialization language adapter
    private void initLanguageAdapter() {
        languageEditTextAdapter = new LanguageEditTextAdapter(mContext, new LanguageEditTextAdapter.LanguageAdapterListener() {
            @Override
            public void onRemoveLanguage(int position) {
                removeLanguageField(position);
            }
        });
        languageRecycler.setAdapter(languageEditTextAdapter);
        languageRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        addLanguageField();
    }

    //Method, which add editText for entering language, spinner for choosing language level
    // and imageButton for removing this fields, to languageRecyclerView
    private void addLanguageField() {
        List<Language> languages = new ArrayList<>();
        languages.addAll(languageEditTextAdapter.getLanguages());
        languages.add(new Language());
        languageEditTextAdapter.addAllLanguages(languages);
        languageEditTextAdapter.notifyItemInserted(languageEditTextAdapter.getItemCount() - 1);
    }

    //Method which remove language field
    private void removeLanguageField(int position) {
        List<Language> languages = new ArrayList<>();
        languages.addAll(languageEditTextAdapter.getLanguages());
        if(position < languages.size()){
            languages.remove(position);
            languageEditTextAdapter.addAllLanguages(languages);
            languageEditTextAdapter.notifyItemRemoved(position);
        }
    }

    //Initialization skill adapter
    private void initSkillAdapter() {
        skillAdapter = new SkillAdapter(mContext, new SkillAdapter.SkillAdapterListener() {
            @Override
            public void onRemoveSkill(int position) {
                removeSkillField(position);
            }
        });
        skillRecycler.setAdapter(skillAdapter);
        skillRecycler.setLayoutManager(new LinearLayoutManager(mContext));
//        addSkillField();
        addSkillField();
    }

    //Method, which add editText for entering skill and imageButton for removing this field
    //to skillRecyclerView
    private void addSkillField() {
        List<Skill> skills = new ArrayList<>();
        skills.addAll(skillAdapter.getSkills());
        skills.add(new Skill());
        skillAdapter.addAllSkill(skills);
        skillAdapter.notifyItemInserted(skillAdapter.getItemCount() - 1);
    }

    //Method which remove skills field
    private void removeSkillField(int position) {
        List<Skill> skills = new ArrayList<>();
        skills.addAll(skillAdapter.getSkills());
        if(position < skills.size()){
            skills.remove(position);
            skillAdapter.addAllSkill(skills);
            skillAdapter.notifyItemRemoved(position);
        }
    }

    //Method, which set data from telephone book into edit form
    private void setContactDataFromPhoneBook(Contact contact) {
        if (contact.getPhotoUri() != null) {
            photoUri = contact.getPhotoUri();
            Glide.with(mContext)
                    .asBitmap()
                    .load(photoUri)
                    .into(photo);
            showImagePen();

        }

        if (contact.getName() != null) {
            name.setText(contact.getName());
        }

        if (contact.getPhone() != null) {
            phone.setText(contact.getPhone());
        }

        if (contact.getEmail() != null) {
            email.setText(contact.getName());
        }

        if (contact.getLinkedInLink() != null) {
            linkedIn.setText(contact.getLinkedInLink());
        }

        if (contact.getDateOfLatestContact() != null) {
            date.setText(contact.getDateOfLatestContact());
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_contact_date_btn:
                showDatePicker();
                break;
            case R.id.edit_contact_photo:
                showGallery();
                break;
            case R.id.edit_contact_trainee_r_btn:
                experienceStr = "trainee";
                break;
            case R.id.edit_contact_junior_r_btn:
                experienceStr = "junior";
                break;
            case R.id.edit_contact_middle_r_btn:
                experienceStr = "middle";
                break;
            case R.id.edit_contact_senior_r_btn:
                experienceStr = "senior";
                break;
            case R.id.edit_contact_tech_lead_r_btn:
                experienceStr = "techLead";
                break;
            case R.id.edit_contact_student_r_btn:
                typeOfEmploymentStr = "Студент";
                hideDeveloperFields();
                break;
            case R.id.edit_contact_worker_r_btn:
                typeOfEmploymentStr = "Працює";
                showDeveloperFields();
                break;
            case R.id.edit_contact_add_language:
                addLanguageField();
                break;
            case R.id.edit_contact_add_skill:
                addSkillField();
                break;
        }
    }

    //Method which create menu for toolbar
    @Override
    public void onCreateOptionMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sign_up_by_email_toolbar_menu, menu);
    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_up_by_email_toolbar_send) {
            Toast.makeText(mContext, "Send", Toast.LENGTH_SHORT).show();
        }
    }

    //Method which set photo from gallery
    @Override
    public void setPhoto(String photoUri) {
        //Hide camera icon
        loadPhoto.setVisibility(View.GONE);

        //Show photo in imageVew
        Glide.with(mContext)
                .asBitmap()
                .load(photoUri)
                .into(photo);
        showImagePen();
    }

    @Override
    public void setContact(Contact contact) {
        if (contact != null) {
            setContactDataFromPhoneBook(contact);
        }
    }

    @Override
    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }
}
