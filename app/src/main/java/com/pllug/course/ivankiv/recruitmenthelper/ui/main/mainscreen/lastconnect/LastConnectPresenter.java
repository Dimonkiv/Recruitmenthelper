package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.AppDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;
import com.pllug.course.ivankiv.recruitmenthelper.data.db.dao.ContactDao;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;

import java.util.List;

public class LastConnectPresenter implements LastConnectContract.Presenter {
    private LastConnectContract.View view;
    private AppDatabase db;
    private ContactDao contactDao;

    public LastConnectPresenter(LastConnectContract.View view) {
        this.view = view;
        initDao();
    }

    private void initDao() {
        db = InitDatabase.getInstance().getDatabese();
        contactDao = db.contactDao();
    }

    public void setDataFromAdapter(long id, long recruiterNotesId, String typeView) {
        view.sendDataToSecondaryActivity(id, recruiterNotesId, typeView);
    }

    @Override
    public List<Contact> getContact() {
        return contactDao.getAllByDate();
    }
}
