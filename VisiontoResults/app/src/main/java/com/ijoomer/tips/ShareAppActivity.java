package com.ijoomer.tips;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomEditTex;
import com.ijoomer.tips.utilities.Utility;


public class ShareAppActivity extends BaseActivity {

    private View view;
    private Button btnShareEmail;
    private Button btnShareSms;
    private CustomEditTex edtShareText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.share_app,content);
        btnShareEmail = (Button) view.findViewById(R.id.btnShareEmail);
        btnShareSms = (Button) view.findViewById(R.id.btnShareSms);
        edtShareText = (CustomEditTex) view.findViewById(R.id.edtShareText);

        btnShareEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject="+getResources().getString(R.string.app_name)+" "+getResources().getString(R.string.tip)+"&body="+edtShareText.getText().toString()+"\n"+Utility.WEBSITE_URL+"&to=");
                emailIntent.setData(data);
                startActivity(emailIntent);
            }
        });
        btnShareSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:"));
                smsIntent.putExtra("sms_body", edtShareText.getText().toString()+"\n"+Utility.WEBSITE_URL);
                startActivity(smsIntent);
            }
        });

        CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
        txtHeader.setText(Html.fromHtml(getString(R.string.share_app)));
    }

}
