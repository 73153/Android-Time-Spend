package com.ijoomer.tips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;


public class SplashActivity extends Activity {

    private LinearLayout lnrFirstSuite;
    private LinearLayout lnrSecondSuite;
    private LinearLayout lnrThirdSuite;
    private LinearLayout lnrFourSuite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        lnrFirstSuite = (LinearLayout) findViewById(R.id.lnrFirstSuite);
        lnrSecondSuite = (LinearLayout) findViewById(R.id.lnrSecondSuite);
        lnrThirdSuite = (LinearLayout) findViewById(R.id.lnrThirdSuite);
        lnrFourSuite = (LinearLayout) findViewById(R.id.lnrFourSuite);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showFirstSuite();
            }
        }, 750);
    }

    public void showFirstSuite(){
        lnrFirstSuite.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_left_in);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       showSecondSuite();
                    }
                }, 750);
            }
        });

        lnrFirstSuite.startAnimation(animation);
    }

    public void showSecondSuite(){
        lnrSecondSuite.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_left_in);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showThirdSuite();
                    }
                }, 750);
            }
        });

        lnrSecondSuite.startAnimation(animation);
    }
    public void showThirdSuite(){
        lnrThirdSuite.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_left_in);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showFourSuite();
                    }
                }, 750);
            }
        });

        lnrThirdSuite.startAnimation(animation);
    }
    public void showFourSuite(){
        lnrFourSuite.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_left_in);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, InspireMeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 750);
            }
        });

        lnrFourSuite.startAnimation(animation);
    }
}
