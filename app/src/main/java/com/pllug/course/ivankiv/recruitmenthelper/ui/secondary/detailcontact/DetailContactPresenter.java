package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact;

import com.pllug.course.ivankiv.recruitmenthelper.R;
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

class DetailContactPresenter implements DetailContactContract.Presenter {
    private DetailContactContract.View view;
    private DetailContactContract.Fragment fragment;

    private ContactDao contactDao;
    private RecruiterNotesDao recruiterNotesDao;
    private LanguageDao languageDao;
    private SkillDao skillDao;

    private Contact contact;
    private RecruiterNotes recruiterNotes;
    private List<Language> languages;
    private List<Skill> skills;

    private long id;
    private long recruiterNotesId;
    private String fragmentName;


    DetailContactPresenter(DetailContactContract.Fragment fragment) {
        this.fragment = fragment;
        initDatabase();

    }

    private void initDatabase() {
        AppDatabase db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
        recruiterNotesDao = db.recruiterNotesDao();
        languageDao = db.languageDao();
        skillDao = db.skillDao();
    }


    /*---------------------------------Show data in View------------------------------------------*/
    private void showData() {
        showDataInFirstContainer();
        showSkillsData();
        showLanguagesData();
        showDataInRecruiterNotesContainer();
    }

    //Method, which set data into firsContainer
    private void showDataInFirstContainer() {

        if (contact != null && contact.getPhotoUri() != null && !contact.getPhotoUri().isEmpty()) {
            view.showPhoto(contact.getPhotoUri());
        }

        if (contact != null && contact.getName() != null && !contact.getName().isEmpty()) {
            view.showName(contact.getName());
            view.showToolbarTitle(contact.getName());
        }

        if (recruiterNotes.getTypeOfEmployment() != null && !recruiterNotes.getTypeOfEmployment().isEmpty()) {
            if (recruiterNotes.getTypeOfEmployment().equals("Студент")) {
                view.showTypeOfEmployment(recruiterNotes.getTypeOfEmployment());
                view.showTypeOfEmploymentContainer();
                view.hideProfessionContainer();
            } else if (recruiterNotes.getTypeOfEmployment().equals("Працює")) {
                if (recruiterNotes.getProfession() != null && !recruiterNotes.getProfession().isEmpty()) {
                    view.hideTypeOfEmploymentContainer();
                    view.showProfession(recruiterNotes.getProfession());
                    if (recruiterNotes.getExperience() != null && !recruiterNotes.getExperience().isEmpty()) {
                        view.showExperience(recruiterNotes.getExperience());
                    }
                }
            }
        }


        if (recruiterNotes.getJobOrUniversity() != null && !recruiterNotes.getJobOrUniversity().isEmpty()) {
            view.showJobOrUniversity(recruiterNotes.getJobOrUniversity());
        } else {
            view.hideJobOrUniversityContainer();
        }

        if (recruiterNotes.getJobInterests() != null && !recruiterNotes.getJobInterests().isEmpty()) {
            view.showJobInterest(recruiterNotes.getJobInterests());
        }

        if (contact.isSelected()) {
            view.setSelectedButton(R.drawable.icon_star_actived_green);
        } else {
            view.setSelectedButton(R.drawable.icon_star_green);
        }

    }

    //Method, which set skillList into skillContainer
    private void showSkillsData() {
        if (skills != null) {
            if (skills.get(0) != null && skills.get(0).getSkill() != null) {
                view.showSkills(getSkillString());
            } else {
                view.hideSkillsContainer();
            }
        } else {
            view.hideSkillsContainer();
        }
    }

    //Method, which create string from skillList
    private String getSkillString() {
        StringBuilder builder = new StringBuilder();
        String skillStr = "";

        if (skills.size() <= 1) {
            skillStr = skills.get(0).getSkill();
        } else {

            for (Skill skill : skills) {
                if (skill.equals(skills.get(skills.size() - 1))) {
                    builder.append(skill.getSkill());
                } else {
                    builder.append(skill.getSkill()).append(", ");
                }
            }

            skillStr = builder.toString();
        }

        return skillStr;
    }

    //Method, which set languages into languages container(RecyclerView)
    private void showLanguagesData() {

        if (languages != null && languages.size() > 0) {

            if (languages.size() == 1 || languages.get(0) == null && languages.get(0).getLanguage() == null && languages.get(0).getLanguage().isEmpty()) {
                view.hideLanguagesContainer();
            } else {
                view.showLanguages(languages);
            }

        } else {
            view.hideLanguagesContainer();
        }
    }

    //Method, which set data into recruiterNotes container
    private void showDataInRecruiterNotesContainer() {

        if (contact.getDateOfLatestContact() != null && recruiterNotes.getAdvantages() != null && recruiterNotes.getDisadvantages() != null && recruiterNotes.getNotes() != null) {

            if (contact.getDateOfLatestContact().isEmpty() && recruiterNotes.getAdvantages().isEmpty() && recruiterNotes.getDisadvantages().isEmpty() && recruiterNotes.getNotes().isEmpty()) {
                view.hideRecruiterNotesContainer();
            } else {

                if (contact.getDateOfLatestContact() != null && !contact.getDateOfLatestContact().isEmpty()) {
                    view.showDateOfLastConnect(contact.getDateOfLatestContact());
                } else {
                    view.hideDateOfLastConnectContainer();
                }

                if (recruiterNotes.getAdvantages() != null && !recruiterNotes.getAdvantages().isEmpty()) {
                    view.showAdvantages(recruiterNotes.getAdvantages());
                } else {
                    view.hideAdvantagesContainer();
                }

                if (recruiterNotes.getDisadvantages() != null && !recruiterNotes.getDisadvantages().isEmpty()) {
                    view.showDisadvantages(recruiterNotes.getDisadvantages());
                } else {
                    view.hideDisadvantagesContainer();
                }

                if (recruiterNotes.getNotes() != null && !recruiterNotes.getNotes().isEmpty()) {
                    view.showNotes(recruiterNotes.getNotes());
                } else {
                    view.hideNotesContainer();
                }

            }
        } else {
            view.hideRecruiterNotesContainer();
        }
    }

    //Method, which update selected
    private void updateSelected(boolean state) {
        contactDao.updateSelected(state, id);
    }


    /*---------------------------------Set data---------------------------------------------------*/
    @Override
    public void setView(DetailContactContract.View view) {
        this.view = view;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setRecruiterNotesId(long recruiterNotesId) {
        this.recruiterNotesId = recruiterNotesId;
    }

    @Override
    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    @Override
    public String getPhone() {
        if (contact.getPhone() != null && !contact.getPhone().isEmpty()) {
            return contact.getPhone();
        }

        return "";
    }

    @Override
    public void loadData() {
        contact = contactDao.getById(id);
        recruiterNotes = recruiterNotesDao.getById(recruiterNotesId);
        languages = languageDao.getAllByRecruiterNotesId(recruiterNotesId);
        skills = skillDao.getAllByRecruiterNotesId(recruiterNotesId);
        showData();
    }


    /*---------------------------------Click handlers---------------------------------------------*/
    @Override
    public void onEmailButtonClick() {
        if (contact.getEmail() != null && !contact.getEmail().isEmpty()) {
            fragment.showSendEmailFragment(contact.getEmail(), id, recruiterNotesId, fragmentName);
        } else {
            view.showToast("Не має електронної пошти!");
        }
    }

    @Override
    public void onPhoneButtonClick() {
        if (contact.getPhone() != null && !contact.getPhone().isEmpty()) {
            fragment.setPermission(contact.getPhone());
        } else {
            view.showToast("Номер телефону відсутній!");
        }
    }

    @Override
    public void onSelectedButtonClick() {
        if (contact.isSelected()) {
            view.setSelectedButton(R.drawable.icon_star_green);
            updateSelected(false);
            view.showToast("Видалено з вподобаних!");
        } else {
            view.setSelectedButton(R.drawable.icon_star_actived_green);
            updateSelected(true);
            view.showToast("Додано до ввподобаних!");
        }
    }

    @Override
    public void onEditButtonClick() {
        fragment.showEditContactFragment(id, recruiterNotesId);
    }

    @Override
    public void onBackButtonClick() {
        if (fragmentName.equals("DetailContact")) {
            fragment.goToMainActivity("ContactList");
        } else {
            fragment.goToMainActivity(fragmentName);
        }
    }
}
