package com.ijoomer.tips;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.utilities.Utility;


public class AboutVTRActivity extends BaseActivity {

    private View view;
    private Button btnVTREBook;
    private CustomTextView txtAboutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.about_vtr,content);
        btnVTREBook = (Button) view.findViewById(R.id.btnVTREBook);
        txtAboutContent = (CustomTextView) view.findViewById(R.id.txtAboutContent);

        txtAboutContent.setMovementMethod(new ScrollingMovementMethod());
        btnVTREBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(AboutVTRActivity.this,VTREBookActivity.class);
                startActivity(intent);
            }
        });

        CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
        txtHeader.setText(Html.fromHtml(getString(R.string.about_vtr)));


    }
}
