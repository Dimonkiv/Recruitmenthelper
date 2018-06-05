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
    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;
    private LanguageDao languageDao;
    private SkillDao skillDao;
    private Contact contact;
    private RecruiterNotes recruiterNotes;
    private List<Language> languages, newLanguages;
    private List<Skill> skills, newSkills;
    private long id, recruiterNotesId;
    private String fragmentName;
    private EditContactContract.Fragment fragment;


    EditContactPresenter(EditContactContract.Fragment fragment) {
        this.fragment = fragment;
        initDao();
        initModel();
    }

    //Initialization dao
    private void initDao() {
        AppDatabase db = InitDatabase.getInstance().getDatabese();
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
        newLanguages = new ArrayList<>();
        newSkills = new ArrayList<>();
    }


    /*---------------------------------Load data----------------------------------------------------------------*/
    //Method, which load contact
    private void loadContact() {
        contact = contactDao.getById(id);
    }

    //Method, which load recruiterNotes
    private void loadRecruiterNotes() {
        recruiterNotes = recruiterNotesDao.getById(recruiterNotesId);
    }

    //Method, which load languages
    private void loadLanguages() {
        languages = languageDao.getAllByRecruiterNotesId(recruiterNotesId);
    }

    //Method, which load skills
    private void loadSkills() {
        skills = skillDao.getAllByRecruiterNotesId(recruiterNotesId);
    }


    /*-------------------------------------Insert into db----------------------------------------------------------*/
    //Method, which insert data into db
    private void insertIntoDb() {
        checkedEnteredData();
        insertCheckedDataIntoDB();
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
        long recruiterNotesId = recruiterNotesDao.insert(recruiterNotes);
        contact.setRecruiterNotesId(recruiterNotesId);
        contactDao.insert(contact);
        insertLanguages(recruiterNotesId);
        insertSkills(recruiterNotesId);
    }

    //Method which insert skills
    private void insertLanguages(long recruiterId) {
        for (Language language : newLanguages) {
            if (language.getLanguageLevel() == null) {
                language.setLanguageLevel("A1");
            }
            language.setRecruiterNotesId(recruiterId);
            languageDao.insert(language);
        }
    }

    //Method which insert skills
    private void insertSkills(long recruiterId) {
        for (Skill skill : newSkills) {
            skill.setRecruiterNotesId(recruiterId);
            skillDao.insert(skill);
        }
    }


    /*-------------------------------------Update data----------------------------------------------------------------*/
    //Method, which update data
    private void updateDataInDB() {
        updateContact();
        updateRecruiterNotes();
        updateLanguages();
        updateSkills();
    }

    //Method which update Contact
    private void updateContact() {
        if (contact != null) {
            contactDao.update(contact);
        }
    }

    //Method which update RecruiterNotes
    private void updateRecruiterNotes() {
        if (recruiterNotes != null) {
            recruiterNotesDao.update(recruiterNotes);
        }
    }

    //Method which update Languages
    private void updateLanguages() {
        int i = 0;
        for (Language language : newLanguages) {
            language.setRecruiterNotesId(recruiterNotesId);

            if (i < languages.size()) {
                languageDao.update(language);
            } else {
                languageDao.insert(language);
            }

            i++;
        }

        if (newLanguages.size() < languages.size()) {
            removeRemovedLanguagesFromDB();
        }
    }

    //method which delete languages which was deleted in adapter during updating date
    private void removeRemovedLanguagesFromDB() {
        int i = 0;

        for (Language language : languages) {
            if (i < newLanguages.size()) {
                if (!language.equals(newLanguages.get(i))) {
                    languageDao.delete(language);
                    i--;
                }

                i++;
            } else {
                languageDao.delete(language);
            }
        }
    }

    //Method which update Skills
    private void updateSkills() {
        int i = 0;

        for (Skill skill : newSkills) {
            skill.setRecruiterNotesId(recruiterNotesId);

            if (i < skills.size()) {
                skillDao.update(skill);
            } else {
                skillDao.insert(skill);
            }

            i++;
        }

        if (newSkills.size() < skills.size()) {
            removeRemovedSkillsFromDB();
        }
    }

    //method which delete languages which was deleted in adapter during updating date
    private void removeRemovedSkillsFromDB() {
        int i = 0;

        for (Skill skill : skills) {
            if (i < newSkills.size()) {
                if (!skill.equals(newSkills.get(i))) {
                    skillDao.delete(skill);
                    i--;
                }
                i++;
            } else {
                skillDao.delete(skill);
            }
        }
    }


    /*-----------------------------------------------Show data in View----------------------------------------------*/
    //Method which show data in view
    private void showData() {
        showContact();
        showRecruiterNotes();
        view.showLanguages(languages);
        view.showSkills(skills);
    }

    //Method which show recruiter notes data in view
    private void showRecruiterNotes() {
        boolean isWorker = false;

        if (recruiterNotes != null) {
            if (recruiterNotes.getTypeOfEmployment() != null) {
                if (recruiterNotes.getTypeOfEmployment().equals("Працює")) {
                    view.setWorkRadioButton();
                    view.showDeveloperFields();
                    isWorker = true;
                } else {
                    view.setStudentRadioButton();
                    view.hideDeveloperFields();
                }
            }

            if (isWorker) {
                if (recruiterNotes.getProfession() != null) {
                    view.showProfession(recruiterNotes.getProfession());
                }

                if (recruiterNotes.getExperience() != null) {
                    switch (recruiterNotes.getExperience()) {
                        case "trainee":
                            view.setTraineeRadioButton();
                            break;
                        case "junior":
                            view.setJuniorRadioButton();
                            break;
                        case "middle":
                            view.setMiddleRadioButton();
                            break;
                        case "senior":
                            view.setSeniorRadioButton();
                            break;
                        case "techLead":
                            view.setTechLeadRadioButton();
                            break;
                    }
                }
            }

            if (recruiterNotes.getJobOrUniversity() != null) {
                view.showJobOrUniversity(recruiterNotes.getJobOrUniversity());
            }

            if (recruiterNotes.getJobInterests() != null) {
                view.setJobInterestSpinnerItem(getJobInterestItemId());
            }

            if (recruiterNotes.getAdvantages() != null) {
                view.showAdvantages(recruiterNotes.getAdvantages());
            }

            if (recruiterNotes.getDisadvantages() != null) {
                view.showDisadvantages(recruiterNotes.getDisadvantages());
            }

            if (recruiterNotes.getNotes() != null) {
                view.showNotes(recruiterNotes.getNotes());
            }
        }
    }

    //Method which return jobInterest spinner item id
    private int getJobInterestItemId() {
        int jobInterestId = 0;

        switch (recruiterNotes.getJobInterests()) {
            case "Не працює":
                jobInterestId = 0;
                break;
            case "Працює":
                jobInterestId = 1;
                break;
            case "Шукає роботу":
                jobInterestId = 2;
                break;
            case "Зацікавлений в нових можливостях":
                jobInterestId = 3;
                break;

        }

        return jobInterestId;
    }

    @Override
    public void showContact() {
        if (contact != null) {
            if (contact.getName() != null) {
                view.showName(contact.getName());
            }

            if (contact.getPhone() != null) {
                view.showPhone(contact.getPhone());
            }

            if (contact.getEmail() != null) {
                view.showEmail(contact.getEmail());
            }

            if (contact.getLinkedInLink() != null) {
                view.showLinkedInLink(contact.getLinkedInLink());
            }

            if (contact.getDateOfLatestContact() != null) {
                view.showDateOfLatestContact(contact.getDateOfLatestContact());
            }

            if (contact.getPhotoUri() != null) {
                view.showPhoto(contact.getPhotoUri());
            }
        }
    }

    /*---------------------------------------------Set data-----------------------------------------------------*/
    @Override
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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
    public void setRecruiterNotesId(long recruiterNotesId) {
        this.recruiterNotesId = recruiterNotesId;
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
        recruiterNotes.setJobOrUniversity(jobOrUniversity);
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
        this.newLanguages.clear();
        this.newLanguages.addAll(languages);
    }

    @Override
    public void setSkills(List<Skill> skills) {
        this.newSkills.clear();
        this.newSkills.addAll(skills);
    }

    @Override
    public void setLanguage(Language language) {
        newLanguages.add(language);
    }

    @Override
    public void setSkill(Skill skill) {
        newSkills.add(skill);
    }


    /*------------------------------------------------Get data-----------------------------------*/
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    @Override
    public long getRecruiterNotesId() {
        return recruiterNotesId;
    }

    @Override
    public void onClickSentButton() {
        if (fragmentName.equals("PhoneContactFragment")) {
            insertIntoDb();
            view.showToast("Дані записані!");
            fragment.setDataToMainActivity("ContactList");
        } else if (fragmentName.equals("DetailContact")) {
            updateDataInDB();
            view.showToast("Дані оновлено!");
            fragment.showDetailContactFragment(id, recruiterNotesId, fragmentName);
        } else {
            updateDataInDB();
            view.showToast("Дані оновлено!");
            fragment.setDataToMainActivity("ContactList");
        }

    }

    @Override
    public void onBackButtonClick() {
        if (fragmentName.equals("PhoneContactFragment") || fragmentName.equals("ContactListEditBtn")) {
            fragment.setDataToMainActivity(fragmentName);
        } else {
            fragment.showDetailContactFragment(id, recruiterNotesId, fragmentName);
        }
    }

    @Override
    public void setView(EditContactContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        loadContact();
        loadRecruiterNotes();
        loadLanguages();
        loadSkills();
        showData();
    }


}
