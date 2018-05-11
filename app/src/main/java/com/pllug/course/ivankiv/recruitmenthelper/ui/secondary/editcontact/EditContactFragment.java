package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter.LanguageEditTextAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter.SkillAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.datepicker.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditContactFragment extends Fragment implements View.OnClickListener, EditContactContract.View {
    private View root;

    private Contact contact;

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

    private DatePickerDialog.OnDateSetListener onDatelistener;
    private EditContactPresenter presenter;

    private LanguageEditTextAdapter languageEditTextAdapter;
    private SkillAdapter skillAdapter;

    private String photoUri, experienceStr, fragmentName, typeOfEmploymentStr;
    private Long id, recruiterNotesId;

    private final int PICK_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_edit_contact, container, false);
        getDataFromActivity();
        initView();
        initPresenter();
        initLanguageAdapter();
        initSkillAdapter();
        initListener();
        initToolbar();
        initDateListener();
        initJobInterestSpinner();
        setData();

        return root;
    }


    //Method, which get data from MainActivity
    private void getDataFromActivity() {
        Bundle bundle = getArguments();

        //if bundle and fragment name != null
        if (bundle != null && bundle.getString("fragmentName") != null) {
            //get fragmentName from SecondaryActivity
            fragmentName = bundle.getString("fragmentName");

            //if data was getting from PhoneContactFragment
            if (fragmentName.equals("PhoneContactFragment")) {
                contact = (Contact) bundle.getSerializable("contact");
            } //if data was getting from ContactListFragment
            else if (fragmentName.equals("ContactListEditBtn")) {
                id = bundle.getLong("id");
                recruiterNotesId = bundle.getLong("recruiterNotesId");
            }
            //else if data was getting from DetailContactFragment
            else if (fragmentName.equals("DetailContact")) {
                id = bundle.getLong("id");
                recruiterNotesId = bundle.getLong("recruiterNotesId");
            }
        }
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

    //Initialization presenter
    private void initPresenter() {
        presenter = new EditContactPresenter(this);
    }

    //Initialization language adapter
    private void initLanguageAdapter() {
        languageEditTextAdapter = new LanguageEditTextAdapter(getActivity());
        languageRecycler.setAdapter(languageEditTextAdapter);
        languageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        addLanguageField();
    }

    //Method, which add editText for entering language, spinner for choosing language level
    // and imageButton for removing this fields, to languageRecyclerView
    private void addLanguageField() {
        languageEditTextAdapter.addLanguage();
        languageEditTextAdapter.notifyItemInserted(languageEditTextAdapter.getItemCount() - 1);
    }

    //Method, which save all data from language fields in languages and languageLevels(ArrayList)
    private List<Language> getLanguageData() {
        List<Language> languages = new ArrayList<>();

        for (LanguageEditTextAdapter.LanguageHolder holder : languageEditTextAdapter.languageHolders) {
            Language language = new Language();
            language.setLanguage(holder.editText.getText().toString());
            language.setLanguageLevel(holder.languageLevel.getSelectedItem().toString());
            if (fragmentName != null && !fragmentName.equals("PhoneContactFragment")) {
                language.setRecruiterNotesId(recruiterNotesId);
            }
            languages.add(language);
        }

        return languages;
    }

    //Initialization skill adapter
    private void initSkillAdapter() {
        skillAdapter = new SkillAdapter();
        skillRecycler.setAdapter(skillAdapter);
        skillRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        addSkillField();
    }

    //Method, which add editText for entering skill and imageButton for removing this field
    //to skillRecyclerView
    private void addSkillField() {
        skillAdapter.addSkill();
        skillAdapter.notifyItemInserted(languageEditTextAdapter.getItemCount() - 1);
    }

    //Method, which save skills from skillFields into skills(ArrayList)
    private List<Skill> getSkillData() {
        List<Skill> skills = new ArrayList<>();

        for (SkillAdapter.SkillHolder holder : skillAdapter.skillHolders) {
            Skill skill = new Skill();
            skill.setSkill(holder.skillEditText.getText().toString());
            if (fragmentName != null && !fragmentName.equals("PhoneContactFragment")) {
                skill.setRecruiterNotesId(recruiterNotesId);
            }
            skills.add(skill);
        }

        return skills;
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
        ((SecondaryActivity) getActivity()).setSupportActionBar(toolbar);

        //Set title
        ((SecondaryActivity) getActivity()).getSupportActionBar().setTitle("Редагування контакту");
        setHasOptionsMenu(true);

        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLastFragment();
            }
        });
    }

    //Method, which show image for image container
    private void showImagePen() {
        image_pen.setVisibility(View.VISIBLE);
    }

    //Initialization JobInterest Spinner
    private void initJobInterestSpinner() {
        String[] jobInterests = {"Працює", "Шукає роботу", "Зацікавлений в нових можливостях"};

        //Set adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, jobInterests);
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

    //Method, which set data from db into edit fields
    private void setData() {
        //if contact was getting beforehand (was getting from phone book)
        //then set data from this contact into edit fields
        if (contact != null) {
            setContactDataFromPhoneBook();
        }
        //else if there are id and recruiterNotesId and they doesn't equals 0
        //then load data from DB and set their into edit fields
        else if (id != null && recruiterNotesId != null) {
            if (recruiterNotesId != 0 && id != 0) {
                setContactDataIntoFieldsFromDB();
                setRecruiterNotesDataIntoFieldsFromDB();
                setLanguageDataIntoFieldsFromDb();
                setSkillDataIntoFieldsFromDB();
            }
        }
    }

    //Method, which set data about contact from db
    private void setContactDataIntoFieldsFromDB() {
        Contact contact = presenter.getContact(id);

        if (contact.getPhotoUri() != null && !contact.getPhotoUri().isEmpty()) {
            photoUri = contact.getPhotoUri();

            Glide.with(getActivity())
                    .load(photoUri)
                    .into(photo);
        }

        if (contact.getName() != null && !contact.getName().isEmpty()) {
            name.setText(contact.getName());
        }

        if (contact.getEmail() != null && !contact.getEmail().isEmpty()) {
            email.setText(contact.getEmail());
        }

        if (contact.getPhone() != null && !contact.getPhone().isEmpty()) {
            phone.setText(contact.getPhone());
        }

        if (contact.getLinkedInLink() != null && !contact.getLinkedInLink().isEmpty()) {
            linkedIn.setText(contact.getLinkedInLink());
        }

        if (contact.getDateOfLatestContact() != null && !contact.getDateOfLatestContact().isEmpty()) {
            date.setText(contact.getDateOfLatestContact());
        }

        if (contact.getLinkedInLink() != null && !contact.getLinkedInLink().isEmpty()) {
            linkedIn.setText(contact.getLinkedInLink());
        }

    }

    //Method, which set data about recruiterNotes from db
    private void setRecruiterNotesDataIntoFieldsFromDB() {
        RecruiterNotes recruiterNotes = presenter.getRecruiterNote(recruiterNotesId);

        //Set typeOfEmployment
        if (recruiterNotes.getTypeOfEmployment() != null && !recruiterNotes.getTypeOfEmployment().isEmpty()) {

            if (recruiterNotes.getTypeOfEmployment().equals("Студент")) {
                studentRBtn.setChecked(true);
                profession.setVisibility(View.GONE);
                experience_container.setVisibility(View.GONE);
            } else {

                workRBtn.setChecked(true);
                profession.setVisibility(View.VISIBLE);
                experience_container.setVisibility(View.VISIBLE);

                //Set profession
                if (recruiterNotes.getProfession() != null && !recruiterNotes.getProfession().isEmpty()) {
                    profession.setText(recruiterNotes.getProfession());
                }

                //Set experience
                if (recruiterNotes.getExperience() != null && !recruiterNotes.getExperience().isEmpty()) {
                    switch (recruiterNotes.getExperience()) {
                        case "trainee":
                            traineeRBtn.setChecked(true);
                            break;
                        case "junior":
                            juniorRBtn.setChecked(true);
                            break;
                        case "middle":
                            middleRBtn.setChecked(true);
                            break;
                        case "senior":
                            seniorRBtn.setChecked(true);
                            break;
                        case "techLead":
                            techLeadRBtn.setChecked(true);
                            break;
                    }
                }
            }
        }

        //Set jobOrUniversity
        if (recruiterNotes.getJobOrUniversity() != null && !recruiterNotes.getJobOrUniversity().isEmpty()) {
            jobOrUniversity.setText(recruiterNotes.getJobOrUniversity());
        }

        //Set jobInterest
        if (recruiterNotes.getJobInterests() != null && !recruiterNotes.getJobInterests().isEmpty()) {
            int pos = 1;

            switch (recruiterNotes.getJobInterests()) {
                case "Працює":
                    pos = 1;
                    break;
                case "Шукає роботу":
                    pos = 2;
                    break;
                case "Зацікавлений в нових можливостях":
                    pos = 3;
                    break;
                default:
                    pos = 2;
                    break;
            }

            jobInterest.setSelection(pos);
        }

        //Set advantages
        if (recruiterNotes.getAdvantages() != null && !recruiterNotes.getAdvantages().isEmpty()) {
            advantages.setText(recruiterNotes.getAdvantages());
        }

        //Set disadvateges
        if (recruiterNotes.getDisadvantages() != null && !recruiterNotes.getDisadvantages().isEmpty()) {
            disadvanteges.setText(recruiterNotes.getDisadvantages());
        }

        //Set notes
        if (recruiterNotes.getNotes() != null && !recruiterNotes.getNotes().isEmpty()) {
            notes.setText(recruiterNotes.getNotes());
        }
    }

    //Method, which set language into language fields(recycler view)
    private void setLanguageDataIntoFieldsFromDb() {
        languageEditTextAdapter.addAllLanguages(presenter.getLanguages(recruiterNotesId));
    }

    //Method, which set skill into skill fields(recycler view)
    private void setSkillDataIntoFieldsFromDB() {
        skillAdapter.addAllSkill(presenter.getSkills(recruiterNotesId));
    }

    //Method, which set data from telephone book into edit form
    private void setContactDataFromPhoneBook() {
        if (contact.getPhotoUri() != null) {
            photoUri = contact.getPhotoUri();
            Glide.with(this)
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

    //Method for choices photo from system gallery
    private void showGallery() {
        //Open system gallery
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //Type of received object - image
        photoPickerIntent.setType("image/*");
        //Start activity for result
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    //Method, which show fragment which was before
    private void openLastFragment() {
        if (fragmentName.equals("DetailContact")) {
            showDetailContactFragment();
        } else {
            openMainActivity();
        }
    }

    //Method, which open DetailContactFragment
    private void showDetailContactFragment() {
        ((SecondaryActivity) getActivity()).showDetailContactFragment(id, recruiterNotesId, fragmentName);
    }

    //Method, which open MainActivity
    private void openMainActivity() {
        ((SecondaryActivity) getActivity()).goToMainActivity(fragmentName);
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
        datePicker.show(getFragmentManager(), "Date Picker");


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
            if (presenter.checkedEnteredData(fragmentName)) {
                Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
                openLastFragment();
            }

        }
        return true;
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

    //Method, which get photoUri from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If received cod is equal to PICK_IMAGE code
        if (requestCode == PICK_IMAGE) {

            //Hide camera icon
            loadPhoto.setVisibility(View.GONE);
            //Get uri from choices image
            photoUri = data.getData().toString();
            //Show photo in imageVew
            Glide.with(this)
                    .asBitmap()
                    .load(photoUri)
                    .into(photo);
            showImagePen();
        }
    }

    @Override
    public RecruiterNotes getRecruiterNotes() {
        RecruiterNotes recruiterNotes = new RecruiterNotes();

        //Id data must be update, then set recruiterNotesId
        if (fragmentName != null && !fragmentName.equals("PhoneContactFragment")) {
            recruiterNotes.setId(recruiterNotesId);
        }

        recruiterNotes.setTypeOfEmployment(typeOfEmploymentStr);
        recruiterNotes.setProfession(profession.getText().toString());
        recruiterNotes.setExperience(experienceStr);
        recruiterNotes.setJobOrUniversity(jobOrUniversity.getText().toString());
        recruiterNotes.setJobInterests(jobInterest.getSelectedItem().toString());
        recruiterNotes.setAdvantages(advantages.getText().toString());
        recruiterNotes.setDisadvantages(disadvanteges.getText().toString());
        recruiterNotes.setNotes(notes.getText().toString());


        return recruiterNotes;
    }

    @Override
    public Contact getContact() {
        Contact contact = new Contact();

        //Id data must be update, then set id and recruiterNotesId
        if (fragmentName != null && !fragmentName.equals("PhoneContactFragment")) {
            contact.setId(id);
            contact.setRecruiterNotesId(recruiterNotesId);
        }

        contact.setName(name.getText().toString());
        contact.setPhone(phone.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setLinkedInLink(linkedIn.getText().toString());
        contact.setDateOfLatestContact(date.getText().toString());

        //set photoUri
        if (photoUri == null || photoUri.isEmpty()) {
            contact.setPhotoUri(photoUri);
        } else {
            contact.setPhotoUri(photoUri);
        }
        return contact;
    }

    @Override
    public List<Language> getLanguages() {

        return getLanguageData();
    }

    @Override
    public List<Skill> getSkills() {
        return getSkillData();
    }
}
