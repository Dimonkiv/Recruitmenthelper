package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact.adapter.LanguageTextViewAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.pllug.course.ivankiv.recruitmenthelper.R.drawable.icon_star_green;

public class DetailContactFragment extends Fragment implements DetailContactContract.View, View.OnClickListener {
    private DetailContactPresenter presenter;
    private LanguageTextViewAdapter adapter;

    private View root;
    private Toolbar toolbar;
    private static final int REQUEST_CODE_CALL_PHONE = 1;
    private static boolean CALL_PHONE_GRANTED = false;

    //View
    private CircleImageView avatar;
    private TextView name, profession, jobOrUniversity, jobInterest, typeOfEmployment, experience, skills,
            dateOfLastConnect, advantages, disadvanteges, notes;
    private ImageButton email, phone, linkedin, selected, edit;
    private LinearLayout skillsContainer, languagesContainer, recruiterNotesContainer, dateOfLastConnectContainer,
            advantagesContainer, disadvantagesContainer, notesContainer, professionContainer;
    private RecyclerView langugeRecycler;

    private long id, recruiterNotesId;
    private String fragmentName;
    private Contact contact;
    private RecruiterNotes recruiterNotes;
    private List<Language> languages;
    private List<Skill> skillList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_contact, container, false);

        initView();
        initAdapter();
        initListener();
        getDataFromSecondaryActivity();
        initPresenter();
        getDataFromPresenter();
        initToolbar();
        setDataIntoView();

        return root;
    }

    //Initialization view
    private void initView() {
        toolbar = root.findViewById(R.id.detail_contact_toolbar);
        //CircularImageView
        avatar = root.findViewById(R.id.detail_contact_avatar);
        //TextView
        name = root.findViewById(R.id.detail_contact_name);
        profession = root.findViewById(R.id.detail_contact_profession);
        typeOfEmployment = root.findViewById(R.id.detail_contact_type_of_employment);
        jobOrUniversity = root.findViewById(R.id.detail_contact_job_or_university);
        jobInterest = root.findViewById(R.id.detail_contact_job_interest);
        experience = root.findViewById(R.id.detail_contact_experience);
        skills = root.findViewById(R.id.detail_contact_skills_text);
        dateOfLastConnect = root.findViewById(R.id.detail_contact_date_of_last_connect);
        advantages = root.findViewById(R.id.detail_contact_advantages);
        disadvanteges = root.findViewById(R.id.detail_contact_disadvantages);
        notes = root.findViewById(R.id.detail_contact_notes);
        //ImageButton
        email = root.findViewById(R.id.detail_contact_email);
        phone = root.findViewById(R.id.detail_contact_phone);
        linkedin = root.findViewById(R.id.detail_contact_linkedin);
        selected = root.findViewById(R.id.detail_contact_selected);
        edit = root.findViewById(R.id.detail_contact_edit);
        //LinearLayout
        skillsContainer = root.findViewById(R.id.detail_contact_skills_container);
        languagesContainer = root.findViewById(R.id.detail_contact_languages_container);
        recruiterNotesContainer = root.findViewById(R.id.detail_contact_recruiter_notes_container);
        advantagesContainer = root.findViewById(R.id.detail_contact_advantages_container);
        disadvantagesContainer = root.findViewById(R.id.detail_contact_disadvantages_container);
        notesContainer = root.findViewById(R.id.detail_contact_notes_container);
        professionContainer = root.findViewById(R.id.detail_contact_profession_container);
        dateOfLastConnectContainer = root.findViewById(R.id.detail_contact_date_of_last_connect_container);
        //RecyclerView
        langugeRecycler = root.findViewById(R.id.detail_contact_language_recycler_view);

    }

    //Initialization language adapter
    private void initAdapter() {
        languages = new ArrayList<>();
        adapter = new LanguageTextViewAdapter(languages);

        langugeRecycler.setAdapter(adapter);
        langugeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    //Init Listener
    private void initListener() {
        email.setOnClickListener(this);
        phone.setOnClickListener(this);
        linkedin.setOnClickListener(this);
        selected.setOnClickListener(this);
        edit.setOnClickListener(this);

    }
    //Method, which get id, recruiterNotesId from SecondaryActivity
    private void getDataFromSecondaryActivity() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            id = bundle.getLong("id", 0);
            recruiterNotesId = bundle.getLong("recruiterNotesId", 0);
            fragmentName = bundle.getString("fragmentName");
        }

    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new DetailContactPresenter(this, id, recruiterNotesId);
    }

    //Method, which get contact, recruiterNotes, languageList, skillList from presenter
    private void getDataFromPresenter() {
        //contact
        contact = presenter.getContact();
        //recruiterNotes
        recruiterNotes = presenter.getRecruiterNotes();
        //languages
        languages.addAll(presenter.getLanguages());
        //skills
        skillList = new ArrayList<>();
        skillList.addAll(presenter.getSkills());
    }

    //Initialization toolbar
    private void initToolbar() {
        ((SecondaryActivity) getActivity()).setSupportActionBar(toolbar);
        if (contact != null && contact.getName() != null) {
            ((SecondaryActivity) getActivity()).getSupportActionBar().setTitle(contact.getName());
        }
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

    //Method, which set data into View
    private void setDataIntoView() {
        setDataIntoFirstContainer();
        setDataIntoSkillsContainer();
        setDataIntoLanguageContainer();
        setDataIntoRecruiterNotesContainer();
    }

    //Method, which set data into firsContainer
    private void setDataIntoFirstContainer() {

        //If PhotoUri is not equals null and not empty
        //then set up photo into avatar
        if (contact != null && contact.getPhotoUri() != null && !contact.getPhotoUri().isEmpty()) {
            Glide.with(this)
                    .load(contact.getPhotoUri())
                    .into(avatar);
        }

        //If Name is not equals null and not empty
        //then set text(Name) into name
        if (contact != null && contact.getName() != null && !contact.getName().isEmpty()) {
            name.setText(contact.getName());
        }

        //If TypeOfEmployment is not equals null and not empty
        if (recruiterNotes.getTypeOfEmployment() != null && !recruiterNotes.getTypeOfEmployment().isEmpty()) {

            //If TypeOfEmployment is equals Студент
            if (recruiterNotes.getTypeOfEmployment().equals("Студент")) {

                //then set text Студент into typeOfEmployment(TextView)
                //show typeOfEmployment and hide profession container
                typeOfEmployment.setText("Студент");
                typeOfEmployment.setVisibility(View.VISIBLE);
                professionContainer.setVisibility(View.GONE);

            }
            // else if TypeOfEmployment is equals Працює
            else if (recruiterNotes.getTypeOfEmployment().equals("Працює")) {

                //then check is not It profession empty and equals null
                if (recruiterNotes.getProfession() != null && !recruiterNotes.getProfession().isEmpty()) {

                    //show professionContainer, hide typeOfEmployment
                    //and set profession text into profession
                    professionContainer.setVisibility(View.VISIBLE);
                    typeOfEmployment.setVisibility(View.GONE);
                    profession.setText(recruiterNotes.getProfession());

                    //If Experience is not equals null and not empty
                    if (recruiterNotes.getExperience() != null && !recruiterNotes.getExperience().isEmpty()) {

                        //than set experience text into experience
                        experience.setText(recruiterNotes.getExperience());
                    }
                }
            }
        }

        //If JobOrUniversity is not equals null and not empty
        //than set jobOrUniversity text into jobOrUniversity
        if (recruiterNotes.getJobOrUniversity() != null && !recruiterNotes.getJobOrUniversity().isEmpty()) {
            jobOrUniversity.setText(recruiterNotes.getJobOrUniversity());
        } else {
            jobOrUniversity.setVisibility(View.GONE);
        }

        //If JobInterest is not equals null and not empty
        //than set JobInterest text into JobInterest
        if (recruiterNotes.getJobInterests() != null && !recruiterNotes.getJobInterests().isEmpty()) {
            jobInterest.setText(recruiterNotes.getJobInterests());
        }

        //Set selected icon
        //if selected is equal true
        //set image star active for selected_image_button
        if (contact.isSelected()) {
            selected.setImageResource(R.drawable.icon_star_actived_green);
        }
        //else set image star for selected_image_button
        else {
            selected.setImageResource(R.drawable.icon_star_green);
        }

    }

    //Method, which set skillList into skillContainer
    private void setDataIntoSkillsContainer() {
        StringBuilder builder = new StringBuilder();
        String skillStr = "";

        //If skillList is not equal null
        if (skillList != null) {

            //if is at least ane skill
            if (skillList.get(0) != null && skillList.get(0).getSkill() != null) {

                //If is one skill
                //then set him into skillStr
                if (skillList.size() <= 1) {
                    skillStr = skillList.get(0).getSkill();
                }
                //Else if skills are more than one
                //then browse all the items in the skillList
                else {
                    for (Skill skill : skillList) {

                        //if skill is equal the last element in skillList
                        //append to string builder skill(String)
                        if (skill.equals(skillList.get(skillList.size() - 1))) {
                            builder.append(skill.getSkill());
                        }
                        //else append to string builder skill(String) and ", "
                        else {
                            builder.append(skill.getSkill()).append(", ");
                        }
                    }

                    skillStr = builder.toString();
                }

            }
            //else hide skillsContainer
            else {
                skillsContainer.setVisibility(View.GONE);
            }
        } else {
            skillsContainer.setVisibility(View.GONE);
        }

        //if skillStr is not empty
        //then set skillStr into skills
        if (!skillStr.isEmpty()) {
            skills.setText(skillStr);
        }

    }

    //Method, which set languages into languages container(RecyclerView)
    private void setDataIntoLanguageContainer() {
        //if language list is not empty
        //then update languge adapter
        if (languages != null && languages.size() > 0 ) {
            adapter.notifyDataSetChanged();
        }
        //else hide languageContainer
        else {
            languagesContainer.setVisibility(View.GONE);
        }
    }

    //Method, which set data into recruiterNotes container
    private void setDataIntoRecruiterNotesContainer() {
        //if dateOfLastConnect, advantages, disadvantages and not are not equal null
        if (contact.getDateOfLatestContact() != null && recruiterNotes.getAdvantages() != null && recruiterNotes.getDisadvantages() != null && recruiterNotes.getNotes() != null) {

            //if dateOfLastConnect, advantages, disadvantages and not are  empty
            if (contact.getDateOfLatestContact().isEmpty() && recruiterNotes.getAdvantages().isEmpty() && recruiterNotes.getDisadvantages().isEmpty() && recruiterNotes.getNotes().isEmpty()) {
                //then hide notes container
                recruiterNotesContainer.setVisibility(View.GONE);

            } else {
                //if dateOfLatestContact is not Empty and not equal null
                //then set dateOfLastContact(String) into dateOfLastContact(TextView)
                if (contact.getDateOfLatestContact() != null && !contact.getDateOfLatestContact().isEmpty()) {
                    dateOfLastConnect.setText(contact.getDateOfLatestContact());
                }
                //else hide dateOfLastConnectContainer
                else {
                    dateOfLastConnectContainer.setVisibility(View.GONE);
                }

                //if advantages(string) is not Empty and not equal null
                //then set advantages(String) into advantages(TextView)
                if (recruiterNotes.getAdvantages() != null && !recruiterNotes.getAdvantages().isEmpty()) {
                    advantages.setText(recruiterNotes.getAdvantages());
                }
                //else hide advantages
                else {
                    advantagesContainer.setVisibility(View.GONE);
                }

                //if disadvantages(string) is not Empty and not equal null
                //then set disadvantages(String) into disadvantages(TextView)
                if (recruiterNotes.getDisadvantages() != null && !recruiterNotes.getDisadvantages().isEmpty()) {
                    disadvanteges.setText(recruiterNotes.getDisadvantages());
                }
                //else hide disadvantages
                else {
                    disadvantagesContainer.setVisibility(View.GONE);
                }

                //if notes(string) is not Empty and not equal null
                //then set notes(String) into disadvantages(TextView)
                if (recruiterNotes.getNotes() != null && !recruiterNotes.getNotes().isEmpty()) {
                    notes.setText(recruiterNotes.getNotes());
                }
                //else hide notes
                else {
                    notesContainer.setVisibility(View.GONE);
                }

            }
        }
        //else hide recruiterNotesContainer
        else {
            recruiterNotesContainer.setVisibility(View.GONE);
        }
    }

    //Method, which open fragment for sending email
    private void openSendEmailFragment() {
        if (contact.getEmail() != null && !contact.getEmail().isEmpty()) {
            ((SecondaryActivity) getActivity()).showSendEmailFragment(contact.getEmail(), id, recruiterNotesId, fragmentName);
        } else {
            Toast.makeText(getActivity(), "Відсутній email!", Toast.LENGTH_SHORT).show();
        }
    }

    //Method, which doing call
    private void callByPhone() {
        String toDial="tel:"+ contact.getPhone();
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(toDial)));
    }

    private void openLinkedinProfile() {
    }

    //Method, which add contact to selected
    private void selectContact() {
        if (contact.isSelected()) {
            selected.setImageResource(R.drawable.icon_star_green);
            presenter.updateSelected(false, id);
            Toast.makeText(getActivity(), "Додано з вибраних", Toast.LENGTH_SHORT).show();
        } else {
            selected.setImageResource(R.drawable.icon_star_actived_green);
            presenter.updateSelected(true, id);
            Toast.makeText(getActivity(), "Видалено з вибраних", Toast.LENGTH_SHORT).show();
        }
    }

    //Method, which open EditContactFragment
    private void editContact() {
        ((SecondaryActivity)getActivity()).showEditContactFragment(id, recruiterNotesId, "DetailContact");
    }

    //Method for set permission CALL_PHONE
    private void setPermission() {
        //get permission from manifest
        int hasReadContactPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

        //If device has API to 23, set permission
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            CALL_PHONE_GRANTED = true;
        } else {
            //Call Compat Activity for set permission
            requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL_PHONE);
        }

        //If permission allow
        if (CALL_PHONE_GRANTED) {
            callByPhone();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CALL_PHONE_GRANTED = true;
                }
        }

        if (CALL_PHONE_GRANTED) {
            callByPhone();
        } else {
            Toast.makeText(getActivity(), "Потрібно дозволити доступ до контактів", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_contact_email:
                openSendEmailFragment();
                break;
            case R.id.detail_contact_phone:
                setPermission();
                break;
            case R.id.detail_contact_linkedin:
                openLinkedinProfile();
                break;
            case R.id.detail_contact_selected:
                selectContact();
                break;
            case R.id.detail_contact_edit:
                editContact();
                break;


        }
    }

}
