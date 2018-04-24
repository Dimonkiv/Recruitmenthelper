package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.datepicker.DatePickerFragment;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditContactFragment extends Fragment implements View.OnClickListener, EditContactContract.View {
    private View root;
    private Contact contact;

    private Toolbar toolbar;
    private CircleImageView photo;
    private ImageButton loadPhoto, dateButton;
    private RelativeLayout image_pen;
    private LinearLayout position_container;
    private Spinner jobInterest;
    private TextView date;
    private RadioGroup position_group, type_of_employment_group;
    private RadioButton traineeRBtn, juniorRBtn, middleRBtn, seniorRBtn, studentRBtn, workRBtn;
    private EditText name, phone, email, linkedIn, jobOrUniversity,
            experience, profession, advantages, disadvanteges, notes, skill, language;

    private DatePickerDialog.OnDateSetListener onDatelistener;
    private EditContactPresenter presenter;
    private String photoUri, positionStr, fragmentName;
    private Long id, recruiterNotesId;

    private final int PICK_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_edit_contact, container, false);
        getDataFromActivity();
        initView();
        initPresenter();
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

            //if data was getting from AddContactFragment
            if (fragmentName.equals("AddContactFragment")) {
                contact = (Contact) bundle.getSerializable("contact");
            } //if data was getting from ContactListFragment
            else if (fragmentName.equals("ContactListEditBtn")) {
                id = bundle.getLong("id");
                recruiterNotesId = bundle.getLong("recruiterNotesId");
            }
        }
    }

    //Initialization View
    private void initView() {
        position_container = root.findViewById(R.id.edit_contact_position_container);

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
        experience = root.findViewById(R.id.edit_contact_experience);
        advantages = root.findViewById(R.id.edit_contact_advantages);
        disadvanteges = root.findViewById(R.id.edit_contact_disadvantages);
        notes = root.findViewById(R.id.edit_contact_notes);
        language = root.findViewById(R.id.edit_contact_language);
        skill = root.findViewById(R.id.edit_contact_skill);


        profession = root.findViewById(R.id.edit_contact_profession);
        jobInterest = root.findViewById(R.id.edit_contact_job_interest);


        position_group = root.findViewById(R.id.edit_contact_position);
        type_of_employment_group = root.findViewById(R.id.edit_contact_type_of_employment_group);

        traineeRBtn = root.findViewById(R.id.edit_contact_trainee_r_btn);
        juniorRBtn = root.findViewById(R.id.edit_contact_junior_r_btn);
        middleRBtn = root.findViewById(R.id.edit_contact_middle_r_btn);
        seniorRBtn = root.findViewById(R.id.edit_contact_senior_r_btn);
        studentRBtn = root.findViewById(R.id.edit_contact_student_r_btn);
        workRBtn = root.findViewById(R.id.edit_contact_worker_r_btn);

        image_pen = root.findViewById(R.id.edit_contact_image_pen);

    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new EditContactPresenter(this);
    }

    //Initialization listener
    private void initListener() {
        dateButton.setOnClickListener(this);
        photo.setOnClickListener(this);
        traineeRBtn.setOnClickListener(this);
        juniorRBtn.setOnClickListener(this);
        middleRBtn.setOnClickListener(this);
        seniorRBtn.setOnClickListener(this);
        workRBtn.setOnClickListener(this);
        studentRBtn.setOnClickListener(this);
    }

    //Initialization toolbar
    private void initToolbar() {
        ((SecondaryActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.edit_contact);
        setHasOptionsMenu(true);

        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SecondaryActivity) getActivity()).goToMainActivity(fragmentName);
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
                showContactDataFromDB();
                showRecruiterNotesData();
            }
        }
    }

    //Method, which set data about contact from db
    private void showContactDataFromDB() {

    }

    //Method, which set data about recruiterNotes from db
    private void showRecruiterNotesData() {

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

    //Method, which open MainActivity
    private void openActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    //Method, which hide profession_edit_text, container for position radio button and experience_edit_text
    private void hideDeveloperFields() {
        profession.setVisibility(View.GONE);
        position_container.setVisibility(View.GONE);
        experience.setVisibility(View.GONE);
    }

    //Method, which show profession_edit_text, container for position radio button and experience_edit_text
    private void showDeveloperFields() {
        profession.setVisibility(View.VISIBLE);
        position_container.setVisibility(View.VISIBLE);
        experience.setVisibility(View.VISIBLE);
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

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                date.setText(String.valueOf(dayOfMonth) + " : " + String.valueOf(monthOfYear + 1)
                        + " : " + String.valueOf(year));
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
            if (presenter.checkedEnteredData()) {
                Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
                openActivity();
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
                positionStr = "trainee";
                break;
            case R.id.edit_contact_junior_r_btn:
                positionStr = "junior";
                break;
            case R.id.edit_contact_middle_r_btn:
                positionStr = "middle";
                break;
            case R.id.edit_contact_senior_r_btn:
                positionStr = "senior";
                break;
            case R.id.edit_contact_student_r_btn:
                hideDeveloperFields();
                break;
            case R.id.edit_contact_worker_r_btn:
                showDeveloperFields();
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

        recruiterNotes.setJobOrUniversity(jobOrUniversity.getText().toString());
        recruiterNotes.setProfession(profession.getText().toString());
        recruiterNotes.setPosition(positionStr);
        recruiterNotes.setWorkInterests(jobInterest.getSelectedItem().toString());
        recruiterNotes.setExperience(experience.getText().toString());
        recruiterNotes.setAdvantages(advantages.getText().toString());
        recruiterNotes.setDisadvantages(disadvanteges.getText().toString());
        recruiterNotes.setNotes(notes.getText().toString());
        recruiterNotes.setLanguage(language.getText().toString());
        recruiterNotes.setSkill(skill.getText().toString());

        return recruiterNotes;
    }

    @Override
    public Contact getContact() {
        Contact contact = new Contact();

        contact.setName(name.getText().toString());
        contact.setPhone(phone.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setLinkedInLink(linkedIn.getText().toString());
        contact.setDateOfLatestContact(date.getText().toString());
        contact.setPhotoUri(photoUri);

        return contact;
    }
}
