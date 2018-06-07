package com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch;

import com.pllug.course.ivankiv.recruitmenthelper.data.model.LanguageForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.SkillForExpandedSearch;

import java.util.List;

public interface ExpandedSearchContract {

    interface View {
        void setLanguageAdapter();

        void addAllLanguages(List<LanguageForExpandedSearch> languages);

        void showFilteredLanguages(List<LanguageForExpandedSearch> filteredLanguages);

        void setSkillAdapter();

        void addAllSkills(List<SkillForExpandedSearch> skills);

        void showFilteredSkills(List<SkillForExpandedSearch> filteredSkills);


    }

    interface Fragment {
        void openDetailFragment(long id, long recruiterNotesId, String fragmentName);
    }

    interface Presenter {

        void onLanguageRadioButtonClick();

        void onSkillRadioButtonClick();

        void onAdapterItemClick(long id, long recruiterNotesId, String fragmentName);

        void loadData();

        void setView(ExpandedSearchView view);

        void filterLanguages(String text);

        void filterSkills(String text);

    }
}
