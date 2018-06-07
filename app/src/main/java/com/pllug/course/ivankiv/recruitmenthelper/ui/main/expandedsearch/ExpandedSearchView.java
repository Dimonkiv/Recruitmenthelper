package com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.LanguageForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.SkillForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch.adapter.LanguageForSearchAdapter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch.adapter.SkillForSearchAdapter;

import java.util.List;

public class ExpandedSearchView implements ExpandedSearchContract.View, View.OnClickListener {
    private ExpandedSearchContract.Fragment fragment;
    private ExpandedSearchPresenter presenter;
    private Context mContext;
    private View root;
    private LanguageForSearchAdapter languageAdapter;
    private SkillForSearchAdapter skillAdapter;

    //View
    private AutoCompleteTextView expandedSearchEdit;
    private RadioButton skillRBtn, languageRBtn;
    private RelativeLayout infoContainer;
    private RecyclerView recyclerView;

    public ExpandedSearchView(ExpandedSearchContract.Fragment fragment, ExpandedSearchPresenter presenter, Context mContext, View root) {
        this.fragment = fragment;
        this.presenter = presenter;
        this.mContext = mContext;
        this.root = root;
        presenter.setView(this);

        initView();
        initListener();
        initAdapters();
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

    //Initialization adapters
    private void initAdapters() {
        languageAdapter = new LanguageForSearchAdapter(mContext, presenter);
        skillAdapter = new SkillForSearchAdapter(mContext, presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
                    presenter.filterLanguages(editable.toString());
                } else {
                   presenter.filterSkills(editable.toString());
                }
            }
        });
    }

    private void initLanguageAutoComplete() {
        String typicalLanguages[] = new String[]{"Українська", "Російська", "Польська", "Німецька", "Англійська", "Французька"};
        expandedSearchEdit.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, typicalLanguages));
    }

    private void initSkillAutoComplete() {
        String typicalSkills[] = new String[] {"Java", "Android", "Git", "Sql", "C", "C++", "Python", "Django", "Html", "Css"
                , "JavaScript", "Jquery", "React", "AngularJs", "Angular 2", "Vue.js", "Backbone.js", "Ember.js", "Knockout.js", "Node.js"
                , "Html5", "Css3", "C#", "Kotlin", "Room", "SQLite", "Retrofit", "Rest.API", ".NET", "objective c" , "swift"
                , "Php", "Assembler", "Менеджмент", "Підбір персоналу", "Набір IT-персоналу", "Управління персоналом", "Android SDK"
                , "Android NDK"};

        expandedSearchEdit.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, typicalSkills));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.expanded_search_language_r_btn:
                presenter.onLanguageRadioButtonClick();
                break;
            case R.id.expanded_search_skill_r_btn:
                presenter.onSkillRadioButtonClick();
                break;

        }
    }

    @Override
    public void setLanguageAdapter() {
        recyclerView.setAdapter(languageAdapter);
        initEditSearchListener(true);
        initLanguageAutoComplete();
    }

    @Override
    public void addAllLanguages(List<LanguageForExpandedSearch> languages) {
        languageAdapter.addAllLanguages(languages);
        languageAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFilteredLanguages(List<LanguageForExpandedSearch> filteredLanguages) {
        languageAdapter.filterList(filteredLanguages);
    }

    @Override
    public void setSkillAdapter() {
        recyclerView.setAdapter(skillAdapter);
        initEditSearchListener(false);
        initSkillAutoComplete();
    }

    @Override
    public void addAllSkills(List<SkillForExpandedSearch> skills) {
        skillAdapter.addAllSkills(skills);
        skillAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFilteredSkills(List<SkillForExpandedSearch> filteredSkills) {
        skillAdapter.filterList(filteredSkills);
    }

}
