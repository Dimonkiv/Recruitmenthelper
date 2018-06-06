package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact.adapter.LanguageTextViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class DetailContactView implements DetailContactContract.View, View.OnClickListener {
    //View
    private Toolbar toolbar;
    private CircleImageView avatar;
    private TextView name, profession, jobOrUniversity, jobInterest, typeOfEmployment, experience, skills,
            dateOfLastConnect, advantages, disadvanteges, notes;
    private ImageButton email, phone, linkedin, selected, edit;
    private LinearLayout skillsContainer, languagesContainer, recruiterNotesContainer, dateOfLastConnectContainer,
            advantagesContainer, disadvantagesContainer, notesContainer, professionContainer;
    private RecyclerView languageRecycler;

    private DetailContactPresenter presenter;
    private Context mContext;
    private View root;
    private LanguageTextViewAdapter adapter;


    DetailContactView(DetailContactPresenter presenter, Context mContext, View root) {
        this.presenter = presenter;
        this.mContext = mContext;
        this.root = root;
        presenter.setView(this);
        initView();
        initAdapter();
        initListener();
        initToolbar();
    }

    /*---------------------------------Initialization---------------------------------------------*/
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
        languageRecycler = root.findViewById(R.id.detail_contact_language_recycler_view);

    }

    //Initialization language adapter
    private void initAdapter() {
        adapter = new LanguageTextViewAdapter();
        languageRecycler.setAdapter(adapter);
        languageRecycler.setLayoutManager(new LinearLayoutManager(mContext));

    }

    //Init Listener
    private void initListener() {
        email.setOnClickListener(this);
        phone.setOnClickListener(this);
        linkedin.setOnClickListener(this);
        selected.setOnClickListener(this);
        edit.setOnClickListener(this);

    }

    //Initialization toolbar
    private void initToolbar() {
        ((SecondaryActivity) mContext).setSupportActionBar(toolbar);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_contact_email:
                presenter.onEmailButtonClick();
                break;
            case R.id.detail_contact_phone:
                presenter.onPhoneButtonClick();
                break;
            case R.id.detail_contact_linkedin:

                break;
            case R.id.detail_contact_selected:
                presenter.onSelectedButtonClick();
                break;
            case R.id.detail_contact_edit:
                presenter.onEditButtonClick();
                break;


        }
    }

    /*---------------------------------Show data--------------------------------------------------*/
    @Override
    public void showToolbarTitle(String title) {
        ((SecondaryActivity) mContext).getSupportActionBar().setTitle(title);
    }

    @Override
    public void showPhoto(String imageURI) {
        Glide.with(mContext)
                .load(imageURI)
                .into(avatar);
    }

    @Override
    public void showName(String name) {
        this.name.setText(name);
    }

    @Override
    public void showTypeOfEmployment(String typeOfEmployment) {
        this.typeOfEmployment.setText(typeOfEmployment);
    }

    @Override
    public void showProfession(String profession) {
        this.profession.setText(profession);
    }

    @Override
    public void showExperience(String experience) {
        this.experience.setText(experience);
    }

    @Override
    public void showJobOrUniversity(String jobOrUniversity) {
        this.jobOrUniversity.setText(jobOrUniversity);
    }

    @Override
    public void showJobInterest(String jobInterest) {
        this.jobInterest.setText(jobInterest);
    }

    @Override
    public void setSelectedButton(int imgId) {
        selected.setImageResource(imgId);
    }

    @Override
    public void showSkills(String skills) {
        this.skills.setText(skills);
    }

    @Override
    public void showLanguages(List<Language> languages) {
        adapter.addAllLanguages(languages);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDateOfLastConnect(String dateOfLastConnect) {
        this.dateOfLastConnect.setText(dateOfLastConnect);
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
    public void showTypeOfEmploymentContainer() {
        typeOfEmployment.setVisibility(View.VISIBLE);
    }

    /*---------------------------------Hide containers--------------------------------------------*/
    @Override
    public void hideTypeOfEmploymentContainer() {
        typeOfEmployment.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProfessionContainer() {
        professionContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideJobOrUniversityContainer() {
        jobOrUniversity.setVisibility(View.GONE);
    }

    @Override
    public void hideSkillsContainer() {
        skillsContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideLanguagesContainer() {
        languagesContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideRecruiterNotesContainer() {
        recruiterNotesContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideDateOfLastConnectContainer() {
        dateOfLastConnectContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideAdvantagesContainer() {
        advantagesContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideDisadvantagesContainer() {
        disadvantagesContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideNotesContainer() {
        notesContainer.setVisibility(View.GONE);
    }


}
