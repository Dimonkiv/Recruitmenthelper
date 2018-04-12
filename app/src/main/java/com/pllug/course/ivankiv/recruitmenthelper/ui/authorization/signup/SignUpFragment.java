package com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.AuthorizationActivity;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private View root;
    private Button btn_email, goToMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initView();
        initListener();

        return root;
    }

    //Initialization View
    private void initView() {
        btn_email = root.findViewById(R.id.sign_up_btn_email);
        goToMainActivity = root.findViewById(R.id.sign_up_btn_go_to_main_activity);
    }

    //Initialization button Listener
    private void initListener() {
        btn_email.setOnClickListener(this);
        goToMainActivity.setOnClickListener(this);
    }

    //Method which handles click on button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_btn_email:
                ((AuthorizationActivity) getActivity()).showSignUpByEmail();
                break;
            case R.id.sign_up_btn_go_to_main_activity:
                ((AuthorizationActivity) getActivity()).goToMainActivity();
        }
    }
}
