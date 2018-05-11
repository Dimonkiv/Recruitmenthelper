package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

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

public class EditContactPresenter implements EditContactContract.Presenter {

    private EditContactContract.View view;
    private AppDatabase db;
    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;
    private LanguageDao languageDao;
    private SkillDao skillDao;


    EditContactPresenter(EditContactContract.View view) {
        this.view = view;
        initDao();
    }

    //Initialization dao
    private void initDao() {
        db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
        recruiterNotesDao = db.recruiterNotesDao();
        skillDao = db.skillDao();
        languageDao = db.languageDao();
    }

    @Override
    public Contact getContact(long id) {
        return contactDao.getById(id);
    }

    @Override
    public RecruiterNotes getRecruiterNote(long recruiterNotesId) {
        return recruiterNotesDao.getById(recruiterNotesId);
    }

    @Override
    public List<Language> getLanguages(long recruiterNotesId) {
        return languageDao.getAllByRecruiterNotesId(recruiterNotesId);
    }

    @Override
    public List<Skill> getSkills(long recruiterNotesId) {
        return skillDao.getAllByRecruiterNotesId(recruiterNotesId);
    }

    @Override
    public boolean checkedEnteredData(String fragmentName) {

        Contact contact = view.getContact();
        RecruiterNotes recruiterNotes = view.getRecruiterNotes();

        if (fragmentName.equals("PhoneContactFragment")) {

            long id = insertRecruiterNotes(recruiterNotes);
            insertContact(contact, id);
            insertLanguages(id);
            insertSkills(id);

        } else {
            int i = contactDao.update(contact);
            recruiterNotesDao.update(recruiterNotes);
            updateLanguages();
            updateSkills();
        }

        return true;
    }

    //Method, which insert recruiter notes into db
    private long insertRecruiterNotes(RecruiterNotes recruiterNotes) {
        return recruiterNotesDao.insert(recruiterNotes);
    }

    //Method, which insert recruiter notes into db
    private void insertContact(Contact contact, long id) {
        contact.setRecruiterNotesId(id);
        contactDao.insert(contact);
    }

    //Method, which insert languages into db
    private void insertLanguages(long id) {
        List<Language> languages = view.getLanguages();

        for (Language language: languages) {
            language.setRecruiterNotesId(id);
            languageDao.insert(language);
        }
    }

    //Method, which update languages
    private void updateLanguages() {
        int i = 0;
        List<Language> languages = view.getLanguages();
        long id = languages.get(0).getRecruiterNotesId();

        for (Language language : languages) {

            if (i < languages.size()-1) {
                languageDao.update(language);
                i++;
            } else {
                language.setRecruiterNotesId(id);
                languageDao.insert(language);
                i++;
            }

        }
    }

    //Method, which insert skills into db
    private void insertSkills(long id) {
        List<Skill> skills = view.getSkills();

        for (Skill skill: skills) {
            skill.setRecruiterNotesId(id);
            skillDao.insert(skill);
        }
    }

    private void updateSkills() {
        int i = 0;
        List<Skill> skills = view.getSkills();
        long id = skills.get(0).getRecruiterNotesId();

        for (Skill skill : skills) {
            if (i < skills.size() - 1) {
                skillDao.update(skill);
                i++;
            } else {
                skill.setRecruiterNotesId(id);
                skillDao.insert(skill);
                i++;
            }
        }
    }
}
