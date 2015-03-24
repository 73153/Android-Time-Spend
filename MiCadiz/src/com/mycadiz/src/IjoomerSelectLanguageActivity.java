package com.mycadiz.src;

import android.content.res.Configuration;
import android.view.View;
import android.widget.RadioGroup;

import com.mycadiz.common.classes.IjoomerSuperMaster;
import com.mycadiz.customviews.IjoomerButton;
import com.smart.framework.SmartApplication;

import java.util.Locale;

/**
 * Created by tasol on 11/11/13.
 */
public class IjoomerSelectLanguageActivity extends IjoomerSuperMaster {

    private IjoomerButton btnEnglish;
    private IjoomerButton btnSpanish;
    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_language_selector;
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
        btnEnglish = (IjoomerButton) findViewById(R.id.btnEnglish);
        btnSpanish = (IjoomerButton) findViewById(R.id.btnSpanish);
    }

    @Override
    public void prepareViews() {
    }

    @Override
    public void setActionListeners() {

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("en");
                SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_LNAGUAGE,"en");
                if(Boolean.parseBoolean(getString(R.string.local_database))){
                    //SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_LAST_REQUEST_TIME,"");
                }
                try {
                    loadNew(IjoomerSplashActivity.class,
                            IjoomerSelectLanguageActivity.this, true,"IN_PRIVIOUS_ACTIVITY",getString(R.string.home));
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });

        btnSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("es");
                SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_LNAGUAGE,"es");
                if(Boolean.parseBoolean(getString(R.string.local_database))){
                    // SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_LAST_REQUEST_TIME,"");
                }
                try {
                    loadNew(IjoomerSplashActivity.class,
                            IjoomerSelectLanguageActivity.this, true,"IN_PRIVIOUS_ACTIVITY",getString(R.string.home));
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeLanguage(String langCode){
        Locale locale;
        if(langCode.equals("es")){
            locale = new Locale(langCode,"ES");
        }else{
            locale = new Locale(langCode,"EN");
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public String[] setTabItemNames() {
        return new String[0];
    }

    @Override
    public int setTabBarDividerResId() {
        return 0;
    }

    @Override
    public int setTabItemLayoutId() {
        return 0;
    }

    @Override
    public int[] setTabItemOnDrawables() {
        return new int[0];
    }

    @Override
    public int[] setTabItemOffDrawables() {
        return new int[0];
    }

    @Override
    public int[] setTabItemPressDrawables() {
        return new int[0];
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }
}
