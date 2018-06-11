package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;


public class EditContactFragment extends Fragment implements EditContactContract.Fragment{
    private View root;
    private EditContactPresenter presenter;
    private EditContactView view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_edit_contact, container, false);

        initPresenter();
        initView();
        getDataFromActivity();

        return root;
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new EditContactPresenter(this);
    }

    //Initialization View
    private void initView() {
        view = new EditContactView(this, presenter, getActivity(), root);
    }

    //Method which get data from activity
    private void getDataFromActivity() {
        Bundle bundle = getArguments();

        if (bundle != null && bundle.getString("fragmentName") != null) {
            String fragmentName = bundle.getString("fragmentName");
            if (fragmentName.equals("PhoneContactFragment")) {
                presenter.setContact((Contact)bundle.getSerializable("contact"));
                presenter.setFragmentName(fragmentName);
                presenter.showContact();
            } else if(fragmentName.equals("DetailContact") || fragmentName.equals("ContactListEditBtn")){
                presenter.setId(bundle.getLong("id"));
                presenter.setRecruiterNotesId(bundle.getLong("recruiterNotesId"));
                presenter.setFragmentName(fragmentName);
                presenter.loadData();
            }
        }
    }

    @Override
    public void showDetailContactFragment(long id, long recruiterNotesId, String fragmentName) {
        ((SecondaryActivity)getActivity()).showDetailContactFragment(id, recruiterNotesId, fragmentName);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        view.onCreateOptionMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        view.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void openGallery() {
        //Open system gallery
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //Type of received object - image
        photoPickerIntent.setType("image/*");
        //Start activity for result

        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    public void setDataToMainActivity(String fragmentName) {
        ((SecondaryActivity)getActivity()).goToMainActivity(fragmentName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If received cod is equal to PICK_IMAGE code
        if (requestCode == 1) {
            presenter.setPhotoUri(data.getData().toString());
            view.showPhoto(data.getData().toString());
        }
    }
}
