package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;


public class DetailContactFragment extends Fragment implements DetailContactContract.Fragment{
    private DetailContactPresenter presenter;
    private DetailContactView view;
    private View root;

    private static final int REQUEST_CODE_CALL_PHONE = 1;
    private static boolean CALL_PHONE_GRANTED = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_contact, container, false);

        initPresenter();
        initView();
        getDataFromSecondaryActivity();

        return root;
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new DetailContactPresenter(this);
    }

    private void initView() {
        view = new DetailContactView( presenter, getActivity(), root);
    }

    //Method, which get id, recruiterNotesId from SecondaryActivity
    private void getDataFromSecondaryActivity() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            presenter.setId(bundle.getLong("id", 0));
            presenter.setRecruiterNotesId(bundle.getLong("recruiterNotesId", 0));
            presenter.setFragmentName(bundle.getString("fragmentName"));
            presenter.loadData();
        }

    }

    //Method, which doing call
    private void callByPhone(String phone) {
        String toDial="tel:"+ phone;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(toDial)));
    }

    //Method for set permission CALL_PHONE
    @Override
    public void setPermission(String phone) {
        //get permission from manifest
        int hasReadContactPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

        //If device has API to 23, set permission
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            CALL_PHONE_GRANTED = true;
        } else {
            //Call Compat Activity for set permission
            requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL_PHONE);
        }

        //If permission allow
        if (CALL_PHONE_GRANTED) {
            callByPhone(phone);
        }
    }

    @Override
    public void showEditContactFragment(long id, long recruiterNotesId) {
        ((SecondaryActivity)getActivity()).showEditContactFragment(id, recruiterNotesId, "DetailContact");
    }

    @Override
    public void goToMainActivity(String fragmentName) {
        ((SecondaryActivity) getActivity()).goToMainActivity(fragmentName);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CALL_PHONE_GRANTED = true;
                }
        }

        if (CALL_PHONE_GRANTED) {
            callByPhone(presenter.getPhone());
        } else {
            view.showToast("Надайте доступ до контактів");
        }
    }

    @Override
    public void showSendEmailFragment(String email, long id, long recruiterNotesId, String fragmentName) {
        ((SecondaryActivity) getActivity()).showSendEmailFragment(email, id, recruiterNotesId, fragmentName);
    }
}
