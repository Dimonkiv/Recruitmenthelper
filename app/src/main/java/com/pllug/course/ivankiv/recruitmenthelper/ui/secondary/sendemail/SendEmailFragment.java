package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.sendemail;

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
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.AuthorizationActivity;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;

public class SendEmailFragment extends Fragment {
    private View root;
    private String fragmentName;
    private long id, recruiterNotesId;

    //View
    private Toolbar toolbar;
    private EditText email, textSubject, textMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_send_email, container, false);

        initView();
        initToolbar();
        getEmailFromDetailContactFragment();

        return root;
    }

    //Initialization view
    private void initView() {
        toolbar = root.findViewById(R.id.send_email_toolbar);
        email = root.findViewById(R.id.send_email_email);
        textSubject = root.findViewById(R.id.send_email_subject);
        textMessage = root.findViewById(R.id.send_email_text_message);
    }

    //Method, which get email by bundle from detailContactFragment
    private void getEmailFromDetailContactFragment() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            email.setText(bundle.getString("email"));
            fragmentName = bundle.getString("fragmentName");
            id = bundle.getLong("id", 0);
            recruiterNotesId = bundle.getLong("recruiterNotesId", 0);

        }
    }

    //Initialization toolbar
    private void initToolbar() {
        //Said that toolbar will be use such as ActionBar
        ((SecondaryActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Написати email");
        setHasOptionsMenu(true);

        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SecondaryActivity) getActivity()).showDetailContactFragment(id, recruiterNotesId, fragmentName);
            }
        });
    }

    //Method, which check entered data
    private void checkingData() {
        //if text message isn’t empty
        //then send email
        if (!textMessage.getText().toString().isEmpty()) {
            sendEmail();
        } else {
            Toast.makeText(getActivity(), "Введіть повідомлення!", Toast.LENGTH_SHORT).show();
        }
    }

    //Method, which send email
    private void sendEmail() {
        String to = email.getText().toString();
        String subject = textSubject.getText().toString();
        String message = textMessage.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Виберіть email клієнт:"));
    }

    //Set icon-button allow
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.send_email_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Listener for send button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.send_email_toolbar_send_btn) {
            checkingData();
        }

        return true;
    }

}
