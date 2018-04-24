package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;

interface EditContactContract {

    interface View {
        RecruiterNotes getRecruiterNotes();

        Contact getContact();
    }

    interface Presenter {

        Contact getContact(long id);

        RecruiterNotes getRecruiterNote(long recruiterNotesId);

        boolean checkedEnteredData();
    }

}
