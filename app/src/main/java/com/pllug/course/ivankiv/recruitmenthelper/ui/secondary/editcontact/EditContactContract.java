package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;

import java.util.List;

interface EditContactContract {

    interface View {

        void onCreateOptionMenu(Menu menu, MenuInflater inflater);

        void onOptionsItemSelected(MenuItem item);

        void showPhone(String phone);

        void showEmail(String email);

        void showLinkedInLink(String linkedInLink);

        void showName(String name);

        void showDateOfLatestContact(String dateOfLatestContact);

        void showPhoto(String photoUri);

        void setStudentRadioButton();

        void setWorkRadioButton();

        void showProfession(String profession);

        void setTraineeRadioButton();

        void setJuniorRadioButton();

        void setMiddleRadioButton();

        void setSeniorRadioButton();

        void setTechLeadRadioButton();

        void showJobOrUniversity(String jobOrUniversity);

        void setJobInterestSpinnerItem(int position);

        void showAdvantages(String advantages);

        void showDisadvantages(String disadvantages);

        void showNotes(String notes);

        void showLanguages(List<Language> languages);

        void showSkills(List<Skill> skills);

        void showDeveloperFields();

        void hideDeveloperFields();

        void showToast(String message);

    }

    interface Presenter {

        void showContact();


        /*------------------------------------------------Set data--------------------------------*/
        void setContact(Contact contact);

        void setId(long id);

        void setName(String name);

        void setPhone(String phone);

        void setEmail(String email);

        void setLinkedInLink(String linkedInLink);

        void setDateOfLatestConnect(String dateOfLatestConnect);

        void setPhotoUri(String photoUri);

        void setRecruiterNotesId(long recruiterNotesId);

        void setTypeOfEmployment(String typeOfEmployment);

        void setProfession(String profession);

        void setExperience(String experience);

        void setJobOrUniversity(String jobOrUniversity);

        void setJobInterest(String jobInterest);

        void setAdvantages(String advantages);

        void setDisadvantages(String disadvantages);

        void setNotes(String notes);

        void setLanguages(List<Language> languages);

        void setSkills(List<Skill> skills);

        void setLanguage(Language language);

        void setSkill(Skill skill);

        void setFragmentName(String fragmentName);

        void setView(EditContactContract.View view);


        /*------------------------------------------------Get data--------------------------------*/
        long getId();

        long getRecruiterNotesId();


        /*-------------------------------Button click handlers------------------------------------*/
        void onClickSentButton();

        void onBackButtonClick();


        void loadData();

    }

    interface Fragment {
        void openGallery();

        void setDataToMainActivity(String fragmentName);

        void showDetailContactFragment(long id, long recruiterNotesId, String fragmentName);

    }

}
