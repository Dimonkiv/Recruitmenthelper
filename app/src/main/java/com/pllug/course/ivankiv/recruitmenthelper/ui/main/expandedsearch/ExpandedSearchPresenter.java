package com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.LanguageDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.SkillDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.LanguageForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.SkillForExpandedSearch;

import java.util.List;

public class ExpandedSearchPresenter implements ExpandedSearchContract.Presenter {
    private ExpandedSearchContract.View view;
    private AppDatabase db;
    private LanguageDao languageDao;
    private SkillDao skillDao;

    ExpandedSearchPresenter(ExpandedSearchContract.View view) {
        this.view = view;
        initDatabase();
    }

    private void initDatabase() {
        db = InitDatabase.getInstance().getDatabese();
        skillDao = db.skillDao();
        languageDao = db.languageDao();
    }

    public void setDataFromAdapter(long id, long recruiterNotesId, String typeView) {
        view.sendDataToSecondaryActivity(id, recruiterNotesId, typeView);
    }

    @Override
    public List<SkillForExpandedSearch> getSkills() {
        return skillDao.getAllSkillForSearch();
    }

    @Override
    public List<LanguageForExpandedSearch> getLanguages() {
        return languageDao.getAllLanguageForSearch();
    }
}
