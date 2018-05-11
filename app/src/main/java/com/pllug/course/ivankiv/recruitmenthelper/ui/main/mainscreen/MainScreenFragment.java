package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist.ContactListFragment;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect.LastConnectFragment;

import java.util.HashMap;
import java.util.List;

public class MainScreenFragment extends Fragment {

    private FragmentTabHost tabHost;
    private String fragmentName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        getDataFromMainActivity();
        initTabHost();
        changeThemeTabHost();

        return tabHost;
    }

    //Method, which get fragmentName from MainActivity
    private void getDataFromMainActivity() {
        Bundle bundle = getArguments();

        if (bundle != null && !bundle.getString("fragmentName").isEmpty()) {
            fragmentName = bundle.getString("fragmentName");
        }
    }

    //Initialization TabHost
    private void initTabHost() {
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_main_screen);

        tabHost.addTab(tabHost.newTabSpec("Contact list").setIndicator("Список контактів"),
                ContactListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Last contact").setIndicator("Останні контакти"),
                LastConnectFragment.class, null);

        if (fragmentName.equals("LastConnect")) {
            tabHost.setCurrentTabByTag("Last contact");
        }

    }

    //Changing the tabs background color and text color on the tabs
    private void changeThemeTabHost() {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.colorPrimary);
            TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }
    }
}
