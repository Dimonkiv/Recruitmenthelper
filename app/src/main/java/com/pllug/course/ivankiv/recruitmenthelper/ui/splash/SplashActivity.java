package com.pllug.course.ivankiv.recruitmenthelper.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.ui.authorization.AuthorizationActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initAnimation();
        goToNextActivity();
    }

    //Method for initialization View
    private void initView() {
        logo = findViewById(R.id.splash_logo);
        text = findViewById(R.id.splash_text);
    }

    //Method for initialization and start animation
    private void initAnimation() {
        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);

        logo.startAnimation(splashAnim);
        text.startAnimation(splashAnim);
    }

    //Method which open AuthorizationActivity after 3 seconds of pause
    private void goToNextActivity() {
        final Intent intent = new Intent(this, AuthorizationActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();
    }
}
