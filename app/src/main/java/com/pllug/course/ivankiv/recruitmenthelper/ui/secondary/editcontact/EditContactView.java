package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter.LanguageEditTextAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter.SkillAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.datepicker.DatePickerFragment;

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
    private RadioButton traineeRBtn, juniorRBtn, middleRBtn, seniorRBtn, techLeadRBtn, studentRBtn, workRBtn;
    private EditText name, phone, email, linkedIn, jobOrUniversity, profession, advantages, disadvanteges, notes;


    //Adapter
    private LanguageEditTextAdapter languageEditTextAdapter;
    private SkillAdapter skillAdapter;

    //Other
    private DatePickerDialog.OnDateSetListener onDateListener;
    private EditContactFragment fragment;
    private EditContactPresenter presenter;
    private Context mContext;
    private View root;


    EditContactView(EditContactFragment fragment, EditContactPresenter presenter, Context mContext, View root) {
        this.fragment = fragment;
        this.presenter = presenter;
        this.mContext = mContext;
        this.root = root;
        presenter.setView(this);
        initView();
        initLanguageAdapter();
        initSkillAdapter();
        initListener();
        initToolbar();
        initDateListener();
        initJobInterestSpinner();
        initEditTextWatcher();
        initSpinnerWatcher();


    }

    /*-------------------------------------------Initialization-----------------------------------*/
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
                presenter.onBackButtonClick();
            }
        });
    }

    //Initialization JobInterest Spinner
    private void initJobInterestSpinner() {
        String[] jobInterests = {"Не працює", "Працює", "Шукає роботу", "Зацікавлений в нових можливостях"};

        //Set adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, jobInterests);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jobInterest.setAdapter(adapter);
    }


    /*-----------------------------------------DatePicker-----------------------------------------*/
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
        datePicker.setCallBack(onDateListener);

        if (fragment.getFragmentManager() != null) {
            datePicker.show(fragment.getFragmentManager(), "Date Picker");
        }


    }

    //Initialization DatePickerListener
    private void initDateListener() {
        onDateListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dateStr = String.valueOf(dayOfMonth) + "." + String.valueOf(monthOfYear + 1)
                        + "." + String.valueOf(year);
                date.setText(dateStr);

                presenter.setDateOfLatestConnect(dateStr);
            }
        };
    }

    //Method for choices photo from system gallery
    private void showGallery() {
        fragment.startActivityForResult();
    }


    /*---------------------------------------Language Adapter-------------------------------------*/
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
        languageEditTextAdapter.addLanguageField();
        languageEditTextAdapter.notifyItemInserted(languageEditTextAdapter.getItemCount() - 1);
    }

    //Method which remove language field
    private void removeLanguageField(int position) {
        languageEditTextAdapter.removeField(position);
        languageEditTextAdapter.notifyItemRemoved(position);
    }


    /*--------------------------------------Skill Adapter-----------------------------------------*/
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
        skillAdapter.addSkillField();
        skillAdapter.notifyItemInserted(skillAdapter.getItemCount() - 1);
    }

    //Method which remove skills field
    private void removeSkillField(int position) {
        skillAdapter.removeSkillField(position);
        skillAdapter.notifyItemRemoved(position);
    }


    /*-------------------------------------Text and Spinner Watcher-------------------------------*/
    //Initialization TextWatcher for edit text fields
    private void initEditTextWatcher() {
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        linkedIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setLinkedInLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        profession.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setProfession(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        jobOrUniversity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setJobOrUniversity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        advantages.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setAdvantages(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        disadvanteges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setDisadvantages(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //Initialization spinner watcher
    private void initSpinnerWatcher() {
        jobInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                presenter.setJobInterest(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                presenter.setJobInterest("Не працює");
            }

        });
    }

    //Method, which show image for image container
    private void showImagePen() {
        image_pen.setVisibility(View.VISIBLE);
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
                presenter.setExperience("trainee");
                break;
            case R.id.edit_contact_junior_r_btn:
                presenter.setExperience("junior");
                break;
            case R.id.edit_contact_middle_r_btn:
                presenter.setExperience("middle");
                break;
            case R.id.edit_contact_senior_r_btn:
                presenter.setExperience("senior");
                break;
            case R.id.edit_contact_tech_lead_r_btn:
                presenter.setExperience("techLead");
                break;
            case R.id.edit_contact_student_r_btn:
                presenter.setTypeOfEmployment("Студент");
                hideDeveloperFields();
                break;
            case R.id.edit_contact_worker_r_btn:
                presenter.setTypeOfEmployment("Працює");
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

    @Override
    public void onCreateOptionMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sign_up_by_email_toolbar_menu, menu);
    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_up_by_email_toolbar_send) {
            presenter.setLanguages(languageEditTextAdapter.getLanguages());
            presenter.setSkills(skillAdapter.getSkills());
            presenter.onClickSentButton();
        }
    }


    /*---------------------------------------------Show data--------------------------------------*/
    @Override
    public void showPhone(String phone) {
        this.phone.setText(phone);
    }

    @Override
    public void showEmail(String email) {
        this.email.setText(email);
    }

    @Override
    public void showLinkedInLink(String linkedInLink) {
        this.linkedIn.setText(linkedInLink);
    }

    @Override
    public void showName(String name) {
        this.name.setText(name);
    }

    @Override
    public void showDateOfLatestContact(String dateOfLatestContact) {
        date.setText(dateOfLatestContact);
    }

    @Override
    public void showPhoto(String photoUri) {
        Glide.with(mContext)
                .asBitmap()
                .load(photoUri)
                .into(photo);

        loadPhoto.setVisibility(View.GONE);
        showImagePen();
    }

    @Override
    public void setStudentRadioButton() {
        studentRBtn.setChecked(true);
    }

    @Override
    public void setWorkRadioButton() {
        workRBtn.setChecked(true);
    }

    @Override
    public void showProfession(String profession) {
        this.profession.setText(profession);
    }

    @Override
    public void setTraineeRadioButton() {
        traineeRBtn.setChecked(true);
    }

    @Override
    public void setJuniorRadioButton() {
        juniorRBtn.setChecked(true);
    }

    @Override
    public void setMiddleRadioButton() {
        middleRBtn.setChecked(true);
    }

    @Override
    public void setSeniorRadioButton() {
        seniorRBtn.setChecked(true);
    }

    @Override
    public void setTechLeadRadioButton() {
        techLeadRBtn.setChecked(true);
    }

    @Override
    public void showJobOrUniversity(String jobOrUniversity) {
        this.jobOrUniversity.setText(jobOrUniversity);
    }

    @Override
    public void setJobInterestSpinnerItem(int position) {
        jobInterest.setSelection(position);
    }

    @Override
    public void showAdvantages(String advantages) {
        this.advantages.setText(advantages);
    }

    @Override
    public void showDisadvantages(String disadvantages) {
        this.disadvanteges.setText(disadvantages);
    }

    @Override
    public void showNotes(String notes) {
        this.notes.setText(notes);
    }

    @Override
    public void showLanguages(List<Language> languages) {
        languageEditTextAdapter.addAllLanguages(languages);
    }

    @Override
    public void showSkills(List<Skill> skills) {
        skillAdapter.addAllSkill(skills);
    }

    @Override
    public void hideDeveloperFields() {
        jobOrUniversity.setHint("Місце навчання");
        profession.setVisibility(View.GONE);
        experience_container.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeveloperFields() {
        jobOrUniversity.setHint("Місце роботи");
        profession.setVisibility(View.VISIBLE);
        experience_container.setVisibility(View.VISIBLE);
    }

}
