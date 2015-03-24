package com.ijoomer.components.intafy;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.IjoomerSplashActivity;
import com.ijoomer.intafy.src.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyLockAppActivity extends IjoomerIntafyMaster {



    private IjoomerTextView txtLockAppDigit;
    private ImageView imgOne;
    private ImageView imgTwo;
    private ImageView imgThree;
    private ImageView imgFour;
    private ImageView imgFive;
    private ImageView imgSix;
    private ImageView imgSeven;
    private ImageView imgEight;
    private ImageView imgNine;
    private ImageView imgZero;
    private ImageView imgBack;


    /**
     * Overrides methods
     */



    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_lockapp;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public int setHeaderLayoutId() {
        return 0;
    }

    @Override
    public int setFooterLayoutId() {
        return 0;
    }

    @Override
    public void initComponents() {

        txtLockAppDigit = (IjoomerTextView) findViewById(R.id.txtLockAppDigit);
        imgOne = (ImageView) findViewById(R.id.imgOne);
        imgTwo = (ImageView) findViewById(R.id.imgTwo);
        imgThree = (ImageView) findViewById(R.id.imgThree);
        imgFour = (ImageView) findViewById(R.id.imgFour);
        imgFive = (ImageView) findViewById(R.id.imgFive);
        imgSix = (ImageView) findViewById(R.id.imgSix);
        imgSeven = (ImageView) findViewById(R.id.imgSeven);
        imgEight = (ImageView) findViewById(R.id.imgEight);
        imgNine = (ImageView) findViewById(R.id.imgNine);
        imgZero = (ImageView) findViewById(R.id.imgZero);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        if(getSmartApplication().readSharedPreferences().getString(SP_ISAPPLOCK,"false").equals("false")){
            loadNew(IjoomerSplashActivity.class,this,true);
        }

    }

    @Override
    public void prepareViews() {

    }

    @Override
    public void setActionListeners() {
        imgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgOne.setImageResource(R.drawable.one_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgOne.setImageResource(R.drawable.one_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"1");
                        }
                    }
                }, 1000);
            }
        });

        imgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgTwo.setImageResource(R.drawable.two_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgTwo.setImageResource(R.drawable.two_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"2");
                        }
                    }
                }, 1000);
            }
        });
        imgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgThree.setImageResource(R.drawable.three_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgThree.setImageResource(R.drawable.three_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"3");
                        }
                    }
                }, 1000);
            }
        });
        imgFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFour.setImageResource(R.drawable.four_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgFour.setImageResource(R.drawable.four_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"4");
                        }
                    }
                }, 1000);
            }
        });
        imgFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFive.setImageResource(R.drawable.five_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgFive.setImageResource(R.drawable.five_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"5");
                        }
                    }
                }, 1000);
            }
        });
        imgSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSix.setImageResource(R.drawable.six_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSix.setImageResource(R.drawable.six_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"6");
                        }
                    }
                }, 1000);
            }
        });
        imgSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSeven.setImageResource(R.drawable.seven_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSeven.setImageResource(R.drawable.seven_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"7");
                        }
                    }
                }, 1000);
            }
        });
        imgEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgEight.setImageResource(R.drawable.eight_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgEight.setImageResource(R.drawable.eight_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"8");
                        }
                    }
                }, 1000);
            }
        });
        imgNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgNine.setImageResource(R.drawable.nine_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgNine.setImageResource(R.drawable.nine_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"9");
                        }
                    }
                }, 1000);
            }
        });
        imgZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgZero.setImageResource(R.drawable.zero_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgZero.setImageResource(R.drawable.zero_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"0");
                        }
                    }
                }, 1000);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgBack.setImageResource(R.drawable.back_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgBack.setImageResource(R.drawable.back_btn);
                        if(txtLockAppDigit.getText().length()>0){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString().subSequence(0,txtLockAppDigit.getText().toString().length()-1));
                        }
                    }
                }, 1000);
            }
        });

        txtLockAppDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(getSmartApplication().readSharedPreferences().getString(SP_APPLOCKPASSWORD,""))){
                    loadNew(IjoomerSplashActivity.class, IjoomerIntafyLockAppActivity.this, true);
                }
            }
        });
    }
}
