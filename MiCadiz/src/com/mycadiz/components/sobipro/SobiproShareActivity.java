package com.mycadiz.components.sobipro;

import android.content.Intent;
import android.view.View;

import com.mycadiz.common.classes.IjoomerShareSmsActivity;
import com.mycadiz.customviews.IjoomerButton;
import com.mycadiz.src.R;

/**
 * Created by tasol on 11/11/13.
 */
public class SobiproShareActivity extends SobiproMasterActivity {

    private IjoomerButton btnFacebook;
    private IjoomerButton btnTwitter;
    private IjoomerButton btnEmail;
    private IjoomerButton btnSms;

    private String SHARE_LINK;
    @Override
    public int setLayoutId() {
        return R.layout.sobipro_share;
    }

    @Override
    public void initComponents() {
        btnFacebook = (IjoomerButton) findViewById(R.id.btnFacebook);
        btnTwitter = (IjoomerButton) findViewById(R.id.btnTwitter);
        btnEmail = (IjoomerButton) findViewById(R.id.btnEmail);
        btnSms = (IjoomerButton) findViewById(R.id.btnSms);
    }

    @Override
    public void prepareViews() {
        SHARE_LINK = getString(R.string.share_link);
    }

    @Override
    public void setActionListeners() {
        btnEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onEmail(SHARE_LINK);
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFacebook(SHARE_LINK);
            }
        });

        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTwitter(SHARE_LINK);
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSms(SHARE_LINK);
            }
        });
    }

    private void onEmail(String link) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,link);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            ting(getString(R.string.share_email_no_client));
        }
    }

    private void onFacebook(String link) {
        try {
      //    loadNew(IjoomerFacebookSharingActivity.class, this, false, "IN_LINK", link);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void onTwitter(String link) {
        try {
           // loadNew(IJoomerTwitterShareActivity.class,this, false, "IN_TWIT_MESSAGE",link);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    private void onSms(String link) {
        try {
            loadNew(IjoomerShareSmsActivity.class,this, false, "IN_SHARE_SHARELINK",link);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
