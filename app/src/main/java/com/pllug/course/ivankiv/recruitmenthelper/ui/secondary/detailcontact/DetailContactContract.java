package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;


import java.util.List;

interface DetailContactContract {

    interface View {

        /*---------------------------------Show data----------------------------------------------*/
        void showToolbarTitle(String title);

        void showPhoto(String imageURI);

        void showName(String name);

        void showTypeOfEmployment(String typeOfEmployment);

        void showProfession(String profession);

        void showExperience(String experience);

        void showJobOrUniversity(String jobOrUniversity);

        void showJobInterest(String jobInterest);

        void setSelectedButton(int imgId);

        void showSkills(String skills);

        void showLanguages(List<Language> languages);

        void showDateOfLastConnect(String dateOfLastConnect);

        void showAdvantages(String advantages);

        void showDisadvantages(String disadvantages);

        void showNotes(String notes);

        void showTypeOfEmploymentContainer();

        void showToast(String message);

        /*---------------------------------Hide containers----------------------------------------*/
        void hideTypeOfEmploymentContainer();

        void hideProfessionContainer();

        void hideJobOrUniversityContainer();

        void hideSkillsContainer();

        void hideLanguagesContainer();

        void hideRecruiterNotesContainer();

        void hideDateOfLastConnectContainer();

        void hideAdvantagesContainer();

        void hideDisadvantagesContainer();

        void hideNotesContainer();

    }

    interface Fragment {

        void showSendEmailFragment(String email, long id, long recruiterNotesId, String fragmentName);

        void setPermission(String phone);

        void showEditContactFragment(long id, long recruiterNotesId);

        void goToMainActivity(String fragmentName);

    }

    interface Presenter {

        /*---------------------------------Set data-----------------------------------------------*/
        void setView(DetailContactContract.View view);

        void setId(long id);

        void setRecruiterNotesId(long recruiterNotesId);

        void setFragmentName(String fragmentName);

        String getPhone();

        void loadData();

        /*---------------------------------Click handlers-----------------------------------------*/
        void onEmailButtonClick();

        void onPhoneButtonClick();

        void onSelectedButtonClick();

        void onEditButtonClick();

        void onBackButtonClick();
    }
}
