package com.ijoomer.tips.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;


public class CustomEditTex extends EditText {

    public CustomEditTex(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomEditTex(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomEditTex(Context context) {
        super(context);
        init(context);
    }

    private void init(Context mContext) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/arial_1.ttf");
        setTypeface(tf);
    }

}
