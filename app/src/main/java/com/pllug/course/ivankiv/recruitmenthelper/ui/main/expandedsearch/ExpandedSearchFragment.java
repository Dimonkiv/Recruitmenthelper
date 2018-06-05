package com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.LanguageForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.SkillForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch.adapter.LanguageForSearchAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch.adapter.SkillForSearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpandedSearchFragment extends Fragment  implements ExpandedSearchContract.View, View.OnClickListener{
    private View root;
    private ExpandedSearchPresenter presenter;
    private List<LanguageForExpandedSearch> languages;
    private List<SkillForExpandedSearch> skills;
    private SkillForSearchAdapter skillAdapter;
    private LanguageForSearchAdapter languageAdapter;

    //View
    private AutoCompleteTextView expandedSearchEdit;
    private RadioButton skillRBtn, languageRBtn;
    private RelativeLayout infoContainer;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_expanded_search, container, false);

        initView();
        initListener();
        initPresenter();
        initAdapters();
        getDataFromPresenter();

        return root;
    }

    //Initialization View
    private void initView() {
        expandedSearchEdit = root.findViewById(R.id.expanded_search_edit);
        skillRBtn = root.findViewById(R.id.expanded_search_skill_r_btn);
        languageRBtn = root.findViewById(R.id.expanded_search_language_r_btn);
        infoContainer = root.findViewById(R.id.expanded_search_info_container);
        recyclerView = root.findViewById(R.id.expanded_search_recycler);
    }

    //Initialization listener
    private void initListener() {
        languageRBtn.setOnClickListener(this);
        skillRBtn.setOnClickListener(this);
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new ExpandedSearchPresenter(this);
    }

    //Initialization adapters
    private void initAdapters() {
        languages = new ArrayList<>();
        languageAdapter = new LanguageForSearchAdapter(getActivity(), presenter);
        skills = new ArrayList<>();
        skillAdapter = new SkillForSearchAdapter(getActivity(), skills, presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //Method, which set skillAdapter to RecyclerView
    private void setSkillAdapter() {
        recyclerView.setAdapter(skillAdapter);
        initEditSearchListener(false);
    }

    //Method, which set languageAdapter to RecyclerView
    private void setLanguageAdapter() {
        recyclerView.setAdapter(languageAdapter);
        initEditSearchListener(true);
    }

    //Method, which get data from presenter
    private void getDataFromPresenter() {
        languages.addAll(presenter.getLanguages());
        skills.addAll(presenter.getSkills());
    }

    //Initialization TextChangeListener for search edit text
    private void initEditSearchListener(final boolean x) {
        expandedSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                infoContainer.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (x) {
                    filterLanguages(editable.toString());
                } else {
                    filterSkill(editable.toString());
                }
            }
        });
    }

    //Method, which checking coincidence between entered text and language in languages
    private void filterLanguages(String text) {
        List<LanguageForExpandedSearch> filteredLanguage = new ArrayList<>();

        for (LanguageForExpandedSearch languageItem : languages) {
            if (languageItem.getLanguage() != null) {
                if (languageItem.getLanguage().toLowerCase().contains(text.toLowerCase())) {
                    filteredLanguage.add(languageItem);
                }
            }
        }

        languageAdapter.filterList(filteredLanguage);
    }

    //Method, which checking coincidence between entered text and skill in skill list
    private void filterSkill(String text) {
        List<SkillForExpandedSearch> filteredSkill = new ArrayList<>();

        for (SkillForExpandedSearch skillItem : skills) {
            if (skillItem.getSkill() != null) {
                if (skillItem.getSkill().toLowerCase().contains(text.toLowerCase())) {
                    filteredSkill.add(skillItem);
                }
            }
        }

        skillAdapter.filterList(filteredSkill);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.expanded_search_language_r_btn:
                setLanguageAdapter();
                break;
            case R.id.expanded_search_skill_r_btn:
                setSkillAdapter();
                break;

        }
    }

    @Override
    public void sendDataToSecondaryActivity(long id, long recruiterNotesId, String typeView) {
        ((MainActivity)getActivity()).goToSecondaryActivity(recruiterNotesId, id, typeView);
    }
}
