package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.RecruiterNotes;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter.LanguageEditTextAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter.SkillAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.datepicker.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        presenter = new EditContactPresenter();
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
                view.setContact((Contact)bundle.getSerializable("contact"));
                view.setFragmentName(fragmentName);
            } else if(fragmentName.equals("DetailContact") || fragmentName.equals("ContactListEditBtn")){
                view.setId(bundle.getLong("id"));
                view.setRecruiterNotesId(bundle.getLong("recruiterNotesId"));
                view.setFragmentName(fragmentName);
                view.setDataFromDBIntoFields();
            }
        }
    }

    //Method which show DetailContactFragment
    public void showDetailContactFragment(long id, long recruiterNotesId, String fragmentName) {
        ((SecondaryActivity)getActivity()).showDetailContactFragment(id, recruiterNotesId, fragmentName);
    }

    //Set icon-button allow
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        view.onCreateOptionMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Listener for allow button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        view.onOptionsItemSelected(item);
        return true;
    }


    @Override
    public void startActivityForResult(Intent intent) {
        startActivityForResult(intent, 1);
    }

    @Override
    public void setDataToMainActivity(String fragmentName) {
        ((SecondaryActivity)getActivity()).goToMainActivity(fragmentName);
    }

    //Method, which get photoUri from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If received cod is equal to PICK_IMAGE code
        if (requestCode == 1) {
            view.setPhoto(data.getData().toString());
        }
    }
}
