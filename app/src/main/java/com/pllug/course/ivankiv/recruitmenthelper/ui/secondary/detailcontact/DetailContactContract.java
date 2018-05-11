package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;

import java.util.List;

public interface DetailContactContract {

    interface View {

    }

    interface Presenter {
        Contact getContact();

        RecruiterNotes getRecruiterNotes();

        List<Language> getLanguages();

        List<Skill> getSkills();

        void updateSelected(boolean state, long id);
    }
}
