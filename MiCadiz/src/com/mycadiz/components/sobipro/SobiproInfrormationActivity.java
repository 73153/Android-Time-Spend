package com.mycadiz.components.sobipro;

import android.content.Intent;
import android.net.Uri;
import android.widget.RadioGroup;

import com.mycadiz.customviews.IjoomerRadioButton;
import com.mycadiz.src.R;

/**
 * Created by tasol on 11/11/13.
 */
public class SobiproInfrormationActivity extends SobiproMasterActivity {

    private RadioGroup rdgTop;
    private IjoomerRadioButton rdbAbout;
    private IjoomerRadioButton rdbContact;
    private IjoomerRadioButton rdbWeb;
    @Override
    public int setLayoutId() {
        return R.layout.sobipro_information;
    }

    @Override
    public void initComponents() {
        rdgTop = (RadioGroup) findViewById(R.id.rdgTop);
        rdbAbout = (IjoomerRadioButton) findViewById(R.id.rdbAbout);
        rdbContact = (IjoomerRadioButton) findViewById(R.id.rdbContact);
        rdbWeb = (IjoomerRadioButton) findViewById(R.id.rdbWeb);
    }

    @Override
    public void prepareViews() {
    }

    @Override
    protected void onResume() {
        rdbAbout.setChecked(true);
        rdbContact.setChecked(false);
        rdbWeb.setChecked(false);
        super.onResume();
    }

    @Override
    public void setActionListeners() {
        rdgTop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rdbContact:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        if(getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"anunciar@MiCadiz.info"});
                        }else{
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"advertise@MiCadiz.info"});
                        }
                        intent.putExtra(Intent.EXTRA_SUBJECT,"");
                        intent.putExtra(Intent.EXTRA_TEXT,"");
                        try {
                            startActivity(Intent.createChooser(intent, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            ting(getString(R.string.share_email_no_client));
                        }
                        break;
                    case R.id.rdbWeb:
                        Intent web = new Intent(Intent.ACTION_VIEW);
                        web.setData(Uri.parse("http://www.mycadizapp.com/"));
                        startActivity(web);
//                        try {
//                            loadNew(IjoomerWebviewClient.class, SobiproInfrormationActivity.this, false, "url", "http://www.mycadizapp.com/");
//                        } catch (Throwable e) {
//                            e.printStackTrace();
//                        }
                        break;


                }
            }
        });
    }

}