package com.ijoomer.tips;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.utilities.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class FAQActivity extends BaseActivity {

    private View view;
    private ListView lstFaq;
    private ProgressBar pbrLoading;
    private FaqAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.faq,content);
        lstFaq = (ListView) view.findViewById(R.id.lstFaq);
        pbrLoading = (ProgressBar) view.findViewById(R.id.pbrLoading);


        CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
        txtHeader.setText(Html.fromHtml(getString(R.string.faqs)));

        imgSlideMenu.setVisibility(View.GONE);
        imgSlideBack.setVisibility(View.VISIBLE);

        imgSlideBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pbrLoading.setVisibility(View.VISIBLE);
        adapter = new FaqAdapter(this,getResources().getStringArray(R.array.faq_question),getResources().getStringArray(R.array.faq_answer));
        lstFaq.setAdapter(adapter);
        pbrLoading.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    class FaqAdapter extends BaseAdapter {

        private Context context;
        private String[] faqQuestionArray;
        private String[] faqAnswerArray;
        public FaqAdapter(Context context,String[] faqQuestionArray,String[] faqAnswerArray){
            this.context=context;
            this.faqQuestionArray =faqQuestionArray;
            this.faqAnswerArray =faqAnswerArray;
        }

        @Override
        public int getCount() {
            return faqQuestionArray.length;
        }

        @Override
        public Object getItem(int position) {
            return faqQuestionArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.faq_list_item,null);
                holder.txtFaqQuestion = (CustomBoldTextView) convertView.findViewById(R.id.txtFaqQuestion);
                holder.txtFaqAnswer = (CustomTextView) convertView.findViewById(R.id.txtFaqAnswer);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }


            holder.txtFaqQuestion.setText(faqQuestionArray[position]);
            holder.txtFaqAnswer.setText(faqAnswerArray[position]);

            return convertView;
        }

        class ViewHolder{
            CustomBoldTextView txtFaqQuestion;
            CustomTextView txtFaqAnswer;
        }
    }
}
