package com.ijoomer.tips.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomBoldTextView extends TextView {

	public CustomBoldTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CustomBoldTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CustomBoldTextView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context mContext) {
			Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/arialbd_1.ttf");
			setTypeface(tf);
	}

}
