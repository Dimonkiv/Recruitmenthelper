package com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.LanguageDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.SkillDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.LanguageForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.SkillForExpandedSearch;

import java.util.ArrayList;
import java.util.List;

public class ExpandedSearchPresenter implements ExpandedSearchContract.Presenter {
    private ExpandedSearchContract.Fragment fragment;
    private ExpandedSearchView view;
    private LanguageDao languageDao;
    private SkillDao skillDao;
    private List<LanguageForExpandedSearch> languages;
    private List<SkillForExpandedSearch> skills;

    ExpandedSearchPresenter(ExpandedSearchContract.Fragment fragment) {
        this.fragment = fragment;
        initDatabase();
    }

    private void initDatabase() {
        AppDatabase db = InitDatabase.getInstance().getDatabese();
        skillDao = db.skillDao();
        languageDao = db.languageDao();
    }


    @Override
    public void onLanguageRadioButtonClick() {
        view.setLanguageAdapter();
    }

    @Override
    public void onSkillRadioButtonClick() {
        view.setSkillAdapter();
    }

    @Override
    public void onAdapterItemClick(long id, long recruiterNotesId, String fragmentName) {
        fragment.openDetailFragment(id, recruiterNotesId, fragmentName);
    }

    @Override
    public void loadData() {
        languages = languageDao.getAllLanguageForSearch();
        view.addAllLanguages(languages);
        skills = skillDao.getAllSkillForSearch();
        view.addAllSkills(skills);
    }

    @Override
    public void setView(ExpandedSearchView view) {
        this.view = view;
    }

    @Override
    public void filterLanguages(String text) {
        List<LanguageForExpandedSearch> filteredLanguages = new ArrayList<>();

        for (LanguageForExpandedSearch languageItem : languages) {
            if (languageItem.getLanguage() != null) {
                if (languageItem.getLanguage().toLowerCase().contains(text.toLowerCase())) {
                    filteredLanguages.add(languageItem);
                }
            }
        }

        view.showFilteredLanguages(filteredLanguages);
    }

    @Override
    public void filterSkills(String text) {
        List<SkillForExpandedSearch> filteredSkill = new ArrayList<>();

        for (SkillForExpandedSearch skillItem : skills) {
            if (skillItem.getSkill() != null) {
                if (skillItem.getSkill().toLowerCase().contains(text.toLowerCase())) {
                    filteredSkill.add(skillItem);
                }
            }
        }

        view.showFilteredSkills(filteredSkill);
    }
}
