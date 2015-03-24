package com.ijoomer.tips;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.utilities.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class ContactUsActivity extends BaseActivity {

    private View view;
    private Button btnCallUs;
    private Button btnEmailUs;
    private Button btnWebsite;
    private Button btnCallMeBack;
    private Button btnFAQ;

    private PopupWindow callUsPopupWindow;
    private View callUsPopupView;
    private ListView lstCallUs;
    private CustomTextView txtPopupClose;
    private CallUsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.contactus,content);
        btnCallUs = (Button) view.findViewById(R.id.btnCallUs);
        btnEmailUs = (Button) view.findViewById(R.id.btnEmailUs);
        btnWebsite = (Button) view.findViewById(R.id.btnWebsite);
        btnCallMeBack = (Button) view.findViewById(R.id.btnCallMeBack);
        btnFAQ = (Button) view.findViewById(R.id.btnFAQ);


        if(getIntent().getIntExtra("FromVTR",0)==1){
            imgSlideBack.setVisibility(View.VISIBLE);
            imgSlideMenu.setVisibility(View.GONE);
            imgSlideBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }else{
            imgSlideBack.setVisibility(View.GONE);
            imgSlideMenu.setVisibility(View.VISIBLE);
        }

        btnCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUsPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
            }
        });
        btnEmailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=&body=&to="+ Utility.MAIL_TO);
                emailIntent.setData(data);
                startActivity(emailIntent);
            }
        });
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Utility.WEBSITE_URL));
                startActivity(intent);
            }
        });
        btnCallMeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactUsActivity.this,CallMeBackActivity.class);
                startActivity(intent);
            }
        });
        btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactUsActivity.this,FAQActivity.class);
                startActivity(intent);
            }
        });

        CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
        txtHeader.setText(Html.fromHtml(getString(R.string.contact_us)));
        callUsPopupView = LayoutInflater.from(this).inflate(R.layout.call_us_popup,null);
        lstCallUs = (ListView) callUsPopupView.findViewById(R.id.lstCallUs);
        txtPopupClose = (CustomTextView) callUsPopupView.findViewById(R.id.txtPopupClose);
        adapter = new CallUsAdapter(this,Utility.CALL_US,Utility.CALL_US_VALUE);
        lstCallUs.setAdapter(adapter);
        lstCallUs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        txtPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callUsPopupWindow.isShowing()){
                    callUsPopupWindow.dismiss();
                }
            }
        });

        callUsPopupWindow = new PopupWindow(callUsPopupView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,false);
        callUsPopupWindow.setAnimationStyle(R.style.anim_bottom_in_out_style);
    }

    @Override
    public void onBackPressed() {
        if(callUsPopupWindow.isShowing()){
            callUsPopupWindow.dismiss();
        }else{
            super.onBackPressed();
        }
    }


    class CallUsAdapter extends BaseAdapter {

        private Context context;
        private String[] callUsArray;
        private String[] callUsValueArray;
        public CallUsAdapter(Context context,String[] callUsArray,String[] callUsValueArray){
            this.context=context;
            this.callUsArray =callUsArray;
            this.callUsValueArray =callUsValueArray;
        }

        @Override
        public int getCount() {
            return callUsArray.length;
        }

        @Override
        public Object getItem(int position) {
            return callUsValueArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.callus_list_item,null);
                holder.txtCallUsNumber = (CustomTextView) convertView.findViewById(R.id.txtCallUsNumber);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtCallUsNumber.setText(callUsArray[position]);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",callUsValueArray[position], null));
                    startActivity(intent);
                    callUsPopupWindow.dismiss();
                }
            });

            return convertView;
        }

        class ViewHolder{
            CustomTextView txtCallUsNumber;
        }
    }
}
