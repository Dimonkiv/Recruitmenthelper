package com.pllug.course.ivankiv.recruitmenthelper.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.pllug.course.ivankiv.recruitmenthelper.R;

public class EditContact extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        initView();
        initToolbar();
    }

    private void initView() {
        toolbar = findViewById(R.id.edit_contact_toolbar);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Редагування контакту");

        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Обробник кліку кнопки назад
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
