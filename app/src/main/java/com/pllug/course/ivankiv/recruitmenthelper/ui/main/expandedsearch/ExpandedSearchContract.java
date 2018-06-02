package com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.LanguageForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.SkillForExpandedSearch;

import java.util.List;

public interface ExpandedSearchContract {

    interface View {
        void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeView);
    }

    interface Presenter {

        List<SkillForExpandedSearch> getSkills();

        List<LanguageForExpandedSearch> getLanguages();
    }
}
