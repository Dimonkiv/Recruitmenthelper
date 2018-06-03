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

import java.util.ArrayList;
import java.util.List;

public class EditContactPresenter implements EditContactContract.Presenter {

    private EditContactContract.View view;
    private AppDatabase db;
    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;
    private LanguageDao languageDao;
    private SkillDao skillDao;
    private Contact contact;
    private RecruiterNotes recruiterNotes;
    private List<Language> languages;
    private List<Skill> skills;


    EditContactPresenter() {
        initDao();
        initModel();
    }

    //Initialization dao
    private void initDao() {
        db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
        recruiterNotesDao = db.recruiterNotesDao();
        skillDao = db.skillDao();
        languageDao = db.languageDao();
    }

    //Initialization model classes
    private void initModel() {
        contact = new Contact();
        recruiterNotes = new RecruiterNotes();
        languages = new ArrayList<>();
        skills = new ArrayList<>();
    }

    //Method which check entered data
    private void checkedEnteredData() {
        if (contact.getName() == null) {
            contact.setName("");
        }

        if (contact.getPhone() == null) {
            contact.setPhone("");
        }

        if (contact.getEmail() == null) {
            contact.setEmail("");
        }

        if (contact.getLinkedInLink() == null) {
            contact.setLinkedInLink("");
        }

        if (contact.getPhotoUri() == null) {
            contact.setPhotoUri("");
        }

        if (contact.getDateOfLatestContact() == null) {
            contact.setDateOfLatestContact("");
        }

        if (recruiterNotes.getTypeOfEmployment() == null) {
            recruiterNotes.setTypeOfEmployment("Студент");
        }

        if (recruiterNotes.getProfession() == null) {
            recruiterNotes.setProfession("");
        }

        if (recruiterNotes.getJobInterests() == null) {
            recruiterNotes.setJobInterests("");
        }

        if (recruiterNotes.getJobOrUniversity() == null) {
            recruiterNotes.setJobOrUniversity("");
        }

        if (recruiterNotes.getExperience() == null) {
            recruiterNotes.setExperience("");
        }

        if (recruiterNotes.getAdvantages() == null) {
            recruiterNotes.setAdvantages("");
        }

        if (recruiterNotes.getDisadvantages() == null) {
            recruiterNotes.setDisadvantages("");
        }

        if (recruiterNotes.getNotes() == null) {
            recruiterNotes.setNotes("");
        }

    }

    //Method which insert data into db
    private void insertCheckedDataIntoDB() {
        long recuiterId = recruiterNotesDao.insert(recruiterNotes);
        contact.setRecruiterNotesId(recuiterId);
        contactDao.insert(contact);
        insertLanguages(recuiterId);
        insertSkills(recuiterId);
    }

    //Method which insert skills
    private void insertLanguages(long recuiterId) {
        for (Language language : languages) {
            if(language.getLanguageLevel() == null) {
                language.setLanguageLevel("A1");
            }
            language.setRecruiterNotesId(recuiterId);
            languageDao.insert(language);
        }
    }

    //Method which insert skills
    private void insertSkills(long recuiterId) {
        for (Skill skill : skills) {
            skill.setRecruiterNotesId(recuiterId);
            skillDao.insert(skill);
        }
    }

    @Override
    public void setName(String name) {
        contact.setName(name);
    }

    @Override
    public void setPhone(String phone) {
        contact.setPhone(phone);
    }

    @Override
    public void setEmail(String email) {
        contact.setEmail(email);
    }

    @Override
    public void setLinkedInLink(String linkedInLink) {
        contact.setLinkedInLink(linkedInLink);
    }

    @Override
    public void setDateOfLatestConnect(String dateOfLatestConnect) {
        contact.setDateOfLatestContact(dateOfLatestConnect);
    }

    @Override
    public void setPhotoUri(String photoUri) {
        contact.setPhotoUri(photoUri);
    }

    @Override
    public void setTypeOfEmployment(String typeOfEmployment) {
        recruiterNotes.setTypeOfEmployment(typeOfEmployment);
    }

    @Override
    public void setProfession(String profession) {
        recruiterNotes.setProfession(profession);
    }

    @Override
    public void setExperience(String experience) {
        recruiterNotes.setExperience(experience);
    }

    @Override
    public void setJobOrUniversity(String jobOrUniversity) {
        recruiterNotes.setJobInterests(jobOrUniversity);
    }

    @Override
    public void setJobInterest(String jobInterest) {
        recruiterNotes.setJobInterests(jobInterest);
    }

    @Override
    public void setAdvantages(String advantages) {
        recruiterNotes.setAdvantages(advantages);
    }

    @Override
    public void setDisadvantages(String disadvantages) {
        recruiterNotes.setDisadvantages(disadvantages);
    }

    @Override
    public void setNotes(String notes) {
        recruiterNotes.setNotes(notes);
    }

    @Override
    public void setLanguages(List<Language> languages) {
        this.languages.clear();
        this.languages.addAll(languages);
    }

    @Override
    public void setSkills(List<Skill> skills) {
        this.skills.clear();
        this.skills.addAll(skills);
    }

    @Override
    public void setLanguage(Language language) {
        languages.add(language);
    }

    @Override
    public void setSkill(Skill skill) {
        skills.add(skill);
    }

    @Override
    public boolean insertIntoDb() {
        checkedEnteredData();
        insertCheckedDataIntoDB();
        return true;
    }




}
