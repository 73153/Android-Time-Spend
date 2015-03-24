package com.ijoomer.tips;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.database.DataBaseProvider;
import com.ijoomer.tips.flipanimation.AnimationFactory;
import com.ijoomer.tips.utilities.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class InspireMeActivity extends BaseActivity implements SensorListener {

    private View view;
    private CustomBoldTextView txtSuiteNameFront;
    private CustomBoldTextView txtSuiteNameBack;
    private CustomBoldTextView txtDriverTipName;
    private CustomTextView txtTip;
    private CustomTextView txtQuestionOne;
    private CustomTextView txtQuestionTwo;
    private CustomTextView txtQuestionThree;
    private ProgressBar pbrLoading;
    private ViewFlipper viewFlipper;
    private ImageView imgSuggest;
    private ImageView imgShare;
    private ImageView imgFavourite;
    private ImageView imgSuiteIconFront;
    private ImageView imgSuiteIconBack;

    private SensorManager sensorMgr;
    private long lastUpdate = -1;
    private float x, y, z;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 2000;

    private DataBaseProvider dataBaseProvider;
    private int max=62;
    private int randomNo=1;

    private Vibrator vibrator;
    private boolean isGettingTip;

    private static final int SWIPE_MIN_DISTANCE = 80;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    private boolean isFront=true;

    private PopupWindow sharePopupWindow;
    private View sharePopupView;
    private LinearLayout shareUpperLayout;
    private ImageView imgShareFacebook;
    private ImageView imgShareEmail;
    private ImageView imgShareTwitter;
    private ImageView imgShareInstagram;
    private ImageView imgSharePinterest;
    private ImageView imgShareLinkdin;

    private PopupWindow successPopupWindow;
    private View successPopupView;
    private CustomTextView txtSuccessPopupClose;
    private CustomTextView txtSuccessPopupMessage;
    private CustomBoldTextView txtSuccessPopupTitle;
    private boolean isSharePopUpHide;

    private HashMap<String,String> tipData;
    private boolean isRandomEnable=true;
    private int flipCount;
    private File image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.inspireme,content);
        txtSuiteNameFront = (CustomBoldTextView) view.findViewById(R.id.txtSuiteNameFront);
        txtSuiteNameBack = (CustomBoldTextView) view.findViewById(R.id.txtSuiteNameBack);
        txtDriverTipName = (CustomBoldTextView) view.findViewById(R.id.txtDriverTipName);
        txtTip = (CustomTextView) view.findViewById(R.id.txtTip);
        txtQuestionOne = (CustomTextView) view.findViewById(R.id.txtQuestionOne);
        txtQuestionTwo = (CustomTextView) view.findViewById(R.id.txtQuestionTwo);
        txtQuestionThree = (CustomTextView) view.findViewById(R.id.txtQuestionThree);
        pbrLoading = (ProgressBar) view.findViewById(R.id.pbrLoading);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        imgSuggest = (ImageView) view.findViewById(R.id.imgSuggest);
        imgShare = (ImageView) view.findViewById(R.id.imgShare);
        imgFavourite = (ImageView) view.findViewById(R.id.imgFavourite);
        imgSuiteIconFront = (ImageView) view.findViewById(R.id.imgSuiteIconFront);
        imgSuiteIconBack = (ImageView) view.findViewById(R.id.imgSuiteIconBack);

        txtTip.setMovementMethod(new ScrollingMovementMethod());
        txtQuestionOne.setMovementMethod(new ScrollingMovementMethod());
        txtQuestionTwo.setMovementMethod(new ScrollingMovementMethod());
        txtQuestionThree.setMovementMethod(new ScrollingMovementMethod());
        dataBaseProvider = new DataBaseProvider(this);



        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        if(getIntent().getIntExtra("tipId",0)!= 0){
            isRandomEnable=false;
            getTip(getIntent().getIntExtra("tipId",0));
        }else{
            isGettingTip=false;
            isRandomEnable=true;
            getRandomTip();


            try{
                sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
                boolean accelSupported = sensorMgr.registerListener(this,SensorManager.SENSOR_ACCELEROMETER,SensorManager.SENSOR_DELAY_GAME);

                if (!accelSupported) {
                    // on accelerometer on this device
                    Toast.makeText(getApplicationContext(), "Accelerometer Sensor Is Not Available.", Toast.LENGTH_LONG).show();
                    sensorMgr.unregisterListener(this,SensorManager.SENSOR_ACCELEROMETER);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
        txtHeader.setText(Html.fromHtml(getString(R.string.inspire_me)));


        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
            }
        });



        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSuccessPopupTitle.setText(getResources().getString(R.string.success));
                int favourite = Integer.parseInt(tipData.get(Utility.TIPFAVOURITE)) == 1 ? 0 : 1;
                if(favourite==1){
                    txtSuccessPopupMessage.setText(getResources().getString(R.string.tip_added_favourite));
                }else{
                    txtSuccessPopupMessage.setText(getResources().getString(R.string.tip_remove_favourite));
                }
                dataBaseProvider.updateFavourite(Integer.parseInt(tipData.get(Utility.TIPID)),favourite);
                tipData.put(Utility.TIPFAVOURITE,String.valueOf(favourite));
                if(Integer.parseInt(tipData.get(Utility.TIPFAVOURITE)) == 1){
                    imgFavourite.setImageResource(R.drawable.favourited_selector);
                }else{
                    imgFavourite.setImageResource(R.drawable.favourit_selector);
                }
                successPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
            }
        });

        imgSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(InspireMeActivity.this, SuggestTipActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });

        sharePopupView = LayoutInflater.from(this).inflate(R.layout.share_options_popup,null);
        shareUpperLayout = (LinearLayout) sharePopupView.findViewById(R.id.shareUpperLayout);

        imgShareFacebook = (ImageView) sharePopupView.findViewById(R.id.imgShareFacebook);
        imgShareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAppInstall=false;
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(image));
                PackageManager pm = v.getContext().getPackageManager();
                List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
                for (ResolveInfo app : activityList) {
                    if ((app.activityInfo.name).toLowerCase().contains("facebook.composer.shareintent")){
                        isAppInstall=true;
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        shareIntent.setComponent(name);
                        startActivity(shareIntent);
                        break;
                    }
                }
                showAppNotInstalledPopup(isAppInstall);
            }
        });
        imgShareEmail = (ImageView) sharePopupView.findViewById(R.id.imgShareEmail);
        imgShareEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(image));
                Uri data = Uri.parse("mailto:?subject="+getResources().getString(R.string.app_name)+" "+getResources().getString(R.string.tip)+"&body=&to=");
                emailIntent.setData(data);
                startActivity(emailIntent);
            }
        });
        imgShareInstagram = (ImageView) sharePopupView.findViewById(R.id.imgShareInstagram);
        imgShareInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAppInstall=false;
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
                PackageManager pm = v.getContext().getPackageManager();
                List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
                for (ResolveInfo app : activityList) {
                    if ((app.activityInfo.name).toLowerCase().contains("instagram")){
                        isAppInstall=true;
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        shareIntent.setComponent(name);
                        startActivity(shareIntent);
                        break;
                    }
                }
                showAppNotInstalledPopup(isAppInstall);
            }
        });
        imgShareLinkdin = (ImageView) sharePopupView.findViewById(R.id.imgShareLinkdin);
        imgShareLinkdin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAppInstall=false;
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
                PackageManager pm = v.getContext().getPackageManager();
                List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
                for (ResolveInfo app : activityList) {
                    if ((app.activityInfo.name).toLowerCase().contains("linkedin")){
                        isAppInstall=true;
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        shareIntent.setComponent(name);
                        startActivity(shareIntent);
                        break;
                    }
                }
                showAppNotInstalledPopup(isAppInstall);
            }
        });
        imgSharePinterest = (ImageView) sharePopupView.findViewById(R.id.imgSharePinterest);
        imgSharePinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAppInstall=false;
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
                PackageManager pm = v.getContext().getPackageManager();
                List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
                for (ResolveInfo app : activityList) {
                    if ((app.activityInfo.name).toLowerCase().contains("pinterest")){
                        isAppInstall=true;
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        shareIntent.setComponent(name);
                        startActivity(shareIntent);
                        break;
                    }
                }
                showAppNotInstalledPopup(isAppInstall);
            }
        });
        imgShareTwitter = (ImageView) sharePopupView.findViewById(R.id.imgShareTwitter);
        imgShareTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAppInstall=false;
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
                PackageManager pm = v.getContext().getPackageManager();
                List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
                for (ResolveInfo app : activityList) {
                    if ((app.activityInfo.name).toLowerCase().contains("twitter")){
                        isAppInstall=true;
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        shareIntent.setComponent(name);
                        startActivity(shareIntent);
                        break;
                    }
                }
                showAppNotInstalledPopup(isAppInstall);
            }
        });
        shareUpperLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePopupWindow.dismiss();
            }
        });
        sharePopupWindow = new PopupWindow(sharePopupView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        sharePopupWindow.setAnimationStyle(R.style.anim_bottom_in_out_style);

        successPopupView = LayoutInflater.from(this).inflate(R.layout.success_popup,null);
        txtSuccessPopupClose = (CustomTextView) successPopupView.findViewById(R.id.txtSuccessPopupClose);
        txtSuccessPopupTitle = (CustomBoldTextView) successPopupView.findViewById(R.id.txtSuccessPopupTitle);
        txtSuccessPopupMessage = (CustomTextView) successPopupView.findViewById(R.id.txtSuccessPopupMessage);
        successPopupWindow = new PopupWindow(successPopupView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        successPopupWindow.setAnimationStyle(R.style.anim_bottom_in_out_style);

        txtSuccessPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successPopupWindow.dismiss();
                if(isSharePopUpHide){
                    isSharePopUpHide=false;
                    sharePopupWindow.showAtLocation(imgShare, Gravity.NO_GRAVITY, 0, 0);
                }
            }
        });
    }

    private void captureScreen(){

      // create bitmap screen capture
        main.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(main.getDrawingCache());
        main.setDrawingCacheEnabled(false);

        image = new File(Environment.getExternalStorageDirectory().toString(), "share.jpg");

        OutputStream fout = null;
        try {
            fout = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fout);
            fout.flush();
            fout.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void showAppNotInstalledPopup(boolean available){
        if(!available) {
            if (sharePopupWindow.isShowing()) {
                sharePopupWindow.dismiss();
            }
            isSharePopUpHide = true;
            txtSuccessPopupTitle.setText(getResources().getString(R.string.app_not_installed));
            txtSuccessPopupMessage.setText(getResources().getString(R.string.no_such_app_found_for_sharing));
            successPopupWindow.showAtLocation(imgShare, Gravity.NO_GRAVITY, 0, 0);
        }
    }

    @Override
    public void onBackPressed() {
        if(sharePopupWindow.isShowing()){
            sharePopupWindow.dismiss();
        }else if(successPopupWindow.isShowing()){
            successPopupWindow.dismiss();
            if(isSharePopUpHide){
                isSharePopUpHide=false;
                sharePopupWindow.showAtLocation(imgShare, Gravity.NO_GRAVITY, 0, 0);
            }
        }else{
            super.onBackPressed();
        }
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if(flipCount == 0){
                        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.RIGHT_LEFT);
                        flipCount++;
                    }else{
                        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                        flipCount--;
                    }
                    isFront=false;
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if(flipCount == 0){
                        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                        flipCount++;
                    }else{
                        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.RIGHT_LEFT);
                        flipCount--;
                    }
                    isFront=true;
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    private void getRandomTip(){
        if(!isGettingTip) {
            isGettingTip = true;
            vibrator.vibrate(500);
            pbrLoading.setVisibility(View.VISIBLE);
            Random r = new Random();
            do{
                randomNo = r.nextInt(max);
            }while (randomNo<1);

            new AsyncTask<Void, Void, HashMap<String, String>>() {
                @Override
                protected HashMap<String, String> doInBackground(Void... params) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    dataBaseProvider.open();
                    try {
                        data = dataBaseProvider.getRandomTips(randomNo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return data;
                }

                @Override
                protected void onPostExecute(HashMap<String, String> response) {
                    super.onPostExecute(response);
                    tipData = response;
                    dataBaseProvider.close();
                    setTip();
                }
            }.execute();
        }

    }

    private void getTip(final int id){
        pbrLoading.setVisibility(View.VISIBLE);

        new AsyncTask<Void, Void, HashMap<String, String>>() {
            @Override
            protected HashMap<String, String> doInBackground(Void... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                // dataBaseProvider.open();
                try {
                    data = dataBaseProvider.getRandomTips(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return data;
            }

            @Override
            protected void onPostExecute(HashMap<String, String> response) {
                super.onPostExecute(response);
                tipData = response;
                //dataBaseProvider.close();
                setTip();
            }
        }.execute();

    }

    private void setTip(){
        if(tipData.get(Utility.SUITEID).equals("1")){
            txtSuiteNameFront.setTextColor(getResources().getColor(R.color.pink));
            txtSuiteNameBack.setTextColor(getResources().getColor(R.color.pink));
            imgSuiteIconFront.setImageResource(R.drawable.suite_one_corner);
            imgSuiteIconBack.setImageResource(R.drawable.suite_one_corner);
            txtDriverTipName.setText(Html.fromHtml("<font color='#ee2b7b'>"+tipData.get(Utility.DRIVERNAME) +" / </font> <br/>"+"<font color='#60605b'>"+tipData.get(Utility.TIPNAME) +"</font>"));
        }else if(tipData.get(Utility.SUITEID).equals("2")){
            txtSuiteNameFront.setTextColor(getResources().getColor(R.color.blue));
            txtSuiteNameBack.setTextColor(getResources().getColor(R.color.blue));
            imgSuiteIconFront.setImageResource(R.drawable.suite_two_corner);
            imgSuiteIconBack.setImageResource(R.drawable.suite_two_corner);
            txtDriverTipName.setText(Html.fromHtml("<font color='#009bdf'>"+tipData.get(Utility.DRIVERNAME) +" / </font> <br/>"+"<font color='#60605b'>"+tipData.get(Utility.TIPNAME)+"</font>"));
        }else if(tipData.get(Utility.SUITEID).equals("3")){
            txtSuiteNameFront.setTextColor(getResources().getColor(R.color.green));
            txtSuiteNameBack.setTextColor(getResources().getColor(R.color.green));
            imgSuiteIconFront.setImageResource(R.drawable.suite_three_corner);
            imgSuiteIconBack.setImageResource(R.drawable.suite_three_corner);
            txtDriverTipName.setText(Html.fromHtml("<font color='#3daf2c'>"+tipData.get(Utility.DRIVERNAME) +" / </font> <br/>"+"<font color='#60605b'>"+tipData.get(Utility.TIPNAME)+"</font>"));
        }else{
            txtSuiteNameFront.setTextColor(getResources().getColor(R.color.orange));
            txtSuiteNameBack.setTextColor(getResources().getColor(R.color.orange));
            imgSuiteIconFront.setImageResource(R.drawable.suite_four_corner);
            imgSuiteIconBack.setImageResource(R.drawable.suite_four_corner);
            txtDriverTipName.setText(Html.fromHtml("<font color='#f7931e'>"+tipData.get(Utility.DRIVERNAME) +" / </font> <br/>"+"<font color='#60605b'>"+tipData.get(Utility.TIPNAME)+"</font>"));
        }

        txtSuiteNameFront.setText(tipData.get(Utility.SUITENAME));
        txtSuiteNameBack.setText(tipData.get(Utility.SUITENAME));

        txtTip.setText(tipData.get(Utility.TIPNOTE));
        txtQuestionOne.setText(tipData.get(Utility.TIPQUESTIONONE)!=null && tipData.get(Utility.TIPQUESTIONONE).trim().length()>0 ? tipData.get(Utility.TIPQUESTIONONE) :"Question number 1 relating to this tip?");
        txtQuestionTwo.setText(tipData.get(Utility.TIPQUESTIONTWO)!=null && tipData.get(Utility.TIPQUESTIONTWO).trim().length()>0 ? tipData.get(Utility.TIPQUESTIONTWO) :"Question number 2 relating to this tip?");
        txtQuestionThree.setText(tipData.get(Utility.TIPQUESTIONTHREE)!=null && tipData.get(Utility.TIPQUESTIONTHREE).trim().length()>0 ? tipData.get(Utility.TIPQUESTIONTHREE) :"Question number 3 relating to this tip?");

        pbrLoading.setVisibility(View.GONE);

        if(Integer.parseInt(tipData.get(Utility.TIPFAVOURITE)) == 1){
            imgFavourite.setImageResource(R.drawable.favourited_selector);
        }else{
            imgFavourite.setImageResource(R.drawable.favourit_selector);
        }

        isGettingTip=false;

        main.post(new Runnable() {
            @Override
            public void run() {
                captureScreen();
            }
        });
    }


    public void onSensorChanged(int sensor, float[] values) {
        if (isRandomEnable && sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                x = values[SensorManager.DATA_X];
                y = values[SensorManager.DATA_Y];
                z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z)
                        / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    getRandomTip();
                    if(!isFront) {
                        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.RIGHT_LEFT);
                        isFront = true;
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    public void onAccuracyChanged(int arg0, int arg1) {
    }


    protected void onPause() {
        if (isRandomEnable && sensorMgr != null) {
            sensorMgr.unregisterListener(this,SensorManager.SENSOR_ACCELEROMETER);
            sensorMgr = null;
        }
        super.onPause();
    }

}
