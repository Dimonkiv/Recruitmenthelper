package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailconatct;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;

public class DetailContactFragment extends Fragment {
    private View root;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_contact, container, false);

        initView();
        initToolbar();
        return root;
    }

    private void initView() {
        toolbar = root.findViewById(R.id.detail_contact_toolbar);
    }

    private void initToolbar() {
        ((SecondaryActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Dima Ivankiv");
        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Back!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
