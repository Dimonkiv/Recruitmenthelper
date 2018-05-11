package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.LanguageDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.RecruiterNotesDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.SkillDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;

import java.util.List;

public class DetailContactPresenter implements DetailContactContract.Presenter {
    private long id;
    private long recruiterNotesId;
    private DetailContactContract.View view;
    private AppDatabase db;
    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;
    private LanguageDao languageDao;
    private SkillDao skillDao;


    public DetailContactPresenter(DetailContactContract.View view, long id, long recruiterNotesId) {
        this.view = view;
        this.id = id;
        this.recruiterNotesId = recruiterNotesId;
        initDatabase();
        db = InitDatabase.getInstance().getDatabese();
    }

    private void initDatabase() {
        db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
        recruiterNotesDao = db.recruiterNotesDao();
        languageDao = db.languageDao();
        skillDao = db.skillDao();
    }


    @Override
    public Contact getContact() {
        return contactDao.getById(id);
    }

    @Override
    public RecruiterNotes getRecruiterNotes() {
        return recruiterNotesDao.getById(recruiterNotesId);
    }

    @Override
    public List<Language> getLanguages() {
        return languageDao.getAllByRecruiterNotesId(recruiterNotesId);
    }

    @Override
    public List<Skill> getSkills() {
        return skillDao.getAllByRecruiterNotesId(recruiterNotesId);
    }

    @Override
    public void updateSelected(boolean state, long id) {
        contactDao.updateSelected(state, id);
    }
}
