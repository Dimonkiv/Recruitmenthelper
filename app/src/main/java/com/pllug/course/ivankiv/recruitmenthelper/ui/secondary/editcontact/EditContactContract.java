package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;

import java.util.List;

interface EditContactContract {

    interface View {
        RecruiterNotes getRecruiterNotes();

        Contact getContact();

        List<Language> getLanguages();

        List<Skill> getSkills();
    }

    interface Presenter {

        Contact getContact(long id);

        RecruiterNotes getRecruiterNote(long recruiterNotesId);

        List<Language> getLanguages(long recruiterNotesId);

        List<Skill> getSkills(long recruiterNotesId);

        boolean checkedEnteredData(String fragmentName);

    }

}
