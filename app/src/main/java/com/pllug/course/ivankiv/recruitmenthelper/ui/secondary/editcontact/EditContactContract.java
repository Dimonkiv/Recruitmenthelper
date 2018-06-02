package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

interface EditContactContract {

    interface View {

        void onCreateOptionMenu(Menu menu, MenuInflater inflater);

        void onOptionsItemSelected(MenuItem item);

        void setPhoto(String photoUri);

        void setContact(Contact contact);

        void setFragmentName(String fragmentName);
    }

    interface Presenter {


    }

    interface Fragment {
        void startActivityForResult(Intent intent);

        void setDataToMainActivity(String fragmentName);

        //Set Contact data
        void setName(String name);

        void setPhone(String phone);

        void setEmail(String email);

        void setLinkedInLink(String linkedInLink);

        void setDateOfLatestConnect(String dateOfLatestConnect);

        void setPhotoUri(String photoUri);

        //set RecruiterNotesData
        void setTypeOfEmployment(String typeOfEmployment);

        void setProfession(String profession);

        void setExperience(String experience);

        void setJobOrUniversity(String jobOrUniversity);

        void setJobInterest(String jobInterest);

        void setAdvantages(String advantages);

        void setDisadvantages(String disadvantages);
    }

}
