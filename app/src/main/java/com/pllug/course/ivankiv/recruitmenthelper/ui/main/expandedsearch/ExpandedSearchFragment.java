package com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.MainActivity;


public class ExpandedSearchFragment extends Fragment  implements ExpandedSearchContract.Fragment{
    private View root;
    private ExpandedSearchPresenter presenter;
    private ExpandedSearchView view;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_expanded_search, container, false);

        initPresenter();
        initView();
        initView();


        return root;
    }


    //Initialization presenter
    private void initPresenter() {
        presenter = new ExpandedSearchPresenter(this);
    }

    private void initView() {
        view = new ExpandedSearchView(this, presenter, getActivity(), root);
        presenter.loadData();
    }


    @Override
    public void openDetailFragment(long id, long recruiterNotesId, String fragmentName) {
        ((MainActivity)getActivity()).goToSecondaryActivity(recruiterNotesId, id, fragmentName);
    }
}
